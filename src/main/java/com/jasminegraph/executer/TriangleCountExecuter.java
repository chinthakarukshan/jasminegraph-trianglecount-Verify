package com.jasminegraph.executer;

import com.jasminegraph.dto.Triangle;

import java.io.*;
import java.util.*;

public class TriangleCountExecuter {

    Map<Integer, List<Integer>> graphDB = new HashMap<>();
    Map<Integer, Integer> graphDBDistributionMap = new HashMap<>();
    Map<Integer,Set<Integer>> degreeMap = new HashMap<>();

    public void loadGraph(String fileName) throws IOException {
        File graphFile = new File(fileName);

        if (graphFile.exists()) {
            FileReader graphFileReader = new FileReader(graphFile);
            BufferedReader graphBufferedReader = new BufferedReader(graphFileReader);

            String splitter=null;
            String vertexLine;

            while ((vertexLine = graphBufferedReader.readLine()) != null) {
                if (vertexLine != null && !vertexLine.isEmpty()) {
                    if (splitter == null && vertexLine.indexOf(" ") != -1) {
                        splitter = " ";
                    } else if (splitter == null && vertexLine.indexOf("\t") != -1) {
                        splitter = "\t";
                    } else if (splitter == null && vertexLine.indexOf(",") != -1) {
                        splitter = ",";
                    }

                    String[] nodeArray = vertexLine.split(splitter);

                    String nodeOne = nodeArray[0];
                    String nodeTwo = nodeArray[1];

                    Integer firstNode = new Integer(nodeOne);
                    Integer secondNode = new Integer(nodeTwo);

                    if (graphDB.containsKey(firstNode)) {
                        List<Integer> firstEdgeList = graphDB.get(firstNode);
                        if (!firstEdgeList.contains(secondNode)) {
                            firstEdgeList.add(secondNode);
                        }
                    } else {
                        List<Integer> firstEdgeList = new ArrayList<>();
                        firstEdgeList.add(secondNode);
                        graphDB.put(firstNode,firstEdgeList);
                    }

                    if (graphDB.containsKey(secondNode)) {
                        List<Integer> secondEdgeList = graphDB.get(secondNode);
                        if (!secondEdgeList.contains(firstNode)) {
                            secondEdgeList.add(firstNode);
                        }
                    } else {
                        List<Integer> secondEdgeList = new ArrayList<>();
                        secondEdgeList.add(firstNode);
                        graphDB.put(secondNode,secondEdgeList);
                    }
                }
            }
        }
    }

    public void countTriangles(String fileName) throws IOException {
        loadGraph(fileName);
        System.out.println("###TRIANGLECOUNT### Loaded Graph");
        generateDistributionMap();
        executeTriangleCount();
    }

    public void generateDistributionMap() {
        Set<Integer> keyset = graphDB.keySet();

        for (Integer node:keyset) {
            Integer distribution = graphDB.get(node).size();
            graphDBDistributionMap.put(node,distribution);
        }
    }

    public void executeTriangleCount() {
        Set<Integer> nodeSet = graphDBDistributionMap.keySet();
        List<Triangle> triangleList = new ArrayList<>();
        int triangleCount = 0;

        for (Integer node:nodeSet) {
            Integer distribution = graphDBDistributionMap.get(node);

            if (degreeMap.containsKey(distribution)) {
                degreeMap.get(distribution).add(node);
            } else {
                Set<Integer> nodes = new HashSet<>();
                nodes.add(node);
                degreeMap.put(distribution,nodes);
            }
        }

        Set<Integer> distributionSet = degreeMap.keySet();

        for (Integer distribution: distributionSet) {
            if (distribution.intValue() == 1) {
                continue;
            }

            Set<Integer> nodes = degreeMap.get(distribution);

            for (Integer node:nodes) {
                List<Integer> uList = graphDB.get(node);
                uList.sort(Comparator.naturalOrder());

                for (Integer uNode : uList) {
                    List<Integer> nuList = graphDB.get(uNode);
                    nuList.sort(Comparator.naturalOrder());

                    for (Integer nuNode:nuList) {
                        if (graphDB.get(node).contains(nuNode) || graphDB.get(nuNode).contains(node)) {
                            List<Integer> tempList = new ArrayList<>();
                            tempList.add(node);
                            tempList.add(uNode);
                            tempList.add(nuNode);
                            tempList.sort(Comparator.naturalOrder());

                            Integer nodeOne = tempList.get(0);
                            Integer nodeTwo = tempList.get(1);
                            Integer nodeThree = tempList.get(2);

                            Triangle triangle = new Triangle(nodeOne,nodeTwo,nodeThree);

                            if (!triangleList.contains(triangle)) {
                                triangleCount++;
                                System.out.println(triangle.toString());
                                triangleList.add(triangle);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("###TRIANGLECOUNT#### NUMBER OF TRIANGLES : " + triangleCount);
    }
}

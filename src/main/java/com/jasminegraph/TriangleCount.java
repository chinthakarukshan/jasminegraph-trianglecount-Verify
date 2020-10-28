package com.jasminegraph;

import com.jasminegraph.executer.TriangleCountExecuter;

import java.io.IOException;

public class TriangleCount {
    public static void main(String[] args) throws IOException {
        //String fileName = "/home/chinthaka/research/dataset/baseData/base.csv";
        String fileName1 = "/home/chinthaka/research/dataset/p2p-Gnutella08.txt";
        TriangleCountExecuter triangleCountExecuter = new TriangleCountExecuter();
        triangleCountExecuter.countTriangles(fileName1);
    }
}

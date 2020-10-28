package com.jasminegraph.dto;

public class Triangle {

    private Integer nodeOne;

    private Integer nodeTwo;

    private Integer nodeThree;

    public Triangle(Integer nodeOne, Integer nodeTwo, Integer nodeThree) {
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
        this.nodeThree = nodeThree;
    }

    public Integer getNodeOne() {
        return nodeOne;
    }

    public void setNodeOne(Integer nodeOne) {
        this.nodeOne = nodeOne;
    }

    public Integer getNodeTwo() {
        return nodeTwo;
    }

    public void setNodeTwo(Integer nodeTwo) {
        this.nodeTwo = nodeTwo;
    }

    public Integer getNodeThree() {
        return nodeThree;
    }

    public void setNodeThree(Integer nodeThree) {
        this.nodeThree = nodeThree;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        Triangle triangle = (Triangle) obj;

        if (this.getNodeOne().equals(triangle.getNodeOne()) && this.getNodeTwo().equals(triangle.getNodeTwo()) && this.getNodeThree().equals(triangle.getNodeThree())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return nodeOne + ":" + nodeTwo + ":" + nodeThree;
    }
}

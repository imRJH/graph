package ch.rjh.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    // Attributes :
    private String name;
    private int level;
    private boolean isMarked;
    private Map<String, Edge> enteringEdge;
    private Map<String, Edge> exitingEdge;

    // Constructors :
    public Node(String name) {
        this.name = name;
        this.level = 0;
        this.isMarked = false;
        enteringEdge = new HashMap<>();
        exitingEdge = new HashMap<>();
    }

    // Getter & Setter :
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<String, Edge> getEnteringEdge() {
        return enteringEdge;
    }

    public void setEnteringEdge(Map<String, Edge> enteringEdge) {
        this.enteringEdge = enteringEdge;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public void reinit() {
        this.level = 0;
        this.isMarked = false;
    }

    public Map<String, Edge> getExitingEdge() {
        return exitingEdge;
    }

    public void setExitingEdge(Map<String, Edge> exitingEdge) {
        this.exitingEdge = exitingEdge;
    }

    // Methods :
    public void mark() {
        this.setMarked(true);
    }

    public void addEdge(String edgeName, Node nodeDestination, double metric) {
        Edge edge = new Edge(edgeName, nodeDestination, metric);
        this.getExitingEdge().put(edge.getName(), edge);
    }

    public void addEdgeWithSource(String edgeName, Node nodeDestination, double metric) {
        Edge edge = new Edge(edgeName, this, nodeDestination, metric);
        this.getExitingEdge().put(edge.getName(), edge);
        nodeDestination.getEnteringEdge().put(edge.getName(), edge);
    }

    public void removeEdge(String edgeName, Node node) {
        node.getExitingEdge().remove(edgeName);
    }

    public void removeEdgeWithSource(String edgeName, Node nodeDestination) {
        this.getExitingEdge().remove(edgeName);
        nodeDestination.getEnteringEdge().remove(edgeName);
    }

    public void listEnteringEdge() {
        List<Edge> list = new ArrayList<>();
        for (Edge edge : this.getEnteringEdge().values()) {
            list.add(edge);
        }
        System.out.println("Arc entrants de " + this + " :");
        System.out.println(list);
    }

    // toString :
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", exitingEdge=" + exitingEdge +
                '}';
    }

}

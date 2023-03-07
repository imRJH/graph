package ch.rjh.business;

import java.util.HashMap;
import java.util.Map;

public class Node {

    // Attributes :
    private String name;
    private Map<String, Edge> enteringEdge;
    private Map<String, Edge> exitingEdge;

    // Constructors :
    public Node(String name) {
        this.name = name;
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

    public Map<String, Edge> getEnteringEdge() {
        return enteringEdge;
    }

    public void setEnteringEdge(Map<String, Edge> enteringEdge) {
        this.enteringEdge = enteringEdge;
    }

    public Map<String, Edge> getExitingEdge() {
        return exitingEdge;
    }

    public void setExitingEdge(Map<String, Edge> exitingEdge) {
        this.exitingEdge = exitingEdge;
    }

    // Methods :

    // toString :
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", exitingEdge=" + exitingEdge +
                '}';
    }

}

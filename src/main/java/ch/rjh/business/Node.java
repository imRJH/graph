package ch.rjh.business;

import java.util.HashMap;
import java.util.Map;

public class Node {

    // Attributes :
    private String name;
    private int level;
    private Map<String, Edge> enteringEdge;
    private Map<String, Edge> exitingEdge;
    private boolean isMarked;

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

    // toString :
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", exitingEdge=" + exitingEdge +
                '}';
    }

}

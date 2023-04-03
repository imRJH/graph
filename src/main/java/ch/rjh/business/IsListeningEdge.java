package ch.rjh.business;

public class IsListeningEdge extends Edge {

    public IsListeningEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsListeningEdge(String name, Node destination, double metric) {
        super(name, destination, metric);
    }

    public IsListeningEdge(String name, Node source, Node destination, double metric) {
        super(name, source, destination, metric);
    }

}

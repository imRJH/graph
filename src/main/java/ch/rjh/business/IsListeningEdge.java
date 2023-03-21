package ch.rjh.business;

public class IsListeningEdge extends Edge {

    public IsListeningEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsListeningEdge(String name, double metric, Node destination) {
        super(name, metric, destination);
    }

    public IsListeningEdge(String name, double metric, Node source, Node destination) {
        super(name, metric, source, destination);
    }

}

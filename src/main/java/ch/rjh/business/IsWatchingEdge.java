package ch.rjh.business;

public class IsWatchingEdge extends Edge {

    public IsWatchingEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsWatchingEdge(String name, Node destination, double metric) {
        super(name, destination, metric);
    }

    public IsWatchingEdge(String name, Node source, Node destination, double metric) {
        super(name, source, destination, metric);
    }

}

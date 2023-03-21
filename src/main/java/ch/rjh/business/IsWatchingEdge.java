package ch.rjh.business;

public class IsWatchingEdge extends Edge {

    public IsWatchingEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsWatchingEdge(String name, double metric, Node destination) {
        super(name, metric, destination);
    }

    public IsWatchingEdge(String name, double metric, Node source, Node destination) {
        super(name, metric, source, destination);
    }

}

package ch.rjh.business;

public class IsFriendWithEdge extends Edge {

    public IsFriendWithEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsFriendWithEdge(String name, Node source, Node destination) {
        super(name, source, destination);
    }

    public IsFriendWithEdge(String name, Node destination, double metric) {
        super(name, destination, metric);
    }

    public IsFriendWithEdge(String name, Node source, Node destination, double metric) {
        super(name, source, destination, metric);
    }

}

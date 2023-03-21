package ch.rjh.business;

public class IsFriendWithEdge extends Edge {

    public IsFriendWithEdge(String name, Node destination) {
        super(name, destination);
    }

    public IsFriendWithEdge(String name, Node source, Node destination) {
        super(name, source, destination);
    }

    public IsFriendWithEdge(String name, double metric, Node destination) {
        super(name, metric, destination);
    }

    public IsFriendWithEdge(String name, double metric, Node source, Node destination) {
        super(name, metric, source, destination);
    }

}

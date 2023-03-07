package ch.rjh.business;

public class Edge {

    // Attributes :
    private String name;
    private double metric;
    private Node source;
    private Node destination;

    // Constructors :
    public Edge(String name, Node destination) {
        this.name = name;
        this.destination = destination;
    }

    public Edge(String name, double metric, Node destination) {
        this.name = name;
        this.metric = metric;
        this.destination = destination;
    }

    public Edge(String name, double metric, Node source, Node destination) {
        this.name = name;
        this.metric = metric;
        this.source = source;
        this.destination = destination;
    }

    // Getter & Setter :
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMetric() {
        return metric;
    }

    public void setMetric(double metric) {
        this.metric = metric;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    // Methods :

    // toString :
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(" - metric : ").append(metric)
                .append(", destination : ").append(destination.getName());
        return sb.toString();
    }

}

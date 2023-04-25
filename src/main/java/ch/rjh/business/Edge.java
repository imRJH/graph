package ch.rjh.business;

import java.io.Serializable;

public class Edge implements Serializable {

    private String name;
    private Node source;
    private Node destination;
    private double metric;

    public Edge(String name, Node destination, double metric) {
        this.name = name;
        this.metric = metric;
        this.destination = destination;
    }

    public Edge(String name, Node source, Node destination, double metric) {
        this.name = name;
        this.metric = metric;
        this.source = source;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public double getMetric() {
        return metric;
    }

    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }

    /**
     * Nouvelle méthode toString()
     * @return Un objet String à afficher
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name)
                .append(" - metric : ").append(metric)
                .append(", destination : ").append(destination.getName());
        return sb.toString();
    }

}

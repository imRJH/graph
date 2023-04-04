package ch.rjh.dijkstra;

import ch.rjh.business.Node;

public class DijkstraTriplet {

    private Node current;
    private double metric;
    private Node predecessor;

    public DijkstraTriplet(Node current, double metric, Node predecessor) {
        this.current = current;
        this.metric = metric;
        this.predecessor = predecessor;
    }

    /**
     * Nouvelle méthode toString()
     * @return Un objet String à afficher
     */
    @Override
    public String toString() {
        return "DijkstraTriplet{" +
                "current=" + current +
                ", metric=" + metric +
                ", predecessor=" + predecessor +
                '}';
    }
}

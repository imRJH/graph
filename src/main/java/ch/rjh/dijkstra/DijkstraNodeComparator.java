package ch.rjh.dijkstra;

import ch.rjh.business.Node;

import java.util.Comparator;

public class DijkstraNodeComparator implements Comparator {

    public int compare(Object arg0, Object arg1) {
        Node one = (Node)arg0;
        Node two = (Node)arg1;
        if (one.getDijkstraWeight() < two.getDijkstraWeight()) {
            return -1;
        } else if (one.getDijkstraWeight() > two.getDijkstraWeight()) {
            return 1;
        } else {
            return 0;
        }
    }

}

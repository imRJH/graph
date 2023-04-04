package ch.rjh.business;

import ch.rjh.dijkstra.DijkstraNodeComparator;

import java.util.*;

public class Graph {

    private String name;
    private Map<String, Node> nodeMap;

    public Graph(String name) {
        this.name = name;
        this.nodeMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

    public void setNodeMap(Map<String, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }

    /**
     * Ajouter un noeud au graphe
     * @param node Noeud à ajouter
     */
    public void addNode(Node node) {
        nodeMap.put(node.getName(), node);
    }

    /**
     * Supprimer un noeud du graphe
     * @param node
     */
    public void removeNode(Node node) {
        for (Node nodeNavigation : this.nodeMap.values()) {
            Iterator<Edge> iteEdge = nodeNavigation.getExitingEdge().values().iterator();
            while (iteEdge.hasNext()) {
                Edge edge = iteEdge.next();
                if (nodeNavigation.equals(edge.getDestination().getName())) {
                    iteEdge.remove();
                }
            }
        }
        nodeMap.remove(node);
    }

    /**
     * Supprimer un noeud du graphe en gérant également les sources
     * @param node Noeud à supprimer
     */
    public void removeNodeWithSource(Node node) {
        for (Node nodeNavigation : this.nodeMap.values()) {
            Iterator<Edge> exitingEdge = nodeNavigation.getExitingEdge().values().iterator();
            while (exitingEdge.hasNext()) {
                Edge edge = exitingEdge.next();
                if (nodeNavigation.getName().equals(edge.getDestination().getName())) {
                    exitingEdge.remove();
                }
            }
            Iterator<Edge> enteringEdge = nodeNavigation.getEnteringEdge().values().iterator();
            while (enteringEdge.hasNext()) {
                Edge edge = enteringEdge.next();
                if (nodeNavigation.getName().equals(edge.getSource().getName())) {
                    enteringEdge.remove();
                }
            }
        }
        nodeMap.remove(node.getName());
    }

    /**
     * Réinitialise tous les noeuds du graphe avant un parcous
     */
    public void reinitAllBeforeWay() {
        for (Node node : nodeMap.values()) {
            node.reinitBeforeWay();
        }
    }

    /**
     * Réinitialise tous les noeuds du graphe avant un algorithme Dijkstra
     */
    public void reinitAllBeforeDijkstra() {
        for (Node node : nodeMap.values()) {
            node.reinitBeforeDijkstra();
        }
    }

    /**
     * Nouvelle méthode toString()
     * @return Un objet String à afficher
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Graph ").append(name).append(" :\n");
        for (Map.Entry<String, Node> entryNode : nodeMap.entrySet()) {
            sb.append("w++(").append(entryNode.getKey()).append(") = { ");
            for (Map.Entry<String, Edge> entryEdge : entryNode.getValue().getExitingEdge().entrySet()) {
                sb.append(entryEdge.getKey())
                        .append("(")
                        .append(entryNode.getKey())
                        .append(",")
                        .append(entryEdge.getValue().getDestination().getName())
                        .append(",")
                        .append(String.valueOf(entryEdge.getValue().getMetric()))
                        .append(") ");
            }
            sb.append("}\n");
        }
        return sb.toString();
    }

}

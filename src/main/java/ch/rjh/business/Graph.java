package ch.rjh.business;

import java.io.Serializable;
import java.util.*;

public class Graph implements Serializable {

    private String name;
    private Map<String, Node> nodeMap;
    private Map<Integer, List<Node>> miseEnRang;

    public Graph(String name) {
        this.name = name;
        this.nodeMap = new HashMap<>();
        this.miseEnRang = new HashMap<>();
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

    public Map<Integer, List<Node>> getMiseEnRang() {
        return miseEnRang;
    }

    public void setMiseEnRang(Map<Integer, List<Node>> miseEnRang) {
        this.miseEnRang = miseEnRang;
    }

    public Node findNode(String nodeName) {
        return nodeMap.get(nodeName);
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

    private void reinitAllDegres() {
        for (Node node : nodeMap.values()) {
            node.setDegInt(0);
            node.setDegOut(0);
        }
    }

    public void calculDegres() {
        reinitAllDegres();
        for (Node node : nodeMap.values()) {
            node.setDegOut(node.getExitingEdge().size());
            for (Edge edge : node.getExitingEdge().values()) {
                Node nodeDest = edge.getDestination();
                nodeDest.setDegInt(nodeDest.getDegInt()+1);
            }
        }
    }

    public void reinitOrdo() {
        for (Node node : nodeMap.values()) {
            node.setOrdoAuPlusTot(0);
            node.setOrdoAuPlusTard(Double.MAX_VALUE);
        }
    }

    public void afficherOrdonnancementAuPlusTard() {
        System.out.println("Affichage ordonnancement au plus tard");
        for (Node node : nodeMap.values()) {
            System.out.println(node.getName() + " : " + node.getOrdoAuPlusTard());
        }
        System.out.println();
    }

    public void afficherOrdonnancementAuPlusTot() {
        System.out.println("Affichage ordonnancement au plus tôt");
        for (Node node : nodeMap.values()) {
            System.out.println(node.getName() + " : " + node.getOrdoAuPlusTot());
        }
        System.out.println();
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

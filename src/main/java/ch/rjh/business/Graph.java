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
     * Parcours en largeur à partir d'un noeud
     * @param node Noeud de départ
     * @return Liste de noeuds
     */
    public List<Node> widthWay(Node node) {
        reinitAllBeforeWay();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<Node> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        queue.addLast(node); // Ajout du premier Node à la queue
        node.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLargeur.add(current); // Ajout du nom à la liste qu'on va retourner
            for (Edge edge : current.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                Node destinationNode = edge.getDestination();
                if (!destinationNode.isMarked()) {
                    queue.addLast(destinationNode);
                    destinationNode.mark();
                }
            }
        }
        return nodesNameLargeur;
    }

    /**
     * Parcours en largeur à partir d'un noeud
     * @param node Noeud de départ
     * @param maxLevel Niveau de détail
     * @return Liste de noeuds
     */
    public List<Node> widthWay(Node node, int maxLevel) {
        reinitAllBeforeWay();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<Node> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        queue.addLast(node); // Ajout du premier Node à la queue
        node.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLargeur.add(current); // Ajout du nom à la liste qu'on va retourner
            if (current.getLevel() < maxLevel) {
                for (Edge edge : current.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                    Node destinationNode = edge.getDestination();
                    if (!destinationNode.isMarked()) {
                        queue.addLast(destinationNode);
                        destinationNode.mark();
                        destinationNode.setLevel(current.getLevel()+1);
                    }
                }
            }
        }
        return nodesNameLargeur;
    }

    /**
     * Parcours en largeur à partir d'un noeud
     * @param node Noeud de départ
     * @param typeClass Afficher un type de classe spécifique
     * @param maxLevel Niveau de détail
     * @return Liste de noeuds
     */
    public List<Node> widthWay(Node node, Class typeClass, int maxLevel) {
        reinitAllBeforeWay();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<Node> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noeuds parcouru
        queue.addLast(node); // Ajout du premier Node à la queue
        node.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLargeur.add(current); // Ajout du noeud à la liste qu'on va retourner
            if (current.getLevel() < maxLevel) {
                for (Edge edge : current.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                    if (edge.getClass() == typeClass) {
                        Node destinationNode = edge.getDestination();
                        if (!destinationNode.isMarked()) {
                            queue.addLast(destinationNode);
                            destinationNode.mark();
                            destinationNode.setLevel(current.getLevel()+1);
                        }
                    }
                }
            }
        }
        return nodesNameLargeur;
    }

    /**
     * Parcours en profondeur à partir d'un noeud
     * @param node Noeud de départ
     */
    public void depthWay(Node node) {
        reinitAllBeforeWay();
        LinkedList<Node> stack = new LinkedList<>();
        List<String> nodesNameLongueur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        stack.addFirst(node); // Ajout du premier Node à la pile
        node.mark(); // Premier Node marqué
        while (!stack.isEmpty()) {
            Node currentLongueur = stack.removeFirst(); // Enlève le dernier de la pile > LIFO
            nodesNameLongueur.add(currentLongueur.getName()); // Ajout du nom à la liste qu'on va retourner
            for (Edge edge : currentLongueur.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                Node destinationNode = edge.getDestination();
                if (!destinationNode.isMarked()) {
                    stack.addFirst(destinationNode);
                    destinationNode.mark();
                }
            }
        }
        System.out.println("Parcours des noeuds en longueur à partir de " + node + " : ");
        System.out.println(nodesNameLongueur);
    }

    public void dijkstra(Node startNode) {

        final int INT_MAX = Integer.MAX_VALUE;
        System.out.println("\nRUNNING DIJKSTRA...");

        // Préparation avant algorithme :
        reinitAllBeforeDijkstra();
        Node currentNode, destinationNode;
        Edge tempEdge;
        double currentWeight;
        List<Node> priorityList = new ArrayList<>();
        startNode.setDijkstraWeight(0);
        priorityList.add(startNode);

        // Algorithme :
        while (!priorityList.isEmpty()) {

            Collections.sort(priorityList, new DijkstraNodeComparator());
            currentNode = (Node)priorityList.remove(0);
            Node copyNode = currentNode;
            startNode.getVpcc().put(copyNode.getName(), copyNode);

            // ToDo : Afficher triplet
            //System.out.println();

            for (Iterator ita = currentNode.getExitingEdge().values().iterator(); ita.hasNext();) {
                tempEdge = (Edge) ita.next();
                destinationNode = tempEdge.getDestination();
                currentWeight = currentNode.getDijkstraWeight() + tempEdge.getMetric();

                // Relâchement et ajout à la liste de priorité :
                if (destinationNode.getDijkstraWeight() == INT_MAX) {
                    priorityList.add(destinationNode);
                }

                // Relachement :
                if (currentWeight < destinationNode.getDijkstraWeight()) {
                    destinationNode.setDijkstraWeight(currentWeight);
                    destinationNode.setDijkstraPred(currentNode);
                }

            }

        }

        System.out.println("DIJKSTRA DONE " + startNode.getVpcc().size());

    }

    /**
     * Réinitialise tous les noeuds du graphe avant un parcous
     */
    private void reinitAllBeforeWay() {
        for (Node node : nodeMap.values()) {
            node.reinitBeforeWay();
        }
    }

    /**
     * Réinitialise tous les noeuds du graphe avant un algorithme Dijkstra
     */
    private void reinitAllBeforeDijkstra() {
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

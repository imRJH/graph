package ch.rjh.util;

import ch.rjh.business.Edge;
import ch.rjh.business.Graph;
import ch.rjh.business.Node;
import ch.rjh.dijkstra.DijkstraNodeComparator;

import java.util.*;

public class utils {

    /**
     * Parcours en largeur à partir d'un noeud
     * @param graph Graphe sur lequel appliquer le parcous
     * @param node Noeud de départ
     * @return Liste de noeuds
     */
    public static List<Node> widthWay(Graph graph, Node node) {
        graph.reinitAllBeforeWay();
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
     * @param graph Graphe sur lequel appliquer le parcous
     * @param node Noeud de départ
     * @param maxLevel Niveau de détail
     * @return Liste de noeuds
     */
    public static List<Node> widthWay(Graph graph, Node node, int maxLevel) {
        graph.reinitAllBeforeWay();
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
     * @param graph Graphe sur lequel appliquer le parcous
     * @param node Noeud de départ
     * @param typeClass Afficher un type de classe spécifique
     * @param maxLevel Niveau de détail
     * @return Liste de noeuds
     */
    public static List<Node> widthWay(Graph graph, Node node, Class typeClass, int maxLevel) {
        graph.reinitAllBeforeWay();
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
     * @param graph Graphe sur lequel appliquer le parcous
     * @param node Noeud de départ
     * @return Liste de noeuds
     */
    public static List<Node> depthWay(Graph graph, Node node) {
        graph.reinitAllBeforeWay();
        LinkedList<Node> stack = new LinkedList<>();
        List<Node> nodesNameLongueur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        stack.addFirst(node); // Ajout du premier Node à la pile
        node.mark(); // Premier Node marqué
        while (!stack.isEmpty()) {
            Node currentLongueur = stack.removeFirst(); // Enlève le dernier de la pile > LIFO
            nodesNameLongueur.add(currentLongueur); // Ajout du nom à la liste qu'on va retourner
            for (Edge edge : currentLongueur.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                Node destinationNode = edge.getDestination();
                if (!destinationNode.isMarked()) {
                    stack.addFirst(destinationNode);
                    destinationNode.mark();
                }
            }
        }
        return nodesNameLongueur;
    }

    /**
     * Parcours selon algorithme de Dijkstra
     * @param graph Graphe sur lequel appliquer l'algorithme
     * @param startNode Noeud de départ
     */
    private static void dijkstra(Graph graph, Node startNode) {

        // Préparation avant algorithme :
        graph.reinitAllBeforeDijkstra();
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

            for (Iterator ita = currentNode.getExitingEdge().values().iterator(); ita.hasNext();) {
                tempEdge = (Edge) ita.next();
                destinationNode = tempEdge.getDestination();
                currentWeight = currentNode.getDijkstraWeight() + tempEdge.getMetric();

                // Relâchement et ajout à la liste de priorité :
                if (destinationNode.getDijkstraWeight() == Integer.MAX_VALUE) {
                    priorityList.add(destinationNode);
                }

                // Relachement :
                if (currentWeight < destinationNode.getDijkstraWeight()) {
                    destinationNode.setDijkstraWeight(currentWeight);
                    destinationNode.setDijkstraPred(currentNode);
                }

            }

        }

    }

    /**
     * Affichange en console du parcous le plus court entre deux noeuds
     * @param graph Graphe sur lequel appliquer l'algorithme
     * @param nodeSource Noeud de départ
     * @param nodeDestination Noeud de destination
     * @return Le chemin sous forme de String avec des espaces
     */
    public static String shortestPath(Graph graph, Node nodeSource, Node nodeDestination) {

        // Préparation avant algorithme :
        LinkedList<Node> path = new LinkedList<>();
        if (nodeSource.getVpcc().isEmpty()) {
            dijkstra(graph, nodeSource);
        }

        // Test si atteignable :
        Node nodeTest = nodeSource.getVpcc().get(nodeDestination.getName());
        if (nodeTest == null) {
            StringBuilder message = new StringBuilder();
            message.append("\nChemin impossible entre les noeuds demandés.");
            message.append("\nEssayer un autre argument.");
            throw new IllegalArgumentException(message.toString());
        }

        // Affichage :
        System.out.println("\nChemin le plus court entre " + nodeSource.getName() + " et " + nodeDestination.getName() + " : ");

        // Algorithme :
        while (nodeDestination != null) {
            path.addFirst(nodeDestination);
            nodeDestination = nodeDestination.getDijkstraPred();
        }

        // StringBuilder afin de retourner un objet String
        StringBuilder sb = new StringBuilder();
        sb.append("Les étapes sont : ");
        for (Node node : path) {
            sb.append(" ").append(node.getName());
        }
        sb.append("\n");
        return sb.toString();

    }

}

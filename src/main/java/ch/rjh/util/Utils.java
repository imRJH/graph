package ch.rjh.util;

import ch.rjh.business.Edge;
import ch.rjh.business.Graph;
import ch.rjh.business.Node;
import ch.rjh.dijkstra.DijkstraNodeComparator;

import java.io.*;
import java.util.*;

public class Utils {

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
            startNode.getVpcc().put(copyNode.getName(), copyNode); // Copie pour garder les bonnes valeurs dans le VPCC

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
        } else {
            System.out.println("\nChemin le plus court entre " + nodeSource.getName() + " et " + nodeDestination.getName() + " : ");
        }

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

        // Réinitialisation du VPCC du noeud source :
        nodeSource.reinitVpcc();

        return sb.toString();

    }

    private static Graph copy(Graph g) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream cout = new ObjectOutputStream(bout);
        cout.writeObject(g);
        byte[] bytes = bout.toByteArray();

        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        ObjectInputStream cin = new ObjectInputStream(bin);
        Graph clone = (Graph) cin.readObject();
        clone.setName(g.getName() + "_Copy");
        return clone;
    }

    public static void triTopologiqueAvecAttrGraphe(Graph g) throws IOException, ClassNotFoundException {

        Graph copy = copy(g);
        g.getMiseEnRang().clear();
        int rang = 0;
        boolean cycle = false;

        while (!copy.getNodeMap().isEmpty() && !cycle) {

            copy.calculDegres();
            List<Node> noeudsRangCourant = new ArrayList<Node>();

            Iterator<Node> it = copy.getNodeMap().values().iterator();
            while (it.hasNext()) {
                Node node = (Node) it.next();
                if (node.getDegInt() == 0) {
                    // Ajouter au rang le nod du graphe (g) et non sa copie (copy)
                    Node noeudCourant = g.findNode(node.getName());
                    noeudsRangCourant.add(noeudCourant);
                    it.remove();
                }
            }

            if (noeudsRangCourant.isEmpty() && !copy.getNodeMap().isEmpty()) {
                cycle = true;
            }

            g.getMiseEnRang().put(rang, noeudsRangCourant);
            rang++;

        }

    }

    public static void ordoAuPlusTot(Graph graph) {
        // Reinit
        // Application triTopologiqueAvecAttrGraphe(graph) -> Tri Topologique pour miseEnRang
        // Boucle sur la miseEnRang
        // À chaque élément de chaque rang
        // Parcourir tous les arcs sortants
        graph.reinitOrdo();
        try {
            if (graph.getMiseEnRang().isEmpty()) // Vérifie que la liste miseEnRang est vide
                triTopologiqueAvecAttrGraphe(graph); // Si liste vide, effectue la mise en rang
            List<Node> list;
            for (int rang = 0; rang < graph.getMiseEnRang().size(); rang++) {
                list = graph.getMiseEnRang().get(rang);
                for (int i = 0; i < list.size(); i++) {
                    Node node = graph.findNode(list.get(i).getName());
                    for (Iterator it = (Iterator) node.getExitingEdge().values().iterator(); it.hasNext();) {
                        Edge edge = (Edge) it.next();
                        Node dest = edge.getDestination();
                        double new_ordo = edge.getMetric() + node.getOrdoAuPlusTot();
                        if (new_ordo > dest.getOrdoAuPlusTot())
                            dest.setOrdoAuPlusTot(new_ordo);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ordoAuPlusTotAuPlusTard(Graph graph) {
        try {
            graph.reinitOrdo();
            if (graph.getMiseEnRang().isEmpty()) {
                triTopologiqueAvecAttrGraphe(graph);
                ordoAuPlusTot(graph);
            }
            List<Node> list;
            for (int rang = graph.getMiseEnRang().size() - 1; rang >= 0; rang--) {
                list = graph.getMiseEnRang().get(rang);
                if (rang == graph.getMiseEnRang().size() -1) {
                    for (int i = 0; i < list.size(); i++) {
                        Node node = graph.findNode(list.get(i).getName());
                        node.setOrdoAuPlusTard(node.getOrdoAuPlusTot());
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    Node node = graph.findNode(list.get(i).getName());
                    for (Iterator it = node.getEnteringEdge().values().iterator(); it.hasNext();) {
                        Edge edge = (Edge) it.next();
                        Node src = edge.getSource();
                        double newOrdo = node.getOrdoAuPlusTard() - edge.getMetric();
                        if (newOrdo < src.getOrdoAuPlusTard()) {
                            src.setOrdoAuPlusTard(newOrdo);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void affichageApresTri(Graph graphe) {
        graphe.getMiseEnRang().entrySet().stream().forEach(entry -> {
            int key = entry.getKey();
            List<Node> nodes = entry.getValue();
            System.out.print(key + " : ");
            nodes.forEach(node -> System.out.print(node.getName() + " "));
            System.out.println();
        });
    }

}

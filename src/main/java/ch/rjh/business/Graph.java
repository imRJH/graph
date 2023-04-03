package ch.rjh.business;

import java.util.*;
import java.util.Scanner;

public class Graph {

    // Attributes :
    private String name;
    private Map<String, Node> nodeMap;

    // Constructors :
    public Graph(String name) {
        this.name = name;
        this.nodeMap = new HashMap<>();
    }

    // Getter & Setter :
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

    // Methods :
    public Node findNode(String nodeName) {
        try {
            return nodeMap.get(nodeName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNodeExist(Node node) {
        if (nodeMap.containsKey(node.getName()))
            return true;
        throw new IllegalArgumentException();
    }

    public void addNode(Node node) {
        nodeMap.put(node.getName(), node);
    }

    public Edge findEdge(Node node, String edgeName) {
        try {
            if (isNodeExist(node)) {
                if (nodeMap.get(node.getName()).getExitingEdge().containsKey(edgeName))
                    return nodeMap.get(node.getName()).getExitingEdge().get(edgeName);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void listEnteringEdge(Node node) {
        try {
            if (isNodeExist(node)) {
                List<Edge> list = new ArrayList<>();
                for (Edge edge : node.getEnteringEdge().values()) {
                    list.add(edge);
                }
                System.out.println("Arc entrants de " + node + " :");
                System.out.println(list);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeNode(Node node) {
        try {
            if (isNodeExist(node)) {
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
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeNodeWithSource(Node node) {
        try {
            if (isNodeExist(node)) {
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
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEdge(Node nodeSource, Node nodeDestination, double metric, String edgeName) {
        try {
            if (isNodeExist(nodeSource) && isNodeExist(nodeDestination)) {
                Edge edge = new Edge(edgeName, metric, nodeDestination);
                nodeSource.getExitingEdge().put(edge.getName(), edge);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEdgeWithSource(Node nodeSource, Node nodeDestination, double metric, String edgeName) {
        try {
            if (isNodeExist(nodeSource) && isNodeExist(nodeDestination)) {
                Edge edge = new Edge(edgeName, metric, nodeSource, nodeDestination);
                nodeSource.getExitingEdge().put(edge.getName(), edge);
                nodeDestination.getEnteringEdge().put(edge.getName(), edge);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeEdge(Node node, String edgeName) {
        try {
            if (isNodeExist(node)) {
                node.getExitingEdge().remove(edgeName);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeEdgeWithSource(Node nodeSource, Node nodeDestination, String edgeName) {
        try {
            if (isNodeExist(nodeSource) && isNodeExist(nodeDestination)) {
                nodeSource.getExitingEdge().remove(edgeName);
                nodeDestination.getEnteringEdge().remove(edgeName);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void widthWay(Node node) {
        reinitAll();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<String> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        queue.addLast(node); // Ajout du premier Node à la queue
        node.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLargeur.add(current.getName()); // Ajout du nom à la liste qu'on va retourner
            for (Edge edge : current.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                Node destinationNode = edge.getDestination();
                if (!destinationNode.isMarked()) {
                    queue.addLast(destinationNode);
                    destinationNode.mark();
                }
            }
        }
        System.out.println("Parcours des noeuds en largeur à partir de " + node + " : ");
        System.out.println(nodesNameLargeur);
    }

    public void widthWay(Node node, int maxLevel) {
        reinitAll();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<String> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        queue.addLast(node); // Ajout du premier Node à la queue
        node.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node current = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLargeur.add(current.getName()); // Ajout du nom à la liste qu'on va retourner
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
        System.out.println("Parcours des noeuds en largeur à partir de " + node + " : ");
        System.out.println(nodesNameLargeur);
    }

    public List<Node> widthWay(Node node, Class typeClass, int maxLevel) {
        reinitAll();
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

    public void depthWay(Node node) {
        reinitAll();
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

    private void reinitAll() {
        for (Node node : nodeMap.values()) {
            node.reinit();
        }
    }

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

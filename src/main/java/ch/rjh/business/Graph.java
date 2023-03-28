package ch.rjh.business;

import java.util.*;
import java.util.Scanner;

public class Graph {

    // Attributes :
    private String name;
    private static Map<String, Node> nodeMap;

    // Constructors :
    public Graph(String name) {
        this.name = name;
        nodeMap = new HashMap<>();
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
    public static Node findNode(String nodeName) {
        if (nodeMap.containsKey(nodeName)) {
            return nodeMap.get(nodeName);
        }
        else {
            return null;
        }
    }

    public void addNode(Node node) {
        this.nodeMap.put(node.getName(), node);
    }

    public Edge findEdge(String nodeName, String edgeName) {
        if (nodeMap.get(nodeName).getExitingEdge().containsKey(edgeName)) {
            return nodeMap.get(nodeName).getExitingEdge().get(edgeName);
        }
        else {
            return null;
        }
    }

    public void listEnteringEdge(String nodeName) {
        List<Edge> list = new ArrayList<>();
        Node node = findNode(nodeName);
        for (Edge edge : node.getEnteringEdge().values()) {
            list.add(edge);
        }
        System.out.println("Arc entrants de " + nodeName + " :");
        System.out.println(list);
    }

    public void removeNode(String nodeName) {
        for (Node node : this.nodeMap.values()) {
            Iterator<Edge> iteEdge = node.getExitingEdge().values().iterator();
            while (iteEdge.hasNext()) {
                Edge edge = iteEdge.next();
                if (nodeName.equals(edge.getDestination().getName())) {
                    iteEdge.remove();
                }
            }
        }
        nodeMap.remove(nodeName);
    }

    public void removeNodeWithSource(String nodeName) {
        for (Node node : this.nodeMap.values()) {
            Iterator<Edge> exitingEdge = node.getExitingEdge().values().iterator();
            while (exitingEdge.hasNext()) {
                Edge edge = exitingEdge.next();
                if (nodeName.equals(edge.getDestination().getName())) {
                    exitingEdge.remove();
                }
            }
            Iterator<Edge> enteringEdge = node.getEnteringEdge().values().iterator();
            while (enteringEdge.hasNext()) {
                Edge edge = enteringEdge.next();
                if (nodeName.equals(edge.getSource().getName())) {
                    enteringEdge.remove();
                }
            }
        }
        nodeMap.remove(nodeName);
    }

    public void addEdge(String nodeSource, String nodeDestination, double metric, String edgeName) {
        if(null == findNode(nodeSource)) {
            Node node = new Node(nodeSource);
            nodeMap.put(nodeSource, node);
        }
        if(findNode(nodeDestination) == null){
            Node node = new Node(nodeDestination);
            nodeMap.put(nodeDestination, node);
        }
        Edge edge = new Edge(edgeName, metric, findNode(nodeDestination));
        findNode(nodeSource).getExitingEdge().put(edge.getName(), edge);
    }

    public void addEdgeWithSource(String nodeSource, String nodeDestination, double metric, String edgeName) {
        if(null == findNode(nodeSource)) {
            Node node = new Node(nodeSource);
            nodeMap.put(nodeSource, node);
        }
        if(null == findNode(nodeDestination)){
            Node node = new Node(nodeDestination);
            nodeMap.put(nodeDestination, node);
        }
        Edge edge = new Edge(edgeName, metric, findNode(nodeSource), findNode(nodeDestination));
        findNode(nodeSource).getExitingEdge().put(edge.getName(), edge);
        findNode(nodeDestination).getEnteringEdge().put(edge.getName(), edge);
    }

    public void removeEdge(String nodeName, String edgeName) {
        findNode(nodeName).getExitingEdge().remove(edgeName);
    }

    public void removeEdgeWithSource(String nodeSource, String nodeDestination, String edgeName) {
        findNode(nodeSource).getExitingEdge().remove(edgeName);
        findNode(nodeDestination).getEnteringEdge().remove(edgeName);
    }

    public void widthWay(String departNodeNameWidth) {
        reinitAll();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<String> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        Node firstNodeLargeur = findNode(departNodeNameWidth); // Création du premier objet Node
        queue.addLast(firstNodeLargeur); // Ajout du premier Node à la queue
        firstNodeLargeur.mark(); // Premier Node marqué
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
        System.out.println("Parcours des noeuds en largeur à partir de " + departNodeNameWidth + " : ");
        System.out.println(nodesNameLargeur.toString());
    }

    public void widthWay(String departNodeNameWidth, int maxLevel) {
        reinitAll();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<String> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        Node firstNodeLargeur = findNode(departNodeNameWidth); // Création du premier objet Node
        queue.addLast(firstNodeLargeur); // Ajout du premier Node à la queue
        firstNodeLargeur.mark(); // Premier Node marqué
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
        System.out.println("Parcours des noeuds en largeur à partir de " + departNodeNameWidth + " : ");
        System.out.println(nodesNameLargeur.toString());
    }

    public List<Node> widthWay(String departNodeNameWidth, Class typeClass, int maxLevel) {
        reinitAll();
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<Node> nodesNameLargeur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noeuds parcouru
        Node firstNodeLargeur = findNode(departNodeNameWidth); // Création du premier objet Node
        queue.addLast(firstNodeLargeur); // Ajout du premier Node à la queue
        firstNodeLargeur.mark(); // Premier Node marqué
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
        //System.out.println("Parcours des noeuds en largeur à partir de " + departNodeNameWidth + " : ");
        //System.out.println(nodesNameLargeur.toString());
    }

    public void depthWay(String departNodeNameLength) {
        reinitAll();
        LinkedList<Node> stack = new LinkedList<>();
        List<String> nodesNameLongueur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        Node firstNodeLongueur = findNode(departNodeNameLength); // Création du premier objet Node
        stack.addFirst(firstNodeLongueur); // Ajout du premier Node à la pile
        firstNodeLongueur.mark(); // Premier Node marqué
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
        System.out.println("Parcours des noeuds en longueur :");
        System.out.println(nodesNameLongueur.toString());
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

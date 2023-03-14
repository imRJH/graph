package ch.rjh.business;

import java.util.*;

public class Graph {

    // Attributes :
    private String name;
    private Map<String, Node> nodeMap;

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
    public Node findNode(String nodeName) {
        if (nodeName.contains(nodeName)) {
            return nodeMap.get(nodeName);
        }
        else {
            return null;
        }
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
        if (findNode(nodeSource) == null ) {
            Node node = new Node(nodeSource);
            nodeMap.put(node.getName(), node);
        }
        if (findNode(nodeDestination) == null) {
            Node node = new Node(nodeDestination);
            nodeMap.put(node.getName(), node);
        }
        Edge edge = new Edge(edgeName, metric, findNode(nodeDestination));
        findNode(nodeSource).getExitingEdge().put(edge.getName(), edge);
    }

    public void addEdgeWithSource(String nodeSource, String nodeDestination, double metric, String edgeName) {
        if (findNode(nodeSource) == null ) {
            Node node = new Node(nodeSource);
            nodeMap.put(node.getName(), node);
        }
        if (findNode(nodeDestination) == null) {
            Node node = new Node(nodeDestination);
            nodeMap.put(node.getName(), node);
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

    public List<String> parcoursLargeur(String departNodeNameLargeur) {
        LinkedList<Node> queue = new LinkedList<>(); // Création de la queue > FIFO
        List<String> nodesNameLongueur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        Node firstNodeLargeur = findNode(departNodeNameLargeur); // Création du premier objet Node
        queue.addLast(firstNodeLargeur); // Ajout du premier Node à la queue
        firstNodeLargeur.mark(); // Premier Node marqué
        while (!queue.isEmpty()) {
            Node currentLargeur = queue.removeFirst(); // Enlève le premier de la queue
            nodesNameLongueur.add(currentLargeur.getName()); // Ajout du nom à la liste qu'on va retourner
            for (Edge edge : currentLargeur.getExitingEdge().values()) { // // Parcours de tous les noms sortant du Node courant
                Node destinationNode = edge.getDestination();
                if (!destinationNode.isMarked()) {
                    queue.addLast(destinationNode);
                    destinationNode.mark();
                }
            }
        }
        return nodesNameLongueur;
    }

    public List<String> parcoursLongueur(String departNodeNameLongueur) {
        LinkedList<Node> stack = new LinkedList<>();
        List<String> nodesNameLongueur = new ArrayList<>(); // Création de la liste qu'on va retourner, contenant les noms des noeuds parcourus
        Node firstNodeLongueur = findNode(departNodeNameLongueur); // Création du premier objet Node
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
        return nodesNameLongueur;
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

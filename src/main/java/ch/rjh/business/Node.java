package ch.rjh.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    private String name;
    private int level;
    private boolean isMarked;
    private Map<String, Edge> enteringEdge;
    private Map<String, Edge> exitingEdge;

    public Node(String name) {
        this.name = name;
        this.level = 0;
        this.isMarked = false;
        enteringEdge = new HashMap<>();
        exitingEdge = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<String, Edge> getEnteringEdge() {
        return enteringEdge;
    }

    public void setEnteringEdge(Map<String, Edge> enteringEdge) {
        this.enteringEdge = enteringEdge;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public Map<String, Edge> getExitingEdge() {
        return exitingEdge;
    }

    public void setExitingEdge(Map<String, Edge> exitingEdge) {
        this.exitingEdge = exitingEdge;
    }

    /**
     * Réaliser un marquage sur un noeud (lors d'un parcous)
     */
    public void mark() {
        this.setMarked(true);
    }

    /**
     * Réinitialise un noeud (avant un parcours) :
     * Le niveau est remis par défaut
     * Le marquage est supprimé
     */
    public void reinit() {
        this.level = 0;
        this.isMarked = false;
    }

    /**
     * Ajouter un arc à un noeud
     * @param edgeName Nom de l'arc
     * @param nodeDestination Noeud de destination
     */
    public void addEdge(String edgeName, Node nodeDestination) {
        Edge edge = new Edge(edgeName, nodeDestination);
        this.getExitingEdge().put(edge.getName(), edge);
    }

    /**
     * Ajouter un arc à un noeud
     * @param edgeName Nom de l'arc
     * @param nodeDestination Noeud de destination
     * @param metric Valeur de l'arc
     */
    public void addEdge(String edgeName, Node nodeDestination, double metric) {
        Edge edge = new Edge(edgeName, nodeDestination, metric);
        this.getExitingEdge().put(edge.getName(), edge);
    }

    /**
     * Ajouter un arc à un noeud en gérant également l'arc entrant pour le noeud de destination
     * @param edgeName Nom de l'arc
     * @param nodeDestination Noeud de destination
     * @param metric Valeur de l'arc
     */
    public void addEdgeWithSource(String edgeName, Node nodeDestination, double metric) {
        Edge edge = new Edge(edgeName, this, nodeDestination, metric);
        this.getExitingEdge().put(edge.getName(), edge);
        nodeDestination.getEnteringEdge().put(edge.getName(), edge);
    }

    /**
     * Supprimer un arc d'un noeud
     * @param edgeName Nom de l'arc à supprimer
     */
    public void removeEdge(String edgeName) {
        this.getExitingEdge().remove(edgeName);
    }

    /**
     * Supprimer un arc d'un noeud en gérant également l'arc entrant pour le noeud de destination
     * @param edgeName Nom de l'arc à supprimer
     * @param nodeDestination Noeud de destination
     */
    public void removeEdgeWithSource(String edgeName, Node nodeDestination) {
        this.getExitingEdge().remove(edgeName);
        nodeDestination.getEnteringEdge().remove(edgeName);
    }

    /**
     * Lister tous les arcs entrants d'un noeud
     */
    public void listEnteringEdge() {
        List<Edge> list = new ArrayList<>();
        for (Edge edge : this.getEnteringEdge().values()) {
            list.add(edge);
        }
        System.out.println("Arc entrants de " + this + " :");
        System.out.println(list);
    }

    /**
     * Lister tous les arcs sortants d'un noeud
     */
    public void listExitingEdge() {
        List<Edge> list = new ArrayList<>();
        for (Edge edge : this.getExitingEdge().values()) {
            list.add(edge);
        }
        System.out.println("Arc sortants de " + this + " :");
        System.out.println(list);
    }

    /**
     * Nouvelle méthode toString()
     * @return Un objet String à afficher
     */
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", exitingEdge=" + exitingEdge +
                '}';
    }

}

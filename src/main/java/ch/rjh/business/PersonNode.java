package ch.rjh.business;

public class PersonNode extends Node {

    private String ville;

    public PersonNode(String name, String ville) {
        super(name);
        this.ville = ville;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void addFriend(String edgeName, PersonNode friend) {
        Edge edge = new IsFriendWithEdge(edgeName, this, friend);
        friend.getEnteringEdge().put(edgeName, edge);
        this.getExitingEdge().put(edgeName, edge);
    }

    public void addListening(String edgeName, ListeningNode listen) {
        Edge edge = new IsListeningEdge(edgeName, listen);
        listen.getEnteringEdge().put(edgeName, edge);
        this.getExitingEdge().put(edgeName, edge);
    }

    public void addWatching(String edgeName, WatchingNode watch) {
        Edge edge = new IsWatchingEdge(edgeName, watch);
        watch.getEnteringEdge().put(edgeName, edge);
        this.getExitingEdge().put(edgeName, edge);
    }

}

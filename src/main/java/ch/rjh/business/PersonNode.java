package ch.rjh.business;

public class PersonNode extends Node {

    private String ville;
    private String name;

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
        Edge edge = new IsFriendWithEdge(edgeName, Graph.findNode(this.getName()), Graph.findNode(friend.getName()));
        Graph.findNode(friend.getName()).getEnteringEdge().put(edgeName, edge);
        Graph.findNode(this.getName()).getExitingEdge().put(edgeName, edge);
    }

    public void addListening(String edgeName, ListeningNode listen) {
        Edge edge = new IsListeningEdge(edgeName, Graph.findNode(listen.getName()));
        Graph.findNode(listen.getName()).getEnteringEdge().put(edgeName, edge);
        Graph.findNode(this.getName()).getExitingEdge().put(edgeName, edge);
    }

    public void addWatching(String edgeName, WatchingNode watch) {
        Edge edge = new IsWatchingEdge(edgeName, Graph.findNode(watch.getName()));
        Graph.findNode(watch.getName()).getEnteringEdge().put(edgeName, edge);
        Graph.findNode(this.getName()).getExitingEdge().put(edgeName, edge);
    }

}

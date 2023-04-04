package ch.rjh.application;

import ch.rjh.business.*;

public class Program {
    public static void main(String[] args) {
        //new Program().multipleNodeTypeWay();
        //new Program().friendshipGraph();
        //new Program().firstGraph();
    }

    private void multipleNodeTypeWay() {

        Graph graphe = new Graph("Exercice");

        PersonNode lucie = new PersonNode("Lucie", "Neuchâtel");
        PersonNode paul = new PersonNode("Paul", "Neuchâtel");
        PersonNode albert = new PersonNode("Albert", "Lausanne");
        PersonNode julie = new PersonNode("Julie", "Cernier");
        PersonNode jean = new PersonNode("Jean", "Neuchâtel");
        PersonNode alfred = new PersonNode("Alfred", "Lausanne");

        WatchingNode disney = new WatchingNode("Disney+");
        WatchingNode amazon = new WatchingNode("Amazon Prime Video");
        WatchingNode netflix = new WatchingNode("Netflix");

        graphe.addNode(lucie);
        graphe.addNode(paul);
        graphe.addNode(albert);
        graphe.addNode(julie);
        graphe.addNode(jean);
        graphe.addNode(alfred);
        graphe.addNode(disney);
        graphe.addNode(amazon);
        graphe.addNode(netflix);

        albert.addWatching("a1", amazon);
        albert.addWatching("a2", disney);
        julie.addFriend("a3", albert);
        paul.addFriend("a4", julie);
        paul.addWatching("a5", amazon);
        paul.addFriend("a6", jean);
        paul.addFriend("a7", lucie);
        paul.addWatching("a8", netflix);
        lucie.addWatching("a9", amazon);
        lucie.addWatching("a10", netflix);
        jean.addFriend("a11", alfred);
        alfred.addWatching("a12", netflix);

        /*
        Question 1 :
        Lister tous les sites de streaming regardés par Paul.
         */
        System.out.println();
        for (Node node : graphe.widthWay(paul, IsWatchingEdge.class, 1)) {
            System.out.print(node.getName() + "; ");
        }

        /*
        Question 2 :
        Donner tous les amis de Paul jusqu’au 2e niveau qui regardent un site de streaming.
         */
        System.out.println();
        for (Node node : graphe.widthWay(paul, IsFriendWithEdge.class, 2)) {
            if(node.getExitingEdge().values().stream().anyMatch(edge -> edge instanceof IsWatchingEdge))
                System.out.print(node.getName() + "; ");
        }

        /*
        Question 3 :
        Lister tous les amis (1er niveau) de Paul qui habitent à NE et qui regardent Amazon Prime Video.
         */
        System.out.println();
        for (Node node : graphe.widthWay(paul, IsFriendWithEdge.class, 1)) {
            if (((PersonNode)node).getVille().equals("Neuchâtel")) {
                if (graphe.widthWay(paul, IsWatchingEdge.class,1).contains(amazon)) {
                    System.out.print(node.getName() + "; ");
                }
            }
        }

    }

    private void friendshipGraph() {

        // Création du graphe :
        Graph graphe = new Graph("Amitiés");

        // Création des personnes :
        PersonNode jean = new PersonNode("Jean", "Neuchâtel");
        PersonNode paul = new PersonNode("Paul", "Bienne");
        PersonNode carlos = new PersonNode("Carlos", "Lausanne");
        PersonNode julie = new PersonNode("Julie", "Yverdon-les-Bains");

        // Création de services :
        ListeningNode spotify = new ListeningNode("Spotify");
        ListeningNode applePodcast = new ListeningNode("Apple Podcast");
        WatchingNode youtube = new WatchingNode("YouTube");
        WatchingNode netflix = new WatchingNode("Netflix");

        // Ajout des noeuds :
        graphe.addNode(jean);
        graphe.addNode(paul);
        graphe.addNode(carlos);
        graphe.addNode(julie);
        graphe.addNode(spotify);
        graphe.addNode(applePodcast);
        graphe.addNode(youtube);
        graphe.addNode(netflix);

        // Ajout d'amitiés :
        jean.addFriend("a1", paul);
        jean.addFriend("a2", carlos);
        carlos.addFriend("a3", jean);
        carlos.addFriend("a4", julie);

        // Ajout d'écoutes ou visualisations :
        julie.addListening("a5", spotify);
        jean.addListening("a6", applePodcast);
        jean.addWatching("a7", youtube);
        carlos.addWatching("a8", netflix);

        // Afficher le graphe :
        System.out.println(graphe);

        // Parcours du graphe en largeur avec un niveau :
        graphe.widthWay(jean, IsFriendWithEdge.class,2);
        graphe.widthWay(jean, IsFriendWithEdge.class,1);
        graphe.widthWay(julie, IsListeningEdge.class,1);

    }

    private void firstGraph() {

        // Création du graphe :
        Graph g = new Graph("G1");

        // Création des noeuds :
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");

        // Ajout des noeuds :
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);

        // Ajout d'edges :
        a.addEdge("u1", c);
        a.addEdge("u2", b);
        a.addEdge("u3", d);
        b.addEdge("u4", d);
        c.addEdge("u5", d);
        d.addEdge("u6", e);
        c.addEdge("u7", e);
        System.out.println(g);

        // Pacours en largeur avec un niveau :
        g.widthWay(a, 1);

    }

}
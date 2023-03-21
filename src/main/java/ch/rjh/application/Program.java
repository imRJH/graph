package ch.rjh.application;

import ch.rjh.business.*;

public class Program {
    public static void main(String[] args) {
        amitie();
        //graph();
    }

    private static void amitie() {

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
        System.out.println(graphe.toString());

        // Parcours du graphe en largeur avec un niveau :
        graphe.widthWay(jean.getName(), IsFriendWithEdge.class,1);
        graphe.widthWay(julie.getName(), IsListeningEdge.class,1);

    }

    private static void graph() {

        // Création du graphe :
        Graph g = new Graph("G1");
        g.addEdgeWithSource("A","C",1,"u1");
        g.addEdgeWithSource("A","B",1,"u2");
        g.addEdgeWithSource("A","D",1,"u3");
        g.addEdgeWithSource("B","D",1,"u4");
        g.addEdgeWithSource("C","D",1,"u5");
        g.addEdgeWithSource("D","E",1,"u6");
        g.addEdgeWithSource("C","E",1,"u7");
        System.out.println(g.toString());

        // Pacours en largeur avec un niveau :
        g.widthWay("A", 1);

    }

}
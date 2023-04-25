package ch.rjh.application;

import ch.rjh.business.*;
import ch.rjh.util.Utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        new Program().firstTriTopologique();
        //new Program().cantonNeuchatel();
        //new Program().firstDijkstra();
        //new Program().multipleNodeTypeWay();
        //new Program().friendshipGraph();
        //new Program().firstGraph();
    }

    private void firstTriTopologique() {

        Graph graphe = new Graph("First Tri Topologique");

        Node a = new Node("A"),
                b = new Node("B"),
                c = new Node("C"),
                d = new Node("D");

        graphe.addNode(a);
        graphe.addNode(b);
        graphe.addNode(c);
        graphe.addNode(d);

        b.addEdge("u1", a, 5);
        a.addEdge("u2", c, 5);
        a.addEdge("u3", d, 3);
        b.addEdge("u4", d, 10);
        d.addEdge("u5", c, 1);

        try {
            Utils.triTopologiqueAvecAttrGraphe(graphe);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        graphe.getMiseEnRang().values().stream()
                .flatMap(List::stream)
                .forEach(node -> System.out.println(node.getName()));

    }

    private void cantonNeuchatel() {

        Graph graphe = new Graph("Canton de Neuchâtel");

        Node neuchatel = new Node("Neuchâtel"),
                laChauxDeFonds = new Node("Chaux-de-Fonds"),
                leLocle = new Node("Le Locle"),
                lesPontsDeMartel = new Node("Les Ponts De Martel"),
                boudry = new Node("Boudry"),
                cernier = new Node("Cernier"),
                stImier = new Node("St. Imier"),
                i1 = new Node("NeuchatelBoudryCouvetLesPontsDeMartel"),
                i2 = new Node("NeuchatelCernierChauxDeFonds"),
                i3 = new Node("NeuchatelCernierStImierRouge"),
                i4 = new Node("NeuchatelCernierStImierOrange")
                        ;

        graphe.addNode(neuchatel);
        graphe.addNode(laChauxDeFonds);
        graphe.addNode(leLocle);
        graphe.addNode(lesPontsDeMartel);
        graphe.addNode(boudry);
        graphe.addNode(cernier);
        graphe.addNode(stImier);
        graphe.addNode(i1);
        graphe.addNode(i2);
        graphe.addNode(i3);
        graphe.addNode(i4);

        // Chaux-de-Fonds <-> St-Imier :
        laChauxDeFonds.addEdge("u1", stImier, 20);
        stImier.addEdge("u2", laChauxDeFonds, 20);

        // Chaux-de-Fonds <-> Le Locle :
        laChauxDeFonds.addEdge("u3", leLocle, 10);
        leLocle.addEdge("u4", laChauxDeFonds, 10);

        // Chaux-de-Fonds <-> i2 :
        laChauxDeFonds.addEdge("u5", i2, 10);
        i2.addEdge("u6", laChauxDeFonds, 10);

        // Le Locle <-> Les Ponts de Martel :
        leLocle.addEdge("u7", lesPontsDeMartel, 15);
        lesPontsDeMartel.addEdge("u8", leLocle, 15);

        // Les Ponts de Martel <-> i1
        lesPontsDeMartel.addEdge("u9", i1, 14);
        i1.addEdge("u10", lesPontsDeMartel, 14);

        // Boudry <-> i1 :
        boudry.addEdge("u11", i1, 5);
        i1.addEdge("u12", boudry, 5);

        // Boudry <-> Neuchâtel :
        boudry.addEdge("u13", neuchatel, 11);
        neuchatel.addEdge("u14", boudry, 11);

        // Neuchâtel <-> i1 :
        neuchatel.addEdge("u15", i1, 12);
        i1.addEdge("u16", neuchatel, 12);

        // Neuchâtel <-> i3 :
        neuchatel.addEdge("u17", i3, 2);
        i3.addEdge("u18", neuchatel, 2);

        // i3 <-> i4 :
        i3.addEdge("u19", i4, 5);
        i4.addEdge("u20", i3, 5);

        // i2 <-> i3 :
        i2.addEdge("u21", i3, 15);
        i3.addEdge("u22", i2, 15);

        // Cernier <-> i2 :
        cernier.addEdge("u23", i2, 1);
        i2.addEdge("u24", cernier, 1);

        // Cernier <-> i4 :
        cernier.addEdge("u25", i4, 4);
        i4.addEdge("u26", cernier, 4);

        // i4 <-> St-Imer :
        i4.addEdge("u27", stImier, 10);
        stImier.addEdge("u28", i4, 10);

        System.out.println(Utils.shortestPath(graphe, laChauxDeFonds, cernier));
        System.out.println(Utils.shortestPath(graphe, cernier, laChauxDeFonds));

    }

    private void firstDijkstra() {

        Graph graphe = new Graph("Exercice");

        Node a = new Node("A"),
                b = new Node("B"),
                c = new Node("C"),
                d = new Node("D"),
                e = new Node("E"),
                f = new Node("F"),
                g = new Node("G"),
                h = new Node("H");

        graphe.addNode(a);
        graphe.addNode(b);
        graphe.addNode(c);
        graphe.addNode(d);
        graphe.addNode(e);
        graphe.addNode(f);
        graphe.addNode(g);
        graphe.addNode(h);

        a.addEdge("u1", b, 4);
        b.addEdge("u2", c, 5);
        c.addEdge("u3", e, 10);
        b.addEdge("u4", d, 8);
        d.addEdge("u5", c, 5);
        c.addEdge("u6", f, 3);
        f.addEdge("u7", e, 2);
        d.addEdge("u8", f, 3);
        g.addEdge("u9", f, 4);
        d.addEdge("u10", g, 2);
        a.addEdge("u11", g, 1);
        h.addEdge("u12", a, 2);
        h.addEdge("u12", g, 9);
        d.addEdge("u14", a, 1);

        System.out.println(Utils.shortestPath(graphe, h, e));

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

        albert.addWatching("a1", amazon, 1);
        albert.addWatching("a2", disney, 1);
        julie.addFriend("a3", albert, 1);
        paul.addFriend("a4", julie, 1);
        paul.addWatching("a5", amazon, 1);
        paul.addFriend("a6", jean, 1);
        paul.addFriend("a7", lucie, 1);
        paul.addWatching("a8", netflix, 1);
        lucie.addWatching("a9", amazon, 1);
        lucie.addWatching("a10", netflix, 1);
        jean.addFriend("a11", alfred, 1);
        alfred.addWatching("a12", netflix, 1);

        /*
        Question 1 :
        Lister tous les sites de streaming regardés par Paul.
         */
        System.out.println("\nQuestion 1 :");
        for (Node node : Utils.widthWay(graphe, paul, IsWatchingEdge.class, 1)) {
            System.out.print(node.getName() + "; ");
        }

        /*
        Question 2 :
        Donner tous les amis de Paul jusqu’au 2e niveau qui regardent un site de streaming.
         */
        System.out.println("\nQuestion 2 :");
        for (Node node : Utils.widthWay(graphe, paul, IsFriendWithEdge.class, 2)) {
            if(node.getExitingEdge().values().stream().anyMatch(edge -> edge instanceof IsWatchingEdge))
                System.out.print(node.getName() + "; ");
        }

        /*
        Question 3 :
        Lister tous les amis (1er niveau) de Paul qui habitent à NE et qui regardent Amazon Prime Video.
         */
        System.out.println("\nQuestion 3 :");
        for (Node node : Utils.widthWay(graphe, paul, IsFriendWithEdge.class, 1)) {
            if (((PersonNode)node).getVille().equals("Neuchâtel")) {
                if (Utils.widthWay(graphe, node, IsWatchingEdge.class,1).contains(amazon)) {
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
        jean.addFriend("a1", paul, 1);
        jean.addFriend("a2", carlos, 1);
        carlos.addFriend("a3", jean, 1);
        carlos.addFriend("a4", julie, 1);

        // Ajout d'écoutes ou visualisations :
        julie.addListening("a5", spotify, 1);
        jean.addListening("a6", applePodcast, 1);
        jean.addWatching("a7", youtube, 1);
        carlos.addWatching("a8", netflix, 1);

        // Parcours du graphe en largeur avec un niveau :
        System.out.println();
        for (Node node : Utils.widthWay(graphe, jean, IsFriendWithEdge.class,2)) {
            System.out.print(node.getName() + "; ");
        }
        System.out.println();
        for (Node node : Utils.widthWay(graphe, jean, IsFriendWithEdge.class,1)) {
            System.out.print(node.getName() + "; ");
        }
        System.out.println();
        for (Node node : Utils.widthWay(graphe, julie, IsListeningEdge.class,1)) {
            System.out.print(node.getName() + "; ");
        }

    }

    private void firstGraph() {

        // Création du graphe :
        Graph graph = new Graph("G1");

        // Création des noeuds :
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");

        // Ajout des noeuds :
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addNode(e);

        // Ajout d'edges :
        a.addEdge("u1", c, 1);
        a.addEdge("u2", b, 1);
        a.addEdge("u3", d, 1);
        b.addEdge("u4", d, 1);
        c.addEdge("u5", d, 1);
        d.addEdge("u6", e, 1);
        c.addEdge("u7", e, 1);
        System.out.println(graph);

        // Pacours en largeur avec un niveau :
        Utils.widthWay(graph, a, 1);

    }

}
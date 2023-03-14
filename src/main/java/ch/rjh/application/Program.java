package ch.rjh.application;

import ch.rjh.business.Graph;

public class Program {
    public static void main(String[] args) {
        Graph g = new Graph("G1");
        g.addEdgeWithSource("A","C",1,"u1");
        g.addEdgeWithSource("A","B",1,"u2");
        g.addEdgeWithSource("A","D",1,"u3");
        g.addEdgeWithSource("B","D",1,"u4");
        g.addEdgeWithSource("C","D",1,"u5");
        g.addEdgeWithSource("D","E",1,"u6");
        g.addEdgeWithSource("C","E",1,"u7");
        System.out.println(g.toString());

        /*g.removeNodeWithSource("A");
        System.out.println(g.toString());*/
        System.out.println(g.parcoursLargeur("A"));
        //System.out.println(g.parcoursLongueur("A"));
    }
}
package ch.rjh.application;

import ch.rjh.business.Graph;

public class Program {
    public static void main(String[] args) {
        Graph g = new Graph("G1");
        g.addEdgeWithSource("x1","x2",1,"u1");
        g.addEdgeWithSource("x2","x3",1,"u2");
        g.addEdgeWithSource("x3","x2",1,"u3");
        g.addEdgeWithSource("x2","x2",1,"u4");
        System.out.println(g.toString());

        g.removeNode("x2");
        System.out.println(g.toString());
    }
}


/** test AdjacencyWDigraph.shortestPaths */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestShortestPaths
{
   public static void main(String [] args)
   {
      int numberOfTypes = 2;
      // create a graph of each adjacency weighted type
      AdjacencyWDigraph [] g = new AdjacencyWDigraph [numberOfTypes];

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();

      // input a test graph
      System.out.println("Enter number of vertices and edges");
      int n = keyboard.readInteger();
      int e = keyboard.readInteger();
      g[0] = new AdjacencyWGraph(n);
      g[1] = new AdjacencyWDigraph(n);
      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter weighted edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         int w = keyboard.readInteger();
         g[0].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
         g[1].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
      }

      System.out.println("The weighted undirected graph is");
      g[0].output();
      System.out.println("\nThe weighted digraph is");
      g[1].output();

      // test shortestPaths
      MyInteger [] dist = new MyInteger [n + 1];
      int [] p = new int [n + 1];
      g[0].shortestPaths(1, dist, p);
      System.out.println("The dist and p values for the undirected graph are");
      for (int i = 1; i <= n; i++)
         System.out.println(dist[i] + " " + p[i]);

      g[1].shortestPaths(1, dist, p);
      System.out.println("\nThe dist and p values for the directed graph are");
      for (int i = 1; i <= n; i++)
         System.out.println(dist[i] + " " + p[i]);
   }
}

/** test Graph.bellmanFord */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestBellmanFord
{
   public static void main(String [] args)
   {
      int numberOfTypes = 4;
      // create a graph of each weighted type
      Graph [] g = new Graph [numberOfTypes];

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();

      // input a test undirected graph
      System.out.println("Enter number of vertices and edges");
      int n = keyboard.readInteger();
      int e = keyboard.readInteger();
      g[0] = new AdjacencyWGraph(n);
      g[1] = new LinkedWGraph(n);
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
      ((AdjacencyWGraph) g[0]).output();

      // input a test directed graph
      System.out.println("Enter number of vertices and edges");
      n = keyboard.readInteger();
      e = keyboard.readInteger();
      g[2] = new AdjacencyWDigraph(n);
      g[3] = new LinkedWDigraph(n);
      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter weighted edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         int w = keyboard.readInteger();
         g[2].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
         g[3].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
      }

      System.out.println("\nThe weighted digraph is");
      ((AdjacencyWDigraph) g[2]).output();

      // test bellmanFord
      for (int s = 0; s < numberOfTypes; s++)
      {
         MyInteger [] dist = new MyInteger [n + 1];
         int [] p = new int [n + 1];
         g[s].bellmanFord(1, dist, p, new MyInteger(0));
         System.out.println("The dist and p values for " +
                            "the graph are");
         for (int i = 1; i <= g[s].vertices(); i++)
            System.out.println(dist[i] + " " + p[i]);
      }
   }
}

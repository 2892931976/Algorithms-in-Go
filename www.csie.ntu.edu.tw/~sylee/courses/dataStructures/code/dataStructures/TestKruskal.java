
/** test Graph.kruskal */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestKruskal
{
   public static void main(String [] args)
   {
      int numberOfTypes = 2;
      // create a graph of each weighted undirected type
      Graph [] g = new Graph [numberOfTypes];
      int n = 7;
      g[0] = new AdjacencyWGraph(n);
      g[1] = new LinkedWGraph(n);

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();
      // input a test graph
      System.out.println("enter number of edges in 5 vertex graph");
      int e = keyboard.readInteger();
      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter weighted edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         int w = keyboard.readInteger();
         g[0].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
         g[1].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
      }

      System.out.println("The adjacency matrix is");
      ((AdjacencyWGraph) g[0]).output();
      System.out.println("\nThe linked lists are");
      ((LinkedWGraph) g[1]).output();

      // test kruskal
      WeightedEdge [] edge = new WeightedEdge [n];
      System.out.println("Using cost-adjacency matrix");
      if (g[0].kruskal(edge))
      {// found the spanning tree
         System.out.println("The min cost spanning tree edges are");
         for (int i = 0; i < n - 1; i++)
            System.out.println(edge[i]);
      }

      System.out.println("\nUsing linked-adjacency lists");
      if (g[1].kruskal(edge))
      {// found the spanning tree
         System.out.println("The min cost spanning tree edges are");
         for (int i = 0; i < n - 1; i++)
            System.out.println(edge[i]);
      }
   }
}

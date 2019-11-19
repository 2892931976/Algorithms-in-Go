

/** test AdjacencyGraph.btMaxClique */

package dataStructures;

import utilities.*;

public class TestBTMaxClique
{
   public static void main(String [] args)
   {
      int n = 7;
      AdjacencyGraph g = new AdjacencyGraph(n);

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();

      // input a test graph
      System.out.println("enter number of edges in 7 vertex graph");
      int e = keyboard.readInteger();
      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter unweighted edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         g.putEdge(new Edge(u, v));
      }

      System.out.println("The undirected graph is");
      g.output();

      // test btMaxClique
      int [] v = new int [n + 1];
      System.out.println("\nMax Clique size is " + g.btMaxClique(v));
      System.out.print("\nSolution vector is ");
      for (int i = 1; i <= n; i++)
         System.out.print(v[i] + " ");
      System.out.println();
   }
}

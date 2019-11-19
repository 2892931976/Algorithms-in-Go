
/** test AdjacencyGraph.maxProfitBBMaxClique */

package dataStructures;

import utilities.*;

public class TestMaxProfitBBMaxClique
{
   public static void main(String [] args)
   {
      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();

      // input a test graph
      System.out.println("Enter number of vertices and edges");
      int n = keyboard.readInteger();
      int e = keyboard.readInteger();
      AdjacencyGraph g = new AdjacencyGraph(n);

      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         g.putEdge(new Edge(u, v));
      }
      System.out.println("The undirected graph is");
      g.output();

      int [] c = new int [n + 1];
      System.out.println("The max clique size is " + g.maxProfitBBMaxClique(c));
      System.out.print("The vertex selection vector is ");
      for (int i = 1; i <= n; i++)
         System.out.print(c[i] + " ");
      System.out.println();
   }
}

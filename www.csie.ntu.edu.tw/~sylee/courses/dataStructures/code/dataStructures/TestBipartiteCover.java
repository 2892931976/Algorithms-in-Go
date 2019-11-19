
/** test Graph.bipartiteCover */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestBipartiteCover
{
   public static void main(String [] args)
   {
      int n = 17;
      int e = 25;
      LinkedGraph g = new LinkedGraph(n);

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();
      // input a test graph
      System.out.println("enter a 25 edge 17 vertex bipartite graph");
      for (int i = 1; i <= e; i++)
      {
         System.out.println("enter edge " + i);
         int u = keyboard.readInteger();
         int v = keyboard.readInteger();
         g.putEdge(new Edge(u, v));
      }
      System.out.println("The undirected graph is");
      g.output();

      int [] l = new int [n + 1];
      System.out.println("\nEnter vertex labels");
      for (int i = 1; i <= n; i++)
         l[i] = keyboard.readInteger();

      System.out.print("\nThe vertex labels are ");
      for (int i = 1; i <= n; i++)
         System.out.print(l[i] + " ");
      System.out.println();

      int [] c = new int [n];
      int size = g.bipartiteCover(l, c);
      if (size == -1) // no cover
         System.out.println("Did not find a cover");
      else
      {
         System.out.print("The cover is ");
         for (int i = 0; i < size; i++)
            System.out.print(c[i] + " ");
         System.out.println();
      }
   }
}

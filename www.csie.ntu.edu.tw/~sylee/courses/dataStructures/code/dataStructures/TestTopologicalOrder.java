
/** test Graph.topologicalOrder */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestTopologicalOrder
{
   public static void main(String [] args)
   {
      int numberOfTypes = 8;
      // create a graph of each type
      Graph [] g = new Graph [numberOfTypes];
      g[0] = new AdjacencyGraph(7);
      g[1] = new AdjacencyDigraph(7);
      g[2] = new LinkedGraph(7);
      g[3] = new LinkedDigraph(7);
      g[4] = new AdjacencyWGraph(7);
      g[5] = new AdjacencyWDigraph(7);
      g[6] = new LinkedWGraph(7);
      g[7] = new LinkedWDigraph(7);
      int n = 7;

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();
      // try two graphs
      for (int q = 1; q <= 2; q++)
      {
         // input a test graph
         System.out.println("enter number of edges in graph");
         int e = keyboard.readInteger();
         for (int i = 1; i <= e; i++)
         {
            System.out.println("enter weighted edge " + i);
            int u = keyboard.readInteger();
            int v = keyboard.readInteger();
            int w = keyboard.readInteger();
            for (int j = 0; j < numberOfTypes / 2; j++)
            {
               g[j].putEdge(new Edge(u, v));
               g[j + 4].putEdge(new WeightedEdge(u, v, new MyInteger(w)));
            }
         }
   
         // test topologicalOrder on all graph types
         int [] s = new int [n];
         for (int i = 0; i < numberOfTypes; i++)
         {
            try
            {
               if (g[i].topologicalOrder(s))
               {
                  System.out.print("The topological order is ");
                  for (int j = 0; j < n; j++)
                     System.out.print(s[j] + " ");
                  System.out.println();
               }
               else
                  System.out.println("The digraph has no topological order");
            }
            catch (Exception ex)
               {System.out.println(ex);}
         }
      }
   }
}

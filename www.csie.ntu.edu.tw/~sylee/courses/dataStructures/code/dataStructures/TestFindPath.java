
/** test Graph.findPath */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestFindPath
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

      // test findPath on all graph types
      for (int i = 0; i < numberOfTypes; i++)
      {
         int [] path = g[i].findPath(1, 3);
         if (path == null)
            System.out.println("No path from 1 to 3");
         else
         {
            System.out.print("Path from 1 to 3 is ");
            for (int j = 0; j < path.length; j++)
               System.out.print(path[j] + " ");
            System.out.println();
         }

         path = g[i].findPath(4, 6);
         if (path == null)
            System.out.println("No path from 4 to 6");
         else
         {
            System.out.print("Path from 4 to 6 is ");
            for (int j = 0; j < path.length; j++)
               System.out.print(path[j] + " ");
            System.out.println();
         }

         path = g[i].findPath(1, 6);
         if (path == null)
            System.out.println("No path from 1 to 6");
         else
         {
            System.out.print("Path from 1 to 6 is ");
            for (int j = 0; j < path.length; j++)
               System.out.print(path[j] + " ");
            System.out.println();
         }
         System.out.println();
      }
   }
}

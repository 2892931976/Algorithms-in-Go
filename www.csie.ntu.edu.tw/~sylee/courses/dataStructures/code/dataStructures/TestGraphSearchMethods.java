/** test Graph.dfs and Graph.bfs */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestGraphSearchMethods
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
      int [] reach = new int [8];
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

      // test bfs on all graph types
      System.out.println("Doing bfs");
      for (int i = 0; i < numberOfTypes; i++)
      {
         g[i].bfs(1, reach, 1);
         g[i].bfs(4, reach, 2);
         System.out.print("Reached values for vertices are ");
         for (int j = 1; j <= n; j++)
         {
            System.out.print(reach[j] + " ");
            reach[j] = 0;
         }
         System.out.println();
      }
         System.out.println();

      // test dfs on all graph types
      System.out.println("Doing dfs");
      for (int i = 0; i < numberOfTypes; i++)
      {
         g[i].dfs(1, reach, 1);
         g[i].dfs(4, reach, 2);
         System.out.print("Reached values for vertices are ");
         for (int j = 1; j <= n; j++)
         {
            System.out.print(reach[j] + " ");
            reach[j] = 0;
         }
         System.out.println();
      }
         System.out.println();
   }
}


/** time for Graph.bfs
  * run this program twice, once with the bfs methods
  * in the implementing classes commented out and once
  * with them present */

package misc;

import java.util.*;
import dataStructures.*;

public class TimeBFS
{
   public static void main(String [] args)
   {
      Graph [] g = new Graph[2];
      int n = 100;
      g[0] = new AdjacencyGraph(n);
      g[1] = new LinkedGraph(n);
      int [] reached = new int [n + 1];
     
      // set up complete graph
      for (int i = 1; i <= n; i++)
         for (int j = i + 1; j <= n; j++)
         {
            g[0].putEdge(new Edge(i, j));
            g[1].putEdge(new Edge(i, j));
         }

      long startTime = System.currentTimeMillis();
      // repeat many times
      int numTimes = 1000;
      for (int r = 1; r <= numTimes; r++)
      {
         for (int i = 1; i <= n; i++)
            reached[i] = 0;
         g[0].bfs(1, reached, 1);
      }
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Time for adjacency matrix is " + elapsedTime +
                         " microseconds");

      startTime = System.currentTimeMillis();
      for (int r = 1; r <= numTimes; r++)
      {
         for (int i = 1; i <= n; i++)
            reached[i] = 0;
         g[1].bfs(1, reached, 1);
      }
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Time for linked lists is " + elapsedTime +
                         " microseconds");
   }
}

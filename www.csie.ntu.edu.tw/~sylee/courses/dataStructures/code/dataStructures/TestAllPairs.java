/** test AdjacencyWDigraph.allPairs */

package dataStructures;

import utilities.*;
import wrappers.*;

public class TestAllPairs
{
   /** output shortest path from i to j */
   public static void outputPath(Operable [][] c, int [][] kay, int i, int j)
   {
      if (c[i][j] == null)
         System.out.println("There is no path from " + i + " to " + j);
      else
      {
         System.out.print("The path is " + i + " ");
         outputPath(kay, i, j);
         System.out.println();
      }
   }
   
   /** actual code to output i to j path */
   public static void outputPath(int [][] kay, int i, int j)
   {
      if (i == j)
         return;
      if (kay[i][j] == 0)  // no intermediate vertices on path
         System.out.print(j + " ");
      else
      {// kay[i][j] is an intermediate vertex on the path
         outputPath(kay, i, kay[i][j]);
         outputPath(kay, kay[i][j], j);
      }
   }

   public static void main(String [] args)
   {
      int numberOfTypes = 2;
      // create a graph of each adjacency weighted type
      AdjacencyWDigraph [] g = new AdjacencyWDigraph [numberOfTypes];
      int n = 5;
      g[0] = new AdjacencyWGraph(n);
      g[1] = new AdjacencyWDigraph(n);

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

      System.out.println("The weighted undirected graph is");
      g[0].output();
      System.out.println("\nThe weighted digraph is");
      g[1].output();

      // test allPairs
      MyInteger [][] c = new MyInteger [n + 1][n + 1];
      int [][] kay = new int [n + 1][n + 1];
      // first do for g[0]
      System.out.println("\nWorking on weighted undirected graph");
      g[0].allPairs(c, kay);
      System.out.println("cost matrix is");
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(c[i][j] + " ");
         System.out.println();
      }
      
      System.out.println("\nkay matrix is");
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(kay[i][j] + " ");
         System.out.println();
      }
   
      System.out.println();
      outputPath(c, kay, 1, 5);
      System.out.println();

      // now do for g[1]
      System.out.println("\nWorking on weighted directed graph");
      g[1].allPairs(c, kay);
      System.out.println("cost matrix is");
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(c[i][j] + " ");
         System.out.println();
      }
      
      System.out.println("\nkay matrix is");
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(kay[i][j] + " ");
         System.out.println();
      }
      System.out.println();
   
      outputPath(c, kay, 1, 5);
   }
}

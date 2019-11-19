

/** adjacency matrix representation of a weighted undirected graph */

package dataStructures;

import java.util.*;

public class AdjacencyWGraph extends AdjacencyWDigraph
{
   // constructors
   public AdjacencyWGraph(int theVertices)
      {super(theVertices);}

   public AdjacencyWGraph()
      {this(0);}

   /** put edge e into the graph, if the edge is already
     * there, update its weight to e.weight 
     * @throws IllegalArgumentException when theEdge
     * cannot be an edge of the graph */
   public void putEdge(Object theEdge)
   {
      WeightedEdge edge =  (WeightedEdge) theEdge;
      int v1 = edge.vertex1;
      int v2 = edge.vertex2;
      if (v1 < 1 || v2 < 1 || v1 > n || v2 > n || v1 == v2)
         throw new IllegalArgumentException
               ("(" + v1 + "," + v2 + ") is not a permissible edge");

      if (a[v1][v2] == null)  // new edge
         e++;
      a[v1][v2] = edge.weight;
      a[v2][v1] = edge.weight;
   }
   
   /** remove the edge (i,j) */
   public void removeEdge(int i, int j)
   {
      if (i >= 1 && j >= 1 && i <= n && j <= n && a[i][j] != null)
         {
            a[i][j] = null;
            a[j][i] = null;
            e--;
         }
   }
   
   /** @return degree of vertex i
     * @throws IllegalArgumentException when
     * i is an invalid vertex */
   public int degree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count edges from vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[i][j] != null)
            sum++;

      return sum;
   }

   /** @return degree of vertex i */
   public int outDegree(int i)
      {return degree(i);}
   
   /** @return degree of vertex i */
   public int inDegree(int i)
      {return degree(i);}
   
   /** test program */
   public static void main(String [] args)
   {
      AdjacencyWGraph g = new AdjacencyWGraph(4);
      System.out.println("Edges = " + g.edges());
      System.out.println();

      g.putEdge(new WeightedEdge(2, 4, new Integer(1)));
      g.putEdge(new WeightedEdge(1, 3, new Integer(2)));
      g.putEdge(new WeightedEdge(2, 1, new Integer(3)));
      g.putEdge(new WeightedEdge(1, 4, new Integer(4)));
      g.putEdge(new WeightedEdge(4, 2, new Integer(5)));
      System.out.println("The graph is");
      g.output();
      System.out.println();

      g.removeEdge(2,1);
      System.out.println("The graph after deleting (2,1) is");
      g.output();
      System.out.println();

      System.out.println("existsEdge(3,1) = " + g.existsEdge(3,1));
      System.out.println("inDegree(3) = " + g.inDegree(3));
      System.out.println("outDegree(1) = " + g.outDegree(1));
      System.out.println("Edges = " + g.edges());
      System.out.println();

      // test iterator
      Iterator iter = g.iterator(4);
      System.out.println("Edges incident to vertex 4 are");
      while (iter.hasNext())
      {
         WeightedEdgeNode w = (WeightedEdgeNode) iter.next();
         System.out.println("(4, " + w + ")");
      }
   }
}

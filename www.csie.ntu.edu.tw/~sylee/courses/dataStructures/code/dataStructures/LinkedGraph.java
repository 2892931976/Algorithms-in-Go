/** linked adjacency list representation of an undirected graph */

package dataStructures;

import java.util.*;

public class LinkedGraph extends LinkedDigraph
{
   // constructors
   public LinkedGraph(int theVertices)
      {super(theVertices);}
   
   // default is a 0 vertex graph
   public LinkedGraph()
      {this(0);}
   
   // override these methods of LinkedDigraph
   /** put theEdge into the digraph
     * @throws IllegalArgumentException when
     * theEdge is invalid */
   public void putEdge(Object theEdge)
   {
      Edge edge =  (Edge) theEdge;
      int v1 = edge.vertex1;
      int v2 = edge.vertex2;
      if (v1 < 1 || v2 < 1 || v1 > n || v2 > n || v1 == v2)
         throw new IllegalArgumentException
               ("(" + v1 + "," + v2 + ") is not a permissible edge");

      if (aList[v1].indexOf(new EdgeNode(v2)) == -1)  // new edge
      {
         // put v2 at front of aList[v1] and v1 at front of aList[v2]
         aList[v1].add(0, new EdgeNode(v2));
         aList[v2].add(0, new EdgeNode(v1));
         e++;
      }
   }


   /** remove the edge (i,j) */
   public void removeEdge(int i, int j)
   {
      if (i >= 1 && j >= 1 && i <= n && j <= n)
      {
         Object v = aList[i].removeElement(j);
         if (v != null)  // edge (i,j) did exist
         {
            aList[j].removeElement(i);
            e--;
         }
      }
   }
   
   /** @return degree of vertex i
     * @throws IllegalArgumentException when
     * i is an invalid vertex */
   public int degree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);
      return aList[i].size();
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
      LinkedGraph g = new LinkedGraph(4);
      System.out.println("Edges = " + g.edges());
      System.out.println();

      g.putEdge(new Edge(2, 4));
      g.putEdge(new Edge(1, 3));
      g.putEdge(new Edge(2, 1));
      g.putEdge(new Edge(1, 4));
      g.putEdge(new Edge(4, 2));
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
      System.out.println("Edges out of vertex 4 are");
      while (iter.hasNext())
      {
         EdgeNode w = (EdgeNode) iter.next();
         System.out.println("(4, " + w.vertex + ")");
      }
   }
}

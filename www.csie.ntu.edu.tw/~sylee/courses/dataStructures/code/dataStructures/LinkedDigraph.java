/** linked adjacency list representation of a digraph */

package dataStructures;

import java.util.*;

public class LinkedDigraph extends Graph
{
   // data members
   int n;                 // number of vertices
   int e;                 // number of edges
   GraphChain [] aList;   // adjacency lists
   
   // constructors
   public LinkedDigraph(int theVertices)
   {
      // validate theVertices
      if (theVertices < 0)
         throw new IllegalArgumentException
                   ("number of vertices must be >= 0");
      n = theVertices;
      aList = new GraphChain [n + 1];
      for (int i = 1; i <= n; i++)
         aList[i] = new GraphChain();

      // default value of e is 0
   }
   
   // default is a 0-vertex graph
   public LinkedDigraph()
      {this(0);}
   
   // Graph methods
   /** @return number of vertices */
   public int vertices()
      {return n;}

   /** @return number of edges */
   public int edges()
      {return e;}

   /** @return true iff (i,j) is an edge */
   public boolean existsEdge(int i, int j)
   {
      if (i < 1 || j < 1 || i > n || j > n
          || aList[i].indexOf(new EdgeNode(j)) == -1)
         return false;
      else
         return true;
   }
   
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
         // put v2 at front of chain aList[v1]
         aList[v1].add(0, new EdgeNode(v2));
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
            e--;
      }
   }
   
   /** this method is undefined for directed graphs 
     * @throws NoSuchMethodError */
   public int degree(int i)
       {throw new NoSuchMethodError();}

   /** @return out-degree of vertex i 
     * @throws IllegalArgumentException when
     * i is an invalid vertex */
   public int outDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      return aList[i].size();
   }
   
   /** @return in-degree of vertex i 
     * @throws IllegalArgumentException when
     * i is an invalid vertex */
   public int inDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count in edges at vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (aList[j].indexOf(new EdgeNode(i)) != -1)
            sum++;

      return sum;
   }
   
   /** output the graph */
   public void output()
   {
      for (int i = 1; i <= n; i++)
         System.out.println("Vertex " + i + " = " + aList[i]);
   }

   /** create and return an iterator for vertex i
     * @throws IllegalArgumentException when
     * i is an invalid vertex */
   public Iterator iterator(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      return aList[i].iterator();
   }

   /** breadth-first search */
   public void bfs(int v, int [] reach, int label)
   {
      ArrayQueue q = new ArrayQueue(10);
      reach[v] = label;
      q.put(new Integer(v));
      while (!q.isEmpty())
      {
         // remove a labeled vertex from the queue
         int w = ((Integer) q.remove()).intValue();

         // mark all unreached vertices adjacent from w
         for (ChainNode p = aList[w].firstNode; p != null; p = p.next)
         {
            int u = ((EdgeNode) p.element).vertex;
            if (reach[u] == 0)
            {// u is an unreached vertex
               q.put(new Integer(u));
               reach[u] = label;
            }
         }
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      LinkedDigraph g = new LinkedDigraph(4);
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
      System.out.println("existsEdge(1,3) = " + g.existsEdge(1,3));
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



/** adjacency matrix representation of an unweighted digraph */

package dataStructures;

import java.util.*;

public class AdjacencyDigraph extends Graph
{
   // data members
   int n;           // number of vertices
   int e;           // number of edges
   boolean [][] a;   // adjacency array
   
   // constructors
   public AdjacencyDigraph(int theVertices)
   {
      // validate theVertices
      if (theVertices < 0)
         throw new IllegalArgumentException
                   ("number of vertices must be >= 0");
      n = theVertices;
      a = new boolean [n + 1] [n + 1];
      // default values are e = 0 and a[i][j] = false
   }
   
   // default is a 0 vertex graph
   public AdjacencyDigraph()
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
      if (i < 1 || j < 1 || i > n || j > n)
         return false;
      else
         return a[i][j];
   }
   
   /** put edge e into the digraph 
     * @throws IllegalArgumentExceptin when theEdge
     * is invalid */
   public void putEdge(Object theEdge)
   {
      Edge edge =  (Edge) theEdge;
      int v1 = edge.vertex1;
      int v2 = edge.vertex2;
      if (v1 < 1 || v2 < 1 || v1 > n || v2 > n || v1 == v2)
         throw new IllegalArgumentException
               ("(" + v1 + "," + v2 + ") is not a permissible edge");

      if (!a[v1][v2])  // new edge
      {
         a[v1][v2] = true;
         e++;
      }
   }
   
   /** remove the edge (i,j) */
   public void removeEdge(int i, int j)
   {
      if (i >= 1 && j >= 1 && i <= n && j <= n && a[i][j])
      {
         a[i][j] = false;
         e--;
      }
   }
   
   /** this method is undefined for directed graphs */
   public int degree(int i)
       {throw new NoSuchMethodError("AdjacencyDigraph:degree");}

   /** @return out-degree of vertex i
     * @throws IllegalArgumentException when i
     * is an invalid vertex */
   public int outDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count out edges from vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[i][j])
            sum++;

      return sum;
   }
   
   /** @return in-degree of vertex i
     * @throws IllegalArgumentException when i
     * is an invalid vertex */
   public int inDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count in edges at vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[j][i])
            sum++;

      return sum;
   }
   
   /** output the adjacency matrix */
   public void output()
   {
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            if (a[i][j]) 
               System.out.print("1 ");
            else
               System.out.print("0 ");
         System.out.println();
      }
   }

   /** create and return an iterator for vertex i
     * @throws IllegalArgumentException when i
     * is an invalid vertex */
   public Iterator iterator(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      return new VertexIterator(i);
   }

   private class VertexIterator implements Iterator
   {
      // data members
      private int v;   // the vertex being iterated
      private int nextVertex;
   
      // constructor
      public VertexIterator(int i)
      {
         v = i;
         // find first adjacent vertex
         for (int j = 1; j <= n; j++)
            if (a[v][j])
            {
               nextVertex = j;
               return;
            }

         // no edge out of vertex i
         nextVertex = n + 1;
      }
   
      // methods
      /** @return true iff there is a next vertex */
      public boolean hasNext()
         {return nextVertex <= n;}
   
      /** @return next adjacent vertex and edge weight
        * @throws NoSuchElementException
        * when there is no next vertex */
      public Object next()
      {
         if (nextVertex <= n)
         {  
            int u = nextVertex;
            // find next adjacent vertex
            for (int j = u + 1; j <= n; j++)
               if (a[v][j])
               {
                  nextVertex = j;
                  return new EdgeNode(u);
               }

            // no next adjacent vertex for v
            nextVertex = n + 1;
            return new EdgeNode(u);
         }
         else throw new NoSuchElementException("no next vertex");
      }

      /** unsupported method */
      public void remove()
         {throw new UnsupportedOperationException();}
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
         for (int u = 1; u <= n; u++)
         {
            if (a[w][u] && reach[u] == 0)
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
      AdjacencyDigraph g = new AdjacencyDigraph(4);
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

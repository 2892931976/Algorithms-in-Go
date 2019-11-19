/** adjacency matrix representation of a weighted digraph */

package dataStructures;

import java.util.*;
import utilities.*;

public class AdjacencyWDigraph extends Graph
{
   // data members
   int n;           // number of vertices
   int e;           // number of edges
   Object [][] a;   // adjacency array
   
   // constructors
   public AdjacencyWDigraph(int theVertices)
   {
      // validate theVertices
      if (theVertices < 0)
         throw new IllegalArgumentException
                   ("number of vertices must be >= 0");
      n = theVertices;
      a = new Object [n + 1] [n + 1];
      // default values are e = 0 and a[i][j] = null
   }
   
   // default is a 0 vertex graph
   public AdjacencyWDigraph()
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
      if (i < 1 || j < 1 || i > n || j > n || a[i][j] == null)
         return false;
      else
         return true;
   }
   
   /** put edge e into the digraph; if the edge is already
     * there, update its weight to e.weight 
     * @throws IllegalArgumentException when theEdge is invalid */
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
   }
   
   /** remove the edge (i,j) */
   public void removeEdge(int i, int j)
   {
      if (i >= 1 && j >= 1 && i <= n && j <= n && a[i][j] != null)
      {
         a[i][j] = null;
         e--;
      }
   }
   
   /** this method is undefined for directed graphs */
   public int degree(int i)
      {throw new NoSuchMethodError();}

   /** @return out-degree of vertex i
     * @throws IllegalArgumentException when i is not a valid vertex */
   public int outDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count out edges from vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[i][j] != null)
            sum++;

      return sum;
   }
   
   /** @return in-degree of vertex i 
     * @throws IllegalArgumentException when i is not a valid vertex*/
   public int inDegree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count in edges at vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[j][i] != null)
            sum++;

      return sum;
   }
   
   /** output the adjacency matrix */
   public void output()
   {
      for (int i = 1; i <= n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(a[i][j] + " ");
         System.out.println();
      }
   }

   /** create and return an iterator for vertex i
     * @throws IllegalArgumentException when i is an invalid vertex */
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
            if (a[v][j] != null)
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
               if (a[v][j] != null)
               {
                  nextVertex = j;
                  return new WeightedEdgeNode(u, a[v][u]);
               }

            // no next adjacent vertex for v
            nextVertex = n + 1;
            return new WeightedEdgeNode(u, a[v][u]);
         }
         else throw new NoSuchElementException
                    ("no next vertex");
      }

      /** unsupported method */
      public void remove()
         {throw new UnsupportedOperationException();}
   }

   /** find shortest paths from sourceVertex
     * @return shortest distances in distanceFromSource
     * @return predecessor information in predecessor
     * @throws IllegalArgumentException when sourceVertex
     * is not a vertex of the graph */
   public void shortestPaths(int sourceVertex,
               Operable [] distanceFromSource, int [] predecessor)
   {
      if (sourceVertex < 1 || sourceVertex > n)
         throw new IllegalArgumentException
               ("source vertex cannot be " + sourceVertex);

      GraphChain newReachableVertices = new GraphChain();

      // initialize
      for (int i = 1; i <= n; i++)
      {
         distanceFromSource[i] = (Operable) a[sourceVertex][i];
         if (distanceFromSource[i] == null)
            predecessor[i] = -1;
         else
         {
            distanceFromSource[sourceVertex] =
                    (Operable) distanceFromSource[i].zero();
            predecessor[i] = sourceVertex; 
            newReachableVertices.add(0, new EdgeNode(i));
         }
      }
      predecessor[sourceVertex] = 0;  // source vertex has no predecessor
   
      // update distanceFromSource and predecessor
      while (!newReachableVertices.isEmpty())
      {// more paths exist
         // find unreached vertex v with least distanceFromSource
         Iterator iNewReachableVertices = newReachableVertices.iterator();
         int v = ((EdgeNode) iNewReachableVertices.next()).vertex;
         while (iNewReachableVertices.hasNext())
         {
            int w = ((EdgeNode) iNewReachableVertices.next()).vertex;
            if (distanceFromSource[w].compareTo(distanceFromSource[v]) < 0)
               v = w;
         }
   
         // next shortest path is to vertex v
         // delete v from newReachableVertices and update distanceFromSource
         newReachableVertices.removeElement(v);
         for (int j = 1; j <= n; j++)
         {
            if (a[v][j] != null && (predecessor[j] == -1 ||
      	             distanceFromSource[j].compareTo
                         (distanceFromSource[v].add(a[v][j])) > 0))
            {
               // distanceFromSource[j] decreases
               distanceFromSource[j] = (Operable) distanceFromSource[v].
                                                  add(a[v][j]);
               // add j to newReachableVertices
               if (predecessor[j] == -1)
                  // not reached before
                  newReachableVertices.add(0, new EdgeNode(j));
               predecessor[j] = v;
            }
         }
      }
   }

   /** dynamic programming all pairs shortest paths algorithm
     * compute c[i][j] and kay[i][j] for all i and j */
   public void allPairs(Operable [][] c, int [][] kay)
   {
      // initialize c[i][j] = c(i,j,0)
      Operable notNull = null;   // eventually a nonnull element
      for (int i = 1; i <= n; i++)
         for (int j = 1; j <= n; j++)
         {
            c[i][j] = (Operable) a[i][j];
            if (c[i][j] != null)
               notNull = c[i][j];
            kay[i][j] = 0;
         }
      if (notNull == null) // graph has no edges
         return;
      for (int i = 1; i <= n; i++)
         c[i][i] = (Operable) notNull.zero();
      
      // compute c[i][j] = c(i,j,k)
      for (int k = 1; k <= n; k++)
         for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
            {
               Operable t1 = c[i][k];
               Operable t2 = c[k][j];
               Operable t3 = c[i][j];
               if (t1 != null && t2 != null &&
                  (t3 == null || t3.compareTo(t1.add(t2)) > 0))
               {// smaller value for c[i][j] found
                    c[i][j] = (Operable) t1.add(t2);
                    kay[i][j] = k;
               }
            }
   }
   
   // class data members
   static int [] partialTour;
   static int [] bestTourSoFar;
   static Object costOfBestTourSoFar;
   static Operable costOfPartialTour;

   /** traveling salesperson by backtracking
     * @param theZero zero weight
     * @param bestTour bestTour[1:n] is set to best tour
     * @return cost of best tour */
   public Object btSalesperson(int [] bestTour, Operable theZero)
   {
      // set partialTour to identity permutation
      partialTour = new int [n + 1];
      for (int i = 1; i <= n; i++)
         partialTour[i] = i;

      costOfBestTourSoFar = null;
      bestTourSoFar = bestTour;
      costOfPartialTour = theZero;
   
      // search permutations of partialTour[2:n]
      rTSP(2);
   
      return costOfBestTourSoFar;
   }
   
   /** recursive backtracking code for traveling salesperson
     * search the permutation tree for best tour */
   private void rTSP(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel == n)
      {// at parent of a leaf
         // complete tour by adding last two edges
         if (a[partialTour[n - 1]][partialTour[n]] != null &&
             a[partialTour[n]][1] != null &&
             (costOfBestTourSoFar == null ||
             ((Operable) ((Operable) costOfPartialTour.
                           add(a[partialTour[n - 1]][partialTour[n]])).
                           add(a[partialTour[n]][1])).
                           compareTo(costOfBestTourSoFar) < 0))
         {// better tour found
            for (int j = 1; j <= n; j++)
               bestTourSoFar[j] = partialTour[j];
            costOfBestTourSoFar = ((Operable) costOfPartialTour.
                             add(a[partialTour[n - 1]][partialTour[n]])).
                             add(a[partialTour[n]][1]);
         }
      }
      else
      {// try out subtrees
         for (int j = currentLevel; j <= n; j++)
            // is move to subtree labeled partialTour[j] possible?
            if (a[partialTour[currentLevel - 1]][partialTour[j]] != null &&
                (costOfBestTourSoFar == null ||
                 ((Operable) costOfPartialTour.
                   add(a[partialTour[currentLevel - 1]][partialTour[j]])).
                   compareTo(costOfBestTourSoFar) < 0))
            {// search this subtree
               MyMath.swap(partialTour, currentLevel, j);
               costOfPartialTour.increment(a[partialTour[currentLevel - 1]]
                                            [partialTour[currentLevel]]);
               rTSP(currentLevel + 1);
               costOfPartialTour.decrement(a[partialTour[currentLevel - 1]]
                                            [partialTour[currentLevel]]);
               MyMath.swap(partialTour, currentLevel, j);
            }
      }
   }

   // class used by least-cost branch-and-bound traveling salesperson
   private static class HeapNode implements Comparable
   {
      // data members
      Operable lowerCost;          // lower bound on cost of tours in subtree
      Operable costOfPartialTour;  // cost of partial tour
      Operable minAdditionalCost;  // min additional cost to complete tour
      int sizeOfPartialTour;       // partial tour is
      int [] partialTour;          // partialTour[sizeOfPartialTour+1:n-1]
                                   // gives remaining vertices to be added
                                   // to partialTour[0:sizeOfPartialTour]

      public int compareTo(Object x)
         {return lowerCost.compareTo(((HeapNode) x).lowerCost);}
   }
   
   /** least-cost branch-and-bound code to find a shortest tour
     * @param theZero zero weight
     * @param bestTour bestTour[i] set to i'th vertex on shortest tour
     * @return cost of shortest tour */
   public Object leastCostBBSalesperson(int bestTour[], Operable theZero)
   {
      MinHeap liveNodeMinHeap = new MinHeap();
   
      // costOfMinOutEdge[i] = cost of least-cost edge leaving vertex i
      Operable [] costOfMinOutEdge = new Operable [n + 1];

      Operable sumOfMinCostOutEdges = (Operable) theZero.zero();
                        // use a new copy of zero

      for (int i = 1; i <= n; i++)
      {// compute costOfMinOutEdge[i] and sumOfMinCostOutEdges
         Operable minCost = null;
         for (int j = 1; j <= n; j++)
            if (a[i][j] != null && (minCost == null ||
                minCost.compareTo(a[i][j]) > 0))
               minCost = (Operable) a[i][j];

         if (minCost == null) return null; // no route
         costOfMinOutEdge[i] = minCost;
         sumOfMinCostOutEdges.increment(minCost);
      }
   
      // initial E-node is tree root
      HeapNode eNode = new HeapNode();
      eNode.partialTour = new int [n];
      for (int i = 0; i < n; i++)
         eNode.partialTour[i] = i + 1;
      eNode.sizeOfPartialTour = 0;               // partial tour is
                                                 // partial[0:0]
      eNode.costOfPartialTour = theZero;         // its cost is zero
      eNode.minAdditionalCost = sumOfMinCostOutEdges;
      Operable costOfBestTourSoFar = null;       // no tour found so far
      int [] partialTour = eNode.partialTour;    // shorthand for
                                                 // eNode.partialTour
      // search permutation tree
      while (eNode != null && eNode.sizeOfPartialTour < n - 1)
      {// not at leaf
         partialTour = eNode.partialTour;
         if (eNode.sizeOfPartialTour == n - 2)
         {// parent of leaf
            // complete tour by adding two edges
            // see whether new tour is better
            if (a[partialTour[n - 2]][partialTour[n - 1]] != null
                && a[partialTour[n - 1]][1] != null
                && (costOfBestTourSoFar == null ||
                   ((Operable) ((Operable) eNode.costOfPartialTour
                  .add(a[partialTour[n - 2]][partialTour[n - 1]]))
                  .add(a[partialTour[n - 1]][1]))
                  .compareTo(costOfBestTourSoFar) < 0))
            {// better tour found
               costOfBestTourSoFar = (Operable) (((Operable) eNode
                      .costOfPartialTour.add(a[partialTour[n - 2]]
                       [partialTour[n - 1]]))
                      .add(a[partialTour[n - 1]][1]));
                eNode.costOfPartialTour = costOfBestTourSoFar;
                eNode.lowerCost = costOfBestTourSoFar;
                eNode.sizeOfPartialTour++;
                liveNodeMinHeap.put(eNode);
            }
         } 
         else
         {// generate children
            for (int i = eNode.sizeOfPartialTour + 1; i < n; i++)
               if (a[partialTour[eNode.sizeOfPartialTour]]
                   [partialTour[i]] != null)
               {
                  // feasible child, bound path cost
                  Operable costOfPartialTour = (Operable) eNode
                      .costOfPartialTour
                      .add(a[partialTour[eNode.sizeOfPartialTour]]
                            [partialTour[i]]);
                  Operable minAdditionalCost =
                      (Operable) eNode.minAdditionalCost.subtract
                      (costOfMinOutEdge[partialTour
                       [eNode.sizeOfPartialTour]]);
                  Operable leastCostPossible =
                     (Operable) costOfPartialTour.add(minAdditionalCost);
                  if (costOfBestTourSoFar == null ||
                    leastCostPossible.compareTo(costOfBestTourSoFar) < 0)
                  {// subtree may have better leaf, put root in min heap
                      HeapNode hNode = new HeapNode();
                      hNode.partialTour = new int [n];
                      for (int j = 0; j < n; j++)
                         hNode.partialTour[j] = partialTour[j];
                      hNode.partialTour[eNode.sizeOfPartialTour + 1] =
                               partialTour[i];
                      hNode.partialTour[i] =
                               partialTour[eNode.sizeOfPartialTour + 1];
                      hNode.costOfPartialTour = costOfPartialTour;
                      hNode.sizeOfPartialTour = eNode.sizeOfPartialTour
                                                + 1;
                      hNode.lowerCost = leastCostPossible;
                      hNode.minAdditionalCost = minAdditionalCost;
                      liveNodeMinHeap.put(hNode);
                  }
               }
            }
   
         // get next E-node
         eNode = (HeapNode) liveNodeMinHeap.removeMin();
      }
   
      if (costOfBestTourSoFar == null)
         return null; // no route

      // copy best route into bestTour[1:n]
      for (int i = 0; i < n; i++)
         bestTour[i + 1] = partialTour[i];
   
      return costOfBestTourSoFar;
   }

   /** test program for Graph methods */
   public static void main(String [] args)
   {
      AdjacencyWDigraph g = new AdjacencyWDigraph(4);
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
         WeightedEdgeNode w = (WeightedEdgeNode) iter.next();
         System.out.println("(4, " + w + ")");
      }
   }
}

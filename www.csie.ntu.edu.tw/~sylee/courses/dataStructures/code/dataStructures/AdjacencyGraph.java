
/** adjacency matrix representation of a unweighted undirected graph */

package dataStructures;

import java.util.*;

public class AdjacencyGraph extends AdjacencyDigraph
{
   // constructors
   public AdjacencyGraph(int theVertices)
      {super(theVertices);}

   public AdjacencyGraph()
      {this(0);}

   /** put edge e into the graph 
     * @throws IllegalArgumentException when theEdge is invalid */
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
         a[v2][v1] = true;
         e++;
      }
   }
   
   /** remove the edge (i,j) */
   public void removeEdge(int i, int j)
   {
      if (i >= 1 && j >= 1 && i <= n && j <= n && a[i][j])
         {
            a[i][j] = false;
            a[j][i] = false;
            e--;
         }
   }
   
   /** @return degree of vertex i
     * @throws IllegalArgumentException when i
     * is an invalid vertex */
   public int degree(int i)
   {
      if (i < 1 || i > n)
         throw new IllegalArgumentException("no vertex " + i);

      // count edges from vertex i
      int sum = 0;
      for (int j = 1; j <= n; j++)
         if (a[i][j])
            sum++;

      return sum;
   }

   /** @return degree of vertex i */
   public int outDegree(int i)
      {return degree(i);}
   
   /** @return degree of vertex i */
   public int inDegree(int i)
      {return degree(i);}

   // class data members used by backtracking max clique
   static int [] currentClique;
   static int sizeOfCurrentClique;
   static int sizeOfMaxCliqueSoFar;
   static int [] maxCliqueSoFar;
   
   /** solve max-clique problem using backtracking
     * @return size of max clique
     * @param maxClique set maxClique[i] = 1 iff i is in max clique */
   public int btMaxClique(int [] maxClique)
   {
      // initialize for rClique
      currentClique = new int [n + 1];
      sizeOfCurrentClique = 0;
      sizeOfMaxCliqueSoFar = 0;
      maxCliqueSoFar = maxClique;
   
      // find max clique
      rClique(1);
      return sizeOfMaxCliqueSoFar;
   }
   
   /** recursive backtracking code to compute largest clique */
   private void rClique(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel > n)
      {// at leaf, found a larger clique
       // update maxCliqueSoFar and sizeOfMaxCliqueSoFar
         for (int j = 1; j <= n; j++)
            maxCliqueSoFar[j] = currentClique[j];
         sizeOfMaxCliqueSoFar = sizeOfCurrentClique;
         return;
      }
   
      // not at leaf; see whether vertex currentLevel
      // is connected to others in current clique
      boolean connected = true;
      for (int j = 1; j < currentLevel; j++)
         if (currentClique[j] == 1 && !a[currentLevel][j])
         {// vertex currentLevel not connected to j
            connected = false;
            break;
         }
   
      if (connected)
      {// try left subtree
         currentClique[currentLevel] = 1;  // add to clique
         sizeOfCurrentClique++;
         rClique(currentLevel + 1);
         sizeOfCurrentClique--;
      }
   
      if (sizeOfCurrentClique + n - currentLevel > sizeOfMaxCliqueSoFar)
      {// try right subtree
         currentClique[currentLevel] = 0;
         rClique(currentLevel + 1);
      }
   }

   // class data member used by max-profit branch-and-bound max clique
   static MaxHeap liveNodeMaxHeap;
   
   // top-level classes used by max-profit branch-and-bound max clique
   static class BBnode
   {
      // instance data members
      BBnode parent;
      boolean leftChild;    // true iff left child of parent

      // constructor
      BBnode(BBnode theParent, boolean theLeftChild)
      {
         parent = theParent;
         leftChild = theLeftChild;
      }

   }
   
   static class HeapNode implements Comparable
   {
      // class data members
      BBnode liveNode;
      int upperSize;     // upper bound on clique size in this subtree
      int cliqueSize;    // size of clique at this node
      int level;

      // constructor
      HeapNode(BBnode theLiveNode, int theUpperSize,
               int theCliqueSize, int theLevel)
      {
         liveNode = theLiveNode;
         upperSize = theUpperSize;
         cliqueSize = theCliqueSize;
         level = theLevel;
      }

      public int compareTo(Object x)
      {
         int xUpperSize = ((HeapNode) x).upperSize;
         if (upperSize < xUpperSize)
            return -1;
         if (upperSize == xUpperSize)
            return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return upperSize == ((HeapNode) x).upperSize;}
   }
   
   /** max-profit branch-and-bound code to find a max clique
     * @param maxClique maxClique[i] set to 1 iff i is in max clique
     * @return size of max clique */
   public int maxProfitBBMaxClique(int [] maxClique)
   {
      liveNodeMaxHeap = new MaxHeap();
   
      // initialize for level 1 start
      BBnode eNode = null;
      int eNodeLevel = 1;
      int sizeOfCliqueAtENode = 0;
      int sizeOfMaxCliqueSoFar = 0;
   
      // search subset space tree
      while (eNodeLevel != n + 1)
      {// while not at leaf
         // see if vertex eNodeLevel is connected to all
         // vertices in the current clique
         boolean connected = true;
         BBnode currentNode = eNode;
         for (int j = eNodeLevel - 1; j > 0;
              currentNode = currentNode.parent, j--)
            if (currentNode.leftChild && !a[eNodeLevel][j])
            {// j is in the clique but no edge between eNodeLevel and j
               connected = false;
               break;
             }
   
         if (connected)
         {// left child is feasible
            if (sizeOfCliqueAtENode + 1 > sizeOfMaxCliqueSoFar)
               sizeOfMaxCliqueSoFar = sizeOfCliqueAtENode + 1;
            addLiveNode(sizeOfCliqueAtENode + n - eNodeLevel + 1,
               sizeOfCliqueAtENode + 1, eNodeLevel + 1, eNode, true);
         }

         if (sizeOfCliqueAtENode + n - eNodeLevel >= sizeOfMaxCliqueSoFar)
            // right child has prospects
            addLiveNode(sizeOfCliqueAtENode + n - eNodeLevel,
               sizeOfCliqueAtENode, eNodeLevel + 1, eNode, false);
   
         // get next E-node, heap cannot be empty
         HeapNode nextENode = (HeapNode) liveNodeMaxHeap.removeMax();
         eNode = nextENode.liveNode;
         sizeOfCliqueAtENode = nextENode.cliqueSize;
         eNodeLevel = nextENode.level;
      }
   
      // construct maxClique[] by following path from eNode to the root
      for (int j = n; j > 0; j--)
      {
         maxClique[j] = (eNode.leftChild) ? 1 : 0;
         eNode = eNode.parent;
      }
   
      return sizeOfMaxCliqueSoFar;
   }


   /** add a new live node to the max heap
     * also add the live node to the solution space tree
     * @param theSize size of clique at this live node
     * @param theParent parent of new node
     * @param leftChild true iff new node is left child of theParent */
   private static void addLiveNode(int upperSize, int theSize, int theLevel,
                                   BBnode theParent, boolean leftChild)
   {
      // create the new node of the solution space tree
      BBnode b = new BBnode(theParent, leftChild);

      // create corresponding node for max heap
      HeapNode hNode = new HeapNode(b, upperSize, theSize, theLevel);

      // put into max heap
      liveNodeMaxHeap.put(hNode);
   }

   /** test program for basic graph methods */
   public static void main(String [] args)
   {
      AdjacencyGraph g = new AdjacencyGraph(4);
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
      System.out.println("Edges incident to vertex 4 are");
      while (iter.hasNext())
      {
         EdgeNode w = (EdgeNode) iter.next();
         System.out.println("(4, " + w.vertex + ")");
      }
   }
}

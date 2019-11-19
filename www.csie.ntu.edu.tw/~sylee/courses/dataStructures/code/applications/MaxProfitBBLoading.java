
/** Max profit branch-and-bound code for two ship loading
  * code to find best loading and its value */

package applications;

import dataStructures.*;
import utilities.*;

public class MaxProfitBBLoading
{
   // top-level classes
   static class BBnode
   {
      // instance data members
      BBnode parent;        // pointer to parent node
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
      int upperWeight;    // upper weight of live node
      int level;          // level of live node

      // constructor
      HeapNode(BBnode theLiveNode, int theUpperWeight, int theLevel)
      {
         liveNode = theLiveNode;
         upperWeight = theUpperWeight;
         level = theLevel;
      }

      public int compareTo(Object x)
      {
         int xUpperWeight = ((HeapNode) x).upperWeight;
         if (upperWeight < xUpperWeight) return -1;
         if (upperWeight == xUpperWeight) return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return upperWeight == ((HeapNode) x).upperWeight;}
   }
   
   // class data member
   static MaxHeap liveNodeMaxHeap;

   /** max profit branch-and-bound search of solution space
     * @param weight array of container weights
     * @param capacity capacity of ship
     * @param bestLoading set to best loading
     * @return weight of best loading */
   public static int maxLoading(int [] weight, int capacity,
                                int [] bestLoading)
   {
      // set class data member
      liveNodeMaxHeap = new MaxHeap();

      // initialize for level 1 E-node
      int numberOfContainers = weight.length - 1;
      BBnode eNode = null;
      int eNodeLevel = 1;
      int eNodeWeight = 0;

      // remainingWeight[j] will be sum of weight[j+1:n]
      // default initial value is 0
      int [] remainingWeight = new int [numberOfContainers + 1];
      for (int j = numberOfContainers - 1; j > 0; j--)
         remainingWeight[j] = remainingWeight[j + 1] + weight[j + 1];
   
      // search subset space tree
      while (eNodeLevel != numberOfContainers + 1)
      {// not at a leaf
         // check children of E-node
         if (eNodeWeight + weight[eNodeLevel] <= capacity)
            // feasible left child
            addLiveNode(eNodeWeight + weight[eNodeLevel] +
                        remainingWeight[eNodeLevel], eNodeLevel + 1,
                        eNode, true);
         // right child is always feasible
         addLiveNode(eNodeWeight + remainingWeight[eNodeLevel],
                     eNodeLevel + 1, eNode, false);
   
         // get next E-node, heap cannot be empty
         HeapNode nextENode = (HeapNode) liveNodeMaxHeap.removeMax();
         eNodeLevel = nextENode.level;
         eNode = nextENode.liveNode;
         eNodeWeight = nextENode.upperWeight
                       - remainingWeight[eNodeLevel - 1];
      }
   
      // construct bestLoading[] by following path
      // from eNode to the root
      for (int j = numberOfContainers; j > 0; j--)
      {
         bestLoading[j] = (eNode.leftChild) ? 1 : 0;
         eNode = eNode.parent;
      }
   
      return eNodeWeight;
   }

   /** add a new live node to the live node max heap
     * also add the live node to the solution space tree
     * @param theParent is the parent of the new live node
     * @param leftChild is true iff the new live node is
     * the left child of theParent */
   private static void addLiveNode(int upperWeight, int level,
                                   BBnode theParent, boolean leftChild)
   {
      // create the new node of the solution space tree
      BBnode b = new BBnode(theParent, leftChild);

      // create corresponding node for max heap
      HeapNode hNode = new HeapNode(b, upperWeight, level);

      // put into max heap
      liveNodeMaxHeap.put(hNode);
   }
   
   
   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 2, 2, 6, 5, 5};
      int c = 16;
      int n = 5;
      int [] x = new int [n + 1];
      System.out.println("Value of max loading is " + maxLoading(w, c, x));
      System.out.print("Loading vector is ");
      for (int i = 1; i <= n; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

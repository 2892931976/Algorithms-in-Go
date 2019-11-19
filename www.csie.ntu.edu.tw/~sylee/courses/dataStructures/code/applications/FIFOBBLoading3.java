
/** FIFO branch-and-bound loading
  * code to find best loading and its value */

package applications;

import dataStructures.*;

public class FIFOBBLoading3
{
   // top-level nested class
   private static class QNode
   {
      // data members
      QNode parent;         // pointer to parent node
      boolean leftChild;    // true iff left child of parent
      int weight;           // weight of partial solution
                            // defined by path to this node

      // constructor
      private QNode(QNode theParent, boolean theLeftChild, int theWeight)
      {
         parent = theParent;
         leftChild = theLeftChild;
         weight = theWeight;
      }
   }

   // class data members
   static int numberOfContainers;
   static int maxWeightSoFar;
   static ArrayQueue liveNodeQueue;
   static QNode bestENodeSoFar;
   static int [] bestLoading;

   /** FIFO branch-and-bound search of solution space
     * @param weight array of container weights
     * @param capacity capacity of ship
     * @param theBestLoading set to best loading
     * @return weight of best loading */
   public static int maxLoading(int [] weight, int capacity,
                                int [] theBestLoading)
   {
      // set class data members
      numberOfContainers = weight.length - 1;
      maxWeightSoFar = 0;
      liveNodeQueue = new ArrayQueue();
      liveNodeQueue.put(null);      // end-of-level marker
      QNode eNode = null;
      bestENodeSoFar = null;
      bestLoading = theBestLoading;

      // initialize for level 1 E-node
      int eNodeLevel = 1;
      int eNodeWeight = 0;
      int remainingWeight = 0;
      for (int j = 2; j <= numberOfContainers; j++)
         remainingWeight += weight[j];
   
      // search subset space tree
      while (true)
      {
         // check left child of E-node
         int leftChildWeight = eNodeWeight + weight[eNodeLevel];
         if (leftChildWeight <= capacity)
         {// feasible left child
            if (leftChildWeight > maxWeightSoFar)
               maxWeightSoFar = leftChildWeight;
            addLiveNode(leftChildWeight, eNodeLevel, eNode, true); 
         }
   
         // check right child
         if (eNodeWeight + remainingWeight > maxWeightSoFar)
            addLiveNode(eNodeWeight, eNodeLevel, eNode, false);
   
         eNode = (QNode) liveNodeQueue.remove();
         if (eNode == null)
         {// end of level
            if (liveNodeQueue.isEmpty()) break;  // no more live nodes
            liveNodeQueue.put(null);             // end-of-level pointer
            eNode = (QNode) liveNodeQueue.remove();
            eNodeLevel++;
            remainingWeight -= weight[eNodeLevel];
         }
   
         eNodeWeight = eNode.weight;
      }
   
      // construct bestLoading[] by following path from
      // bestENodeSoFar to root, bestLoading[numberOfContainers]
      // is set by addLiveNode
      for (int j = numberOfContainers - 1; j > 0; j--)
      {
         bestLoading[j] = (bestENodeSoFar.leftChild) ? 1 : 0;
         bestENodeSoFar = bestENodeSoFar.parent;
      }
   
      return maxWeightSoFar;
   }
   
   /** add a live node at level theLevel and having weight theWeight
     * to liveNodeQueue if not a leaf
     * if feasible leaf, set bestLoading[numberOfContainers] = 1
     * iff leftChild is true
     * @param theParent parent of new node
     * @param leftChild true iff new node is left child of theParent */
   private static void addLiveNode(int theWeight, int theLevel,
                                   QNode theParent, boolean leftChild)
   {
      if (theLevel == numberOfContainers)
      {// feasible leaf
         if (theWeight == maxWeightSoFar)
         {// best leaf so far
           bestENodeSoFar = theParent;
           bestLoading[numberOfContainers] = (leftChild) ? 1 : 0;
         }
         return;
     }
   
      // not a leaf, add to queue
      QNode b = new QNode(theParent, leftChild, theWeight);
      liveNodeQueue.put(b);
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

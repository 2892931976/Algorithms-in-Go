
/** first version of FIFO branch-and-bound code for two ship loading
  * code finds weight of best loading for first ship only */



package applications;

import dataStructures.*;

public class FIFOBBLoading1
{
   // class data members
   static int numberOfContainers;
   static int maxWeightSoFar;
   static ArrayQueue liveNodeQueue;
   
   
   /** FIFO branch-and-bound search of solution space
     * @param weight array of container weights
     * @param capacity capacity of ship
     * @return weight of best loading */
   public static int maxLoading(int [] weight, int capacity)
   {
      // set class data members
      numberOfContainers = weight.length - 1;
      maxWeightSoFar = 0;
      liveNodeQueue = new ArrayQueue();
      liveNodeQueue.put(new Integer(-1));  // end-of-level marker

      // initialize for level 1 E-node
      int eNodeLevel = 1;
      int eNodeWeight = 0;
   
      // search subset space tree
      while (true)
      {
         // check left child of E-node
         if (eNodeWeight + weight[eNodeLevel] <= capacity) 
            // left child
            addLiveNode(eNodeWeight + weight[eNodeLevel], eNodeLevel);
   
         // right child is always feasible
         addLiveNode(eNodeWeight, eNodeLevel);
   
         // get next E-node
         eNodeWeight = ((Integer) liveNodeQueue.remove()).intValue();
         if (eNodeWeight == -1)
         {// end of level
            if (liveNodeQueue.isEmpty())            // no more live nodes
               return maxWeightSoFar;
            liveNodeQueue.put(new Integer(-1));     // end-of-level marker
            // get next E-node
            eNodeWeight = ((Integer) liveNodeQueue.remove()).intValue();
            eNodeLevel++;
         }
      }
   }

   /** add node whose weight is theWeight to liove node queue
     * if not at a leaf */
   private static void addLiveNode(int theWeight, int theLevel)
   {
      if (theLevel == numberOfContainers)
      {// feasible leaf
         if (theWeight > maxWeightSoFar)  // better leaf reached
            maxWeightSoFar = theWeight;
       }
      else  // not a leaf
         liveNodeQueue.put(new Integer(theWeight));
   }
   

   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 2, 2, 6, 5, 5};
      int c = 16;
      System.out.println("Value of max loading is " + maxLoading(w, c));
   }
}

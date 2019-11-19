
/** second version of FIFO branch-and-bound code for two ship loading
  * code finds weight of best loading for first ship only */

package applications;

import dataStructures.*;

public class FIFOBBLoading2
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
            // add to queue unless leaf
            if (eNodeLevel < numberOfContainers)
               liveNodeQueue.put(new Integer(leftChildWeight));
         }
   
         // check right child
         if (eNodeWeight + remainingWeight > maxWeightSoFar
             && eNodeLevel < numberOfContainers)
             // right child may lead to better leaf
             liveNodeQueue.put(new Integer(eNodeWeight));
   
         // get next E-node
         eNodeWeight = ((Integer) liveNodeQueue.remove()).intValue();
         if (eNodeWeight == -1)
         {// end of level
            if (liveNodeQueue.isEmpty())         // no more live nodes
               return maxWeightSoFar;
            liveNodeQueue.put(new Integer(-1));  // end-of-level marker
            // get next E-node
            eNodeWeight = ((Integer) liveNodeQueue.remove()).intValue();
            eNodeLevel++;
            remainingWeight -= weight[eNodeLevel];
         }
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 2, 2, 6, 5, 5};
      int c = 16;
      System.out.println("Value of max loading is " + maxLoading(w, c));
   }
}

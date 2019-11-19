
/** refined recursive backtracking code for container loading
  * onto two ships
  * code finds weight of max loading for first ship only */

package applications;

public class RecursiveBTLoading2
{
   // class data members
   static int numberOfContainers;
   static int [] weight;
   static int capacity;
   static int weightOfCurrentLoading;
   static int maxWeightSoFar;
   static int remainingWeight;
   
   
   /** @param theWeight array of container weights
     * @param theCapacity capacity of ship
     * @return weight of max loading */
   public static int maxLoading(int [] theWeight, int theCapacity)
   {
      // set class data members
      numberOfContainers = theWeight.length - 1;
      weight = theWeight;
      capacity = theCapacity;
      weightOfCurrentLoading = 0;
      maxWeightSoFar = 0;
   
      // initialize remainingWeight to sum of all weights
      for (int i = 1; i <= numberOfContainers; i++)
         remainingWeight += weight[i];

      // compute weight of max loading
      rLoad(1);
      return maxWeightSoFar;
   }
   
   /** actual method to find max loading */
   private static void rLoad(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel > numberOfContainers)
      {// at a leaf
         maxWeightSoFar = weightOfCurrentLoading;
         return;
      }

      // not at a leaf, check subtrees
      remainingWeight -= weight[currentLevel];
      if (weightOfCurrentLoading + weight[currentLevel] <= capacity)
      {// try left subtree
         weightOfCurrentLoading += weight[currentLevel];
         rLoad(currentLevel + 1);
         weightOfCurrentLoading -= weight[currentLevel];
      }
      if (weightOfCurrentLoading + remainingWeight > maxWeightSoFar)
         // try right subtree
         rLoad(currentLevel+1);
      remainingWeight += weight[currentLevel];
   }


   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 7, 2, 6, 5, 4};
      int c = 10;
      System.out.println("Value of max loading is " + maxLoading(w, c));
   }
}

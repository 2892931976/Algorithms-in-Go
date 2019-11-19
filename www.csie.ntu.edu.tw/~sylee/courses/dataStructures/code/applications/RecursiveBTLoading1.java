
/** first recursive backtracking code to load containers onto 2 ships
  * code finds weight of max loading for first ship only */


package applications;

public class RecursiveBTLoading1
{
   // class data members
   static int numberOfContainers;
   static int [] weight;
   static int capacity;
   static int weightOfCurrentLoading;
   static int maxWeightSoFar;
   
   
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
   
      // compute weight of max loading
      rLoad(1);
      return maxWeightSoFar;
   }
   
   /** actual method to find max loading */
   private static void rLoad(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel > numberOfContainers)
      {// at a leaf
         if (weightOfCurrentLoading > maxWeightSoFar)
            maxWeightSoFar = weightOfCurrentLoading;
         return;
      }
      // not at a leaf, check subtrees
      if (weightOfCurrentLoading + weight[currentLevel] <= capacity)
      {// try left subtree; i.e., x[currentLevel] = 1
         weightOfCurrentLoading += weight[currentLevel];
         rLoad(currentLevel + 1);
         weightOfCurrentLoading -= weight[currentLevel];
      }
      rLoad(currentLevel + 1);  // try right subtree
   }

   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 7, 2, 6, 5, 4};
      int c = 10;
      System.out.println("Value of max loading is " + maxLoading(w, c));
   }
}

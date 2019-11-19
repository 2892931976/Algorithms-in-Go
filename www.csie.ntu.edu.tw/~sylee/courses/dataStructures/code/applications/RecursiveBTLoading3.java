
/** loading containers onto two ships via backtracking
  * code finds max loading of first ship as well as its weight
  * recursive version */

package applications;

public class RecursiveBTLoading3
{
   // class data members
   static int numberOfContainers;
   static int [] weight;
   static int capacity;
   static int weightOfCurrentLoading;
   static int maxWeightSoFar;
   static int remainingWeight;
   static int [] currentLoading;
   static int [] bestLoadingSoFar;
   
   
   /** @param theWeight array of container weights
     * @param theCapacity capacity of ship
     * @param bestLoading solution array
     * @return weight of max loading */
   public static int maxLoading(int [] theWeight, int theCapacity,
                                int [] bestLoading)
   {
      // set class data members
      numberOfContainers = theWeight.length - 1;
      weight = theWeight;
      capacity = theCapacity;
      weightOfCurrentLoading = 0;
      maxWeightSoFar = 0;
      currentLoading = new int [numberOfContainers + 1];
      bestLoadingSoFar = bestLoading;
   
      // initialize r to sum of all weights
      for (int i = 1; i <= numberOfContainers; i++)
         remainingWeight += weight[i];

      // compute weight of best loading
      rLoad(1);
      return maxWeightSoFar;
   }
   
   /** actual method to find best loading */
   private static void rLoad(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel > numberOfContainers)
      {// at a leaf, save better solution
         for (int j = 1; j <= numberOfContainers; j++)
            bestLoadingSoFar[j] = currentLoading[j];
         maxWeightSoFar = weightOfCurrentLoading;
         return;
      }

      // not at a leaf, check subtrees
      remainingWeight -= weight[currentLevel];
      if (weightOfCurrentLoading + weight[currentLevel] <= capacity)
      {// try left subtree
         currentLoading[currentLevel] = 1;
         weightOfCurrentLoading += weight[currentLevel];
         rLoad(currentLevel + 1);
         weightOfCurrentLoading -= weight[currentLevel];
      }
      if (weightOfCurrentLoading + remainingWeight > maxWeightSoFar)
      {
         currentLoading[currentLevel] = 0; // try right subtree
         rLoad(currentLevel + 1);
      }
      remainingWeight += weight[currentLevel];
   }


   /** test program */
   public static void main(String [] args)
   {
      int [] w = {0, 7, 2, 6, 5, 4};
      int c = 10;
      int n = 5;
      int [] x = new int [n + 1];

      // compute and output best loading
      System.out.println("Value of max loading is " + maxLoading(w, c, x));
      System.out.print("x values are ");
      for (int i = 1; i <= n; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

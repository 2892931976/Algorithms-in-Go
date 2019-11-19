
/** backtracking solution for knapsack problem */

package applications;

import utilities.*;

public class RecursiveBTKnapsack
{
   // top-level nested class
   private static class Element implements Comparable
   {
      // data members
      int id;   // object identifier
      double profitDensity;

      // constructor
      private Element(int theID, double theProfitDensity)
      {
         id = theID;
         profitDensity = theProfitDensity;
      }

      public int compareTo(Object x)
      {
         double xpd = ((Element) x).profitDensity;
         if (profitDensity < xpd) return -1;
         if (profitDensity == xpd) return 0;
         return 1;
      }

      public boolean equals(Object x)
         {return profitDensity == ((Element) x).profitDensity;}
   }


   // class data members
   static double capacity;
   static int numberOfObjects;
   static double [] weight;
   static double [] profit;
   static double weightOfCurrentPacking;
   static double profitFromCurrentPacking;
   static double maxProfitSoFar;
   
   /** @param theProfit[1:theProfit.length] is array of object profits
     * @param theWeight[1:theProfit.length] is array of object weights
     * @param theCapacity is knapsack capacity
     * @return profit of best filling */
   public static double knapsack(double [] theProfit, double [] theWeight,
                                 double theCapacity)
   {
      // set class data members
      capacity = theCapacity;
      numberOfObjects = theProfit.length - 1;
      weightOfCurrentPacking = 0.0;
      profitFromCurrentPacking = 0.0;
      maxProfitSoFar = 0.0;

      // define an Element array for profit densities
      Element [] q  = new Element [numberOfObjects];
   
      // set up densities in q[0:n-1]
      for (int i = 1; i <= numberOfObjects; i++)
         q[i - 1] = new Element(i, theProfit[i] / theWeight[i]);
   
      // sort into increasing density order
      MergeSort.mergeSort(q);
   
      // initialize class members
      profit = new double [numberOfObjects + 1];
      weight = new double [numberOfObjects + 1];
      for (int i = 1; i <= numberOfObjects; i++)
      {// profits and weights in decreasing density order
         profit[i] = theProfit[q[numberOfObjects - i].id];
         weight[i] = theWeight[q[numberOfObjects - i].id];
      }

      rKnap(1);  // compute max profit
      return maxProfitSoFar;
   }
      
   /** actual method to find value of best filling */
   private static void rKnap(int currentLevel)
   {// search from a node at currentLevel
      if (currentLevel > numberOfObjects)
      {// at a leaf
         maxProfitSoFar = profitFromCurrentPacking;
         return;
      }

      // not at a leaf, check subtrees
      if (weightOfCurrentPacking + weight[currentLevel] <= capacity)
      {// try left subtree
         weightOfCurrentPacking += weight[currentLevel];
         profitFromCurrentPacking += profit[currentLevel];
         rKnap(currentLevel + 1);
         weightOfCurrentPacking -= weight[currentLevel];
         profitFromCurrentPacking -= profit[currentLevel];
      }
      if (profitBound(currentLevel + 1) > maxProfitSoFar)
         rKnap(currentLevel + 1);   // try right subtree
   }
   
   
   /** bounding function
     * @return upper bound on value of best leaf in subtree */
   private static double profitBound(int currentLevel)
   {
      double remainingCapacity = capacity - weightOfCurrentPacking;
      double profitBound = profitFromCurrentPacking;
      // fill remaining capacity in order of profit density
      while (currentLevel <= numberOfObjects &&
             weight[currentLevel] <= remainingCapacity)
      {
         remainingCapacity -= weight[currentLevel];
         profitBound += profit[currentLevel];
         currentLevel++;
      }
   
      // take fraction of next object
      if (currentLevel <= numberOfObjects)
         profitBound += profit[currentLevel] / weight[currentLevel]
                           * remainingCapacity;
      return profitBound;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      double [] p = {0, 6, 3, 5, 4, 6};
      double [] w = {0, 2, 2, 6, 5, 4};
      int n = 5;
      int c = 10;
      System.out.println("Optimal value is " + knapsack(p, w, c));
   }
}

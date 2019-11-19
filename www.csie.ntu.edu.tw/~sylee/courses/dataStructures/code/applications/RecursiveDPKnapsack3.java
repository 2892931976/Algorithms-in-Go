

/** dynamic programming recursive knapsack without recomputation */

package applications;

import utilities.*;

public class RecursiveDPKnapsack3
{
   static int [] profit;
   static int [] weight;
   static int numberOfObjects;
   static int [][] fArray;  // array of f values

   /** set class data members and invoke method f 
     * @param theProfit[1:theProfit.length - 1] gives object profits
     * @param theWeight[1:theWeight.length-1] gives object weights
     * @return value of optimal knapsack filling */
   public static int knapsack(int [] theProfit, int [] theWeight,
                              int knapsackCapacity)
   {
      // set class data members
      profit = theProfit;
      weight = theWeight;
      numberOfObjects = theProfit.length - 1;
      fArray = new int [numberOfObjects + 1][knapsackCapacity + 1];
      for (int i = 1; i <= numberOfObjects; i++)
         for (int j = 0; j <= knapsackCapacity; j++)
            fArray[i][j] = -1;

      return f(1, knapsackCapacity);
   }
      
   /** recursive method to solve dynamic programming recurrence
     * without recomputing f values computed earlier
     * @return f(i,theCapacity) */
   private static int f(int i, int theCapacity)
   {
      // check if already computed
      if (fArray[i][theCapacity] >= 0)
         return fArray[i][theCapacity];
   
      // not yet computed
      if (i == numberOfObjects)
      {// use Equation 15.1
         // compute and save f(i,theCapacity)
         fArray[i][theCapacity] = (theCapacity < weight[numberOfObjects])
                                   ? 0 : profit[numberOfObjects];
         return fArray[i][theCapacity];
      }
      // use Equation 15.2
      if (theCapacity < weight[i])
         // object i does not fit
         fArray[i][theCapacity] = f(i + 1, theCapacity); 
      else
         // object i fits, try both possibilities
         fArray[i][theCapacity] = Math.max(f(i + 1, theCapacity),
                      f(i + 1, theCapacity - weight[i]) + profit[i]);
   
      return fArray[i][theCapacity];
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of objects and knapsack capacity");
      int n = keyboard.readInteger();
      int c = keyboard.readInteger();
      int [] p = new int [n + 1];
      int [] w = new int [n + 1];

      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter profit and weight of object " + i);
         p[i] = keyboard.readInteger();
         w[i] = keyboard.readInteger();
      }

      System.out.print("Optimal value is ");
      System.out.println(knapsack(p, w, c));
   }
}

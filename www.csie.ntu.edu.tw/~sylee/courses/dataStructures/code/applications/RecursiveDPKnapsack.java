

/** dynamic programming recursive knapsack */

package applications;

import utilities.*;

public class RecursiveDPKnapsack
{
   static int [] profit;
   static int [] weight;
   static int numberOfObjects;

   /** set class data members and invoke method f 
     * @param theProfit[1:theProfit.length - 1] gives object profits
     * @param theWeight[1:theWeight.length-1] gives object weights
     * @return value of optimal knapsack filling */
   public static int knapsack(int [] theProfit, int [] theWeight,
                              int knapsackCapacity)
   {
      profit = theProfit;
      weight = theWeight;
      numberOfObjects = theProfit.length - 1;
      return f(1, knapsackCapacity);
   }
      
   /** recursive method to solve dynamic programming recurrence
     * @return f(i,theCapacity) */
   private static int f(int i, int theCapacity)
   {
      if (i == numberOfObjects)
         return (theCapacity < weight[numberOfObjects])
                 ? 0 : profit[numberOfObjects];
      if (theCapacity < weight[i])
         return f(i + 1, theCapacity); 
      return Math.max(f(i + 1, theCapacity),
                      f(i + 1, theCapacity - weight[i]) + profit[i]);
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

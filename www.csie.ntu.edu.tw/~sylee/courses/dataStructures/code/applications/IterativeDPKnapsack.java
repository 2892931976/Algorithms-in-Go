
/** iterative dynamic programming knapsack */

package applications;

import utilities.*;

public class IterativeDPKnapsack
{
   /** iterative method to solve dynamic programming recurrence
     * computes f[1][c] and f[i][y], 2 <= i <= numberOfObjects,
     * 0 <= y <= knapsackCapacity
     * @param profit[1:profit.length - 1] gives object profits
     * @param weight[1:weight.length-1] gives object weights
     * @param knapsackCapacity is the knapsack capacity */
   public static void knapsack(int [] profit, int [] weight,
                               int knapsackCapacity, int [][] f)
   {
      int numberOfObjects = profit.length - 1;

      // initialize f[numberOfObjects][]
      int yMax = Math.min(weight[numberOfObjects] - 1, knapsackCapacity);
      for (int y = 0; y <= yMax; y++)
         f[numberOfObjects][y] = 0;
      for (int y = weight[numberOfObjects]; y <= knapsackCapacity; y++)
         f[numberOfObjects][y] = profit[numberOfObjects];
      
      // compute f[i][y], 1 < i < numberOfObjects
      for (int i = numberOfObjects - 1; i > 1; i--)
      {
         yMax = Math.min(weight[i] - 1, knapsackCapacity);
         for (int y = 0; y <= yMax; y++)
            f[i][y] = f[i + 1][y];
         for (int y = weight[i]; y <= knapsackCapacity; y++)
            f[i][y] = Math.max(f[i + 1][y],
                          f[i + 1][y - weight[i]] + profit[i]);
      }

      // compute f[1][knapsackCapacity]
      f[1][knapsackCapacity] = f[2][knapsackCapacity];
      if (knapsackCapacity >= weight[1])
         f[1][knapsackCapacity] = Math.max(f[1][knapsackCapacity],
                   f[2][knapsackCapacity-weight[1]] + profit[1]);
   }
   
   /** compute solution vector x */
   public static void traceback(int [][] f, int [] weight,
                                int knapsackCapacity, int [] x)
   {
      int numberOfObjects = weight.length - 1;

      for (int i = 1; i < numberOfObjects; i++)
         if (f[i][knapsackCapacity] == f[i+1][knapsackCapacity])
            // do not include object i
            x[i] = 0;
         else
         {// include object i
            x[i] = 1;
            knapsackCapacity -= weight[i];
          }
      x[numberOfObjects] = (f[numberOfObjects][knapsackCapacity] > 0)
                           ? 1 : 0;
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
      int [] x = new int [n + 1];
      int [][] f = new int [n + 1][c + 1];

      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter profit and weight of object " + i);
         p[i] = keyboard.readInteger();
         w[i] = keyboard.readInteger();
      }

      knapsack(p, w, c, f);

      System.out.print("Optimal value is ");
      System.out.println(f[1][c] + "\n");
      System.out.println("Rest of table is");
      for (int i = 2; i <= n; i++)
      {
         for (int j = 0; j <= c; j++)
            System.out.print(f[i][j] + " ");
         System.out.println();
      }

      traceback(f, w, c, x);

      System.out.println();
      for (int i = 1; i <= n; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

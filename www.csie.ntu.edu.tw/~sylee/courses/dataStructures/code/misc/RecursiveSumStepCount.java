
/** counting the steps in method rSum */

package misc;

import utilities.*;
import wrappers.*;

public class RecursiveSumStepCount
{
   static int count = 0;

   public static Computable recursiveSum(Computable [] a, int n)
   {// Driver for true recursive method rsum.
      count++; // for if-else statement
      if (a.length > 0)
         return rSum(a, n);
      else return null;  // no elements to sum
   }

   private static Computable rSum(Computable [] a, int n)
   {
      if (n == 0)
      {
         count++; // for conditional and return
         return (Computable) a[0].zero();
      }
      else
      {
         count++;  // for conditional, rSum invocation,
                   // add, and return
         return (Computable) rSum(a, n - 1).add(a[n-1]);
      }
   }
   
   /** test program */
   public static final void main(String [] args)
   {
      // generate a test array
      MyInteger[] z = new MyInteger[5];
      for (int i = 0; i < z.length; i++)
         z[i] = new MyInteger(i * i + 1);

      // output the array
      System.out.print("The test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();

      // compute and output the sum
      System.out.println("The sum of the integers is " +
                         recursiveSum(z,5));

      System.out.println("The step count is " + count);
   }
}



/** simplified step counting program */

package misc;

import utilities.*;
import wrappers.*;

public class SumStepCount2
{
   static int count = 0;
   
   public static Computable sum(Computable [] a, int n)
   {
      count++;
      if (a.length == 0) return null;
      for (int i = 0; i < n; i++)
         count += 2;

      count += 3;
      return null;
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
                         sum(z,5));

      System.out.println("The step count is " + count);
   }
}

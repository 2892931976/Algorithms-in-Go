
/** Generic method to recursively sum the objects
  * in an array a.
  * @return null if array has no objects
  * @return sum of the objects a[0:n-1] otherwise */

package applications;

import wrappers.*;
import utilities.*;

public class RecursiveSum
{
   public static Computable recursiveSum(Computable [] a, int n)
   {// Driver for true recursive method rsum.
      if (a.length > 0)
         return rSum(a, n);
      else return null;  // no elements to sum
   }

   /** Recursively compute the sum of the objects a[0:n-1].
     * a.length > 0.
     * @return sum of the objects a[0:n-1] */
     
   private static Computable rSum(Computable [] a, int n)
   {
      if (n == 0) return (Computable) a[0].zero();
      else return (Computable) rSum(a, n - 1).add(a[n-1]);
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
                         recursiveSum(z, 5));
   }
}

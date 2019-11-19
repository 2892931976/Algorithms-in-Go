
/** math utilities */

package utilities;

import exceptions.*;

public class MyMath
{
   /** @return sum of the integers a[0:n-1] */
   public static int sum(int [] a, int n)
   {
      int sum = 0;
      for (int i = 0; i < n; i++)
         sum += a[i];
      return sum;
   }

   /** Generic sum method.
     * @return null if array has no objects and
     * sum of the objects a[0:n-1] otherwise */
   public static Computable sum(Computable [] a, int n)
   {
      if (a.length == 0) return null;
      Computable sum = (Computable) a[0].zero();
      for (int i = 0; i < n; i++)
         sum.increment(a[i]);
      return sum;
   }

   /** Swap the integers a[i] and a[j]. */
   public static void swap(int [] a, int i, int j)
   {
      // Don't bother to check that indexes i and j
      // are in bounds. Java will do this and throw
      // an ArrayIndexOutOfBoundsException if i or
      // j is out of bounds.
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   /** Generic method to swap the object
     * references a[i] and a[j]. */
   public static void swap(Object [] a, int i, int j)
   {
      Object temp = a[i];
      a[i] = a[j];
      a[j] = temp;
   }

   /** @return n! */
   public static int factorial(int n)
   {
      if (n <= 1)
         return 1;
      else
         return n * factorial(n - 1);
   }

   /** generic method to find maximum object in a[0:n]
     * @throws IllegalArgumentException when n < 0
     * @return position of max element in a[0:n] */
   public static int max(Comparable [] a, int n)
   {
      if (n < 0)
         throw new IllegalArgumentException
               ("MyMath.max: Cannot find max of zero elements ");

      int positionOfCurrentMax = 0;
      for (int i = 1; i <= n; i++)
         if (a[positionOfCurrentMax].compareTo(a[i]) < 0)
            positionOfCurrentMax = i;
      return positionOfCurrentMax;
   }


   /** Test integer MyMath methods.
     * The generic methods are tested by
     * MyInteger.main. */
   
   public static void main(String [] args)
   {
      // create an integer array with 5 elements
      // you can change this to your own data
      int [] z = {2, 4, 5, 3, 1};
      
      // test integer sum
      // output the array
      System.out.print("The test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();

      // output the sum
      System.out.println("The sum is " + sum(z, z.length));

      // test integer swap
      swap(z, 0, z.length - 1);
      // output the array
      System.out.print("After swapping the first and last "
                        + "integers, the test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();

      // test factorial
      System.out.println("(-3)! = " + factorial(-3));
      System.out.println("5! = " + factorial(5));
   }
}


/** Change the length of an array by creating
  * a new array of the desired length and copying
  * elements from the old array to the new one. */
package utilities;

import wrappers.*;
import java.lang.reflect.*;

public class ChangeArrayLength 
{
   /** Change the length of the 1D array a.
     * @param n number of elements in a
     * @param newLength new length of array
     * @return array of length newLength with a[0:n-1] copied into it */
   public static Object [] changeLength1D(Object [] a,
                                 int n, int newLength)
   {
      // make sure new length is adequate
      if (n > newLength)
         throw new IllegalArgumentException
                   ("new length is too small");
   
      // allocate a new array of desired length and same type
      Object [] newArray = (Object []) Array.newInstance
                (a.getClass().getComponentType(), newLength);
   
      // copy from old space to new space
      System.arraycopy(a, 0, newArray, 0, n);
   
      return newArray;
   }

   /* Change the length of a 1D array with a.length elements */
   public static Object [] changeLength1D(Object [] a, int newLength)
      {return changeLength1D(a, a.length, newLength);}

   /** test program */
   public static void main(String [] args)
   {
      // create an array of length 4
      MyInteger [] x = {new MyInteger(10),
                        new MyInteger(11),
                        new MyInteger(12),
                        new MyInteger(13)};

      // output
      System.out.println("Array length is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 4; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   
      // increase array length to 8
      x = (MyInteger []) changeLength1D(x, 8);
   
      // add two elements to x
      for (int i = 4; i < 6; i++)
         x[i] = new MyInteger(10 + i);
   
      // output
      System.out.println("Array length is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 6; i++)
         System.out.print(x[i] + " ");
      System.out.println();

      // increase length to 10
      x = (MyInteger []) changeLength1D(x, 6, 10);
   
      // add four elements to x
      for (int i = 6; i < 10; i++)
         x[i] = new MyInteger(10 + i);
   
      // output
      System.out.println("Array length is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 10; i++)
         System.out.print(x[i] + " ");
      System.out.println();

      // reduce length to 5 retaining only first 3 elements
      x = (MyInteger []) changeLength1D(x, 3, 5);
   
      // output
      System.out.println("Array length is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 4; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

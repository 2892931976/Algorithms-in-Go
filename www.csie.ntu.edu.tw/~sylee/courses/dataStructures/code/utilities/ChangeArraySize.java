
/** Change the size of an array by creating
  * a new array of the desired size and copying
  * elements from the old array to the new one. */

package utilities;

import wrappers.*;
import java.lang.reflect.*;

public class ChangeArraySize 
{
   /** Change the size of the 1D array a.
     * @param n number of elements in a
     * @param newSize new size of array
     * @return array of size newSize with a[0:n-1]
     * copied into it */

   public static Object [] changeSize1D(Object [] a,
                                 int n, int newSize)
   {
      // make sure new size is adequate
      if (n > newSize)
         throw new IllegalArgumentException
            ("ChangeArraySize.changeSize1D: new size is too small");
   
      // allocate a new array of desired size
      // and same type
      Object [] newArray = (Object []) Array.newInstance
                (a.getClass().getComponentType(), newSize);
   
      // copy from old space to new space
      System.arraycopy(a, 0, newArray, 0, n);
   
      return newArray;
   }

   /* Change the size of a 1D array with a.length
    * elements. */
   public static Object [] changeSize1D(Object [] a,
                                 int newSize)
   {
      return changeSize1D(a, a.length, newSize);
   }

   /** test program */
   public static void main(String [] args)
   {
      // create an array of size 4
      MyInteger [] x = {new MyInteger(10),
                        new MyInteger(11),
                        new MyInteger(12),
                        new MyInteger(13)};

      // output
      System.out.println("Array size is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 4; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   
      // increase array size to 8
      x = (MyInteger []) changeSize1D(x, 8);
   
      // add two elements to x
      for (int i = 4; i < 6; i++)
         x[i] = new MyInteger(10 + i);
   
      // output
      System.out.println("Array size is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 6; i++)
         System.out.print(x[i] + " ");
      System.out.println();

      // increase size to 10
      x = (MyInteger []) changeSize1D(x, 6, 10);
   
      // add four elements to x
      for (int i = 6; i < 10; i++)
         x[i] = new MyInteger(10 + i);
   
      // output
      System.out.println("Array size is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 10; i++)
         System.out.print(x[i] + " ");
      System.out.println();

      // reduce size to 5 retaining only first 3 elements
      x = (MyInteger []) changeSize1D(x, 3, 5);
   
      // output
      System.out.println("Array size is " + x.length);
      System.out.print("The elements are ");
      for (int i = 0; i < 4; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

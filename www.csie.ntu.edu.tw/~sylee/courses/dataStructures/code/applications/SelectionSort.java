

/** selection sort */

package applications;

import utilities.*;

public class SelectionSort
{
   /** sort the array a using the selection sort method */
   public static void selectionSort(Comparable [] a)
   {
      for (int size = a.length; size > 1; size--)
      {
         // find max object in a[0:size-1]
         int j = MyMath.max(a, size-1);

         // move max object to right end
         MyMath.swap(a, j, size - 1);
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(3),
                      new Integer(2),
                      new Integer(4),
                      new Integer(1),
                      new Integer(6),
                      new Integer(9),
                      new Integer(8),
                      new Integer(7),
                      new Integer(5),
                      new Integer(0)};

      // output elements to be sorted
      System.out.println("The elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      // sort the elements
      selectionSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}

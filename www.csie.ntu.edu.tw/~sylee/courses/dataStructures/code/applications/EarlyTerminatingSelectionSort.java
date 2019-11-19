
/** early terminating selection sort */

package applications;

import utilities.*;

public class EarlyTerminatingSelectionSort
{
   /** sort the array a using the selection sort method */
   public static void selectionSort(Comparable [] a)
   {// Early-terminating version of selection sort.
      boolean sorted = false;
      for (int size = a.length; !sorted && (size > 1); size--)
      {
         int pos = 0;
         sorted = true;

         // find largest
         for (int i = 1; i < size; i++)
            if (a[pos].compareTo(a[i]) <= 0)
               pos = i;
            else
               sorted = false; // out of order

         MyMath.swap(a, pos, size - 1);
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

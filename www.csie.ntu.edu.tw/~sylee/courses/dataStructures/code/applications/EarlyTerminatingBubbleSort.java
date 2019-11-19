
/** early terminating bubble sort */

package applications;

import utilities.*;

public class EarlyTerminatingBubbleSort
{
   /** bubble largest element in a[0:n-1] to right */
   private static boolean bubble(Comparable [] a, int n)
   {
      boolean swapped = false; // no swaps so far
      for (int i = 0; i < n - 1; i++)
         if (a[i].compareTo(a[i+1]) > 0)
         {
            MyMath.swap(a, i, i + 1);
            swapped = true; // swap was done
         }
      return swapped;
   }
   
   /** early-terminating bubble sort */
   public static void bubbleSort(Comparable [] a)
   {
      for (int i = a.length; i > 1 && bubble(a, i); i--);
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
      bubbleSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}

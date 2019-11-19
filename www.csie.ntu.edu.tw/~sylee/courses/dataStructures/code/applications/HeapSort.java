
/** heap sort */

package applications;

import dataStructures.*;

public class HeapSort
{
   /** sort the elements a[1 : a.length - 1] using
     * the heap sort method */
   public static void heapSort(Comparable [] a)
   {
      // create a max heap of the elements
      MaxHeap h = new MaxHeap();
      h.initialize(a, a.length - 1);
   
      // extract one by one from the max heap
      for (int i = a.length - 2; i >= 1; i--)
         a[i + 1] = h.removeMax();
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
      for (int i = 1; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      // sort the elements
      heapSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 1; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}


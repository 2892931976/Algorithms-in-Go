
/** iterative merge sort */

package applications;

import utilities.*;

public class MergeSort
{
   /** sort the elements a[0 : a.length - 1] using
     * the merge sort method */
   public static void mergeSort(Comparable [] a)
   {
      Comparable [] b = new Comparable [a.length];
      int segmentSize = 1;
      while (segmentSize < a.length)
      {
         mergePass(a, b, segmentSize); // merge from a to b
         segmentSize += segmentSize;
         mergePass(b, a, segmentSize); // merge from b to a
         segmentSize += segmentSize;
      }
   }

   /** merge adjacent segments from x to y */
   public static void mergePass(Comparable [] x, Comparable [] y,
                                int segmentSize)
   {
      int i = 0;   // start of the next segment
      while (i <= x.length - 2 * segmentSize)
      {// merge two adjacent segments from x to y
         merge(x, y, i, i + segmentSize - 1, i + 2 * segmentSize - 1);
         i = i + 2 * segmentSize;
      }
   
      // fewer than 2 full segments remain
      if (i + segmentSize < x.length)
         // 2 segments remain
         merge(x, y, i, i + segmentSize - 1, x.length - 1);
      else
         // 1 segment remains, copy to y
         for (int j = i; j < x.length; j++)
            y[j] = x[j];
   }
   
   /** merge two adjacent segments from c to d */
   public static void merge(Comparable [] c, Comparable [] d,
                 int startOfFirst, int endOfFirst, int endOfSecond)
   {
      int first = startOfFirst,       // cursor for first segment
          second = endOfFirst + 1,    // cursor for second
          result = startOfFirst;      // cursor for result
   
      // merge until one segment is done
      while ((first <= endOfFirst) && (second <= endOfSecond))
         if (c[first].compareTo(c[second]) <= 0)
            d[result++] = c[first++];
         else
            d[result++] = c[second++];
   
      // take care of leftovers
      if (first > endOfFirst)
         for (int q = second; q <= endOfSecond; q++)
             d[result++] = c[q];
      else
         for (int q = first; q <= endOfFirst; q++)
             d[result++] = c[q];
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
      mergeSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}


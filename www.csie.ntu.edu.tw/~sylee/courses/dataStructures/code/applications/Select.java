
/** find an element by rank */

package applications;

import utilities.*;

public class Select
{
   // data member
   static Comparable [] a;   // array of elements

   /** @return k'th smallest element in a[0 : a.length - 1] */
   public static Comparable select(Comparable [] a, int k)
   {
      if (k < 1 || k > a.length)
         throw new IllegalArgumentException
                   ("k must be between 1 and a.length");

      Select.a = a;
      // move largest element to right end
      MyMath.swap(a, a.length - 1, MyMath.max(a, a.length - 1));
      return select(0, a.length - 1, k);
   }
   
   /** @return k'th element in a[leftEnd:rightEnd] */
   private static Comparable select(int leftEnd, int rightEnd, int k)
   {
      if (leftEnd >= rightEnd) return a[leftEnd];
      int leftCursor = leftEnd,      // left-to-right cursor
          rightCursor = rightEnd + 1;  // right-to-left cursor
      Comparable pivot = a[leftEnd];
   
      // swap elements >= pivot on left side
      // with elements <= pivot on right side
      while (true)
      {
         do
         {// find >= element on left side
            leftCursor++;
         } while (a[leftCursor].compareTo(pivot) < 0);

         do
         {// find <= element on right side
            rightCursor--;
         } while (a[rightCursor].compareTo(pivot) > 0);

         if (leftCursor >= rightCursor) break;  // swap pair not found
         MyMath.swap(a, leftCursor, rightCursor);
      }
   
      if (rightCursor - leftEnd + 1 == k) return pivot;

      // place pivot
      a[leftEnd] = a[rightCursor];
      a[rightCursor] = pivot;
   
      // recursive call on one segment
      if (rightCursor - leftEnd + 1 < k)
         return select(rightCursor + 1, rightEnd,
                       k - rightCursor + leftEnd - 1);
      else return select(leftEnd, rightCursor - 1, k);
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

      // output elements
      System.out.println("The elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      // output by rank
      for (int i = 1; i <= a.length; i++)
         System.out.println("The " + i + " element is " + select(a, i));
   }
}


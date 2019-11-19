/** divide and conquer method to find the positions of the
   * minimum and maximum elements in array a. */

package applications;

public class MinMax3
{
   /** @throws IllegalArgumentException when a has no elements
     * @return a MinMaxPair p such that p.min and p.max give the position
     * of the min and max elements in a[0:a.length-1] */
   public static MinMaxPair minMax(Comparable [] a)
   {
      // special cases, a.length <= 1
      if (a.length < 1)
         throw new IllegalArgumentException
                   ("Cannot find min and max of zero elements");
      if (a.length == 1) return new MinMaxPair(0, 0);

      // a.length > 1, initialize Min and Max
      int s = 1;              // start point for loop
      MinMaxPair p;           // current min and max
      if (a.length % 2 == 1)  // a.length is odd
         p = new MinMaxPair(0, 0);
      else
      {// a.length is even, compare first pair
         if (a[0].compareTo(a[1]) > 0) p = new MinMaxPair(1, 0);
         else p = new MinMaxPair(0, 1);
         s = 2;
      }
   
      // compare remaining pairs
      for (int i = s; i < a.length; i += 2)
      {
         // find larger of a[i] and a[i + 1], then compare larger one
         // with a[p.max] and smaller one with a[p.min]
         if (a[i].compareTo(a[i + 1]) > 0)
         {
            if (a[i].compareTo(a[p.max]) > 0) p.max = i;
            if (a[i + 1].compareTo(a[p.min]) < 0) p.min = i + 1;
         }
         else
         {
            if (a[i + 1].compareTo(a[p.max]) > 0) p.max = i + 1;
            if (a[i].compareTo(a[p.min]) < 0) p.min = i;
         }
      }

      return p;
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

      // locate min and max elements
      MinMaxPair p = minMax(a);

      // output
      System.out.println("The minimum is at " + p.min);
      System.out.println("The maximum is at " + p.max);
   }
}

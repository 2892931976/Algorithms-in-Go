
/** first method to find the positions of the
   * minimum and maximum elements in array a */

package applications;

public class MinMax1
{
   /** @throws IllegalArgumentException when a has no elements
     * @return a MinMaxPair p such that p.min and p.max give the position
     * of the min and max elements in a[0:a.length-1] */
   public static MinMaxPair minMax(Comparable [] a)
   {
      if (a.length < 1)
         throw new IllegalArgumentException
                   ("Cannot find min and max of zero elements");
         
      // guess that a[0] is min and max
      MinMaxPair p = new MinMaxPair(0,0);

      // update guess
      for (int i = 1; i < a.length; i++)
      {
         if (a[p.min].compareTo(a[i]) > 0) p.min = i;
         if (a[p.max].compareTo(a[i]) < 0) p.max = i;
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

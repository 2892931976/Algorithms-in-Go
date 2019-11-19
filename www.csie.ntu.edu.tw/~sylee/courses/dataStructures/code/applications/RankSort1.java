
/** rank or count sort using an additional
  * array to rearrange the elements */

package applications;

import utilities.*;

public class RankSort1
{
   /** sort the array a using the rank sort method */
   public static void rankSort(Comparable [] a)
   {
      // create rank array
      int [] r = new int [a.length];

      // rank the elements
      Rank.rank(a, r);

      // rearrange into sorted order
      rearrange(a, r);
   }

   /** rearrange objects by rank using an additional array
     * @param a is the object array
     * @param r is the rank array */
   private static void rearrange(Comparable [] a, int [] r)
   {
      // create an additional array u
      Comparable [] u = new Comparable [a.length];

      // move references to correct place in u
      for (int i = 0; i < a.length; i++)
         u[r[i]] = a[i];

      // move back to a
      for (int i = 0; i < a.length; i++)
         a[i] = u[i];
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
      rankSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}

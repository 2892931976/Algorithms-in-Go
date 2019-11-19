/** rank the objects in an array */ 

package applications;

import utilities.*;

public class Rank
{
   /** @param a is the array of objects to be ranked
     * @param r is the array of computed ranks
     * @throws IllegalArgumentException
     * when the length of r is smaller than that of a */
   public static void rank(Comparable [] a, int [] r)
   {// Rank the objects in a[].
      // make sure rank array is large enough
      if (r.length < a.length)
         throw new IllegalArgumentException
               ("length of rank array cannot " +
                "be less than the number of objects");

      // set all ranks to zero
      for (int i = 0; i < a.length; i++)
         r[i] = 0;

      // compare all pairs of objects
      for (int i = 1; i < a.length; i++)
         for (int j = 0; j < i; j++)
            if (a[j].compareTo(a[i]) <= 0) r[i]++;
            else r[j]++;
   }

   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(2),
                      new Integer(6),
                      new Integer(4),
                      new Integer(3),
                      new Integer(1),
                      new Integer(5)};
      int [] r = new int [10];

      // output elements to be ranked
      System.out.println("The elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      // rank the elements
      rank(a, r);

      // output the ranks
      System.out.println("The ranks are");
      for (int i = 0; i < a.length; i++)
         System.out.print(r[i] + " ");
      System.out.println();
   }
}


/** ineffficient prefix sums */

package misc;

import utilities.*;

public class InefficientPrefixSums
{
   /** @return array of prefix sums */
   public static int [] inef(int [] a)
   {
      // create an array for the prefix sums
      int [] b = new int [a.length];

      // compute the prefix sums
      for (int j = 0; j < a.length; j++)
         b[j] = MyMath.sum(a, j + 1);
     
      return b;
   }

   public static void main(String [] args)
   {
      int [] a = {1, 2, 3, 4, 5, 6};

      // output the elements
      System.out.println("The elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      int [] b = inef(a);

      // output their prefix sums
      System.out.println("The prefix sums are");
      for (int i = 0; i < a.length; i++)
         System.out.print(b[i] + " ");
      System.out.println();
   }
}

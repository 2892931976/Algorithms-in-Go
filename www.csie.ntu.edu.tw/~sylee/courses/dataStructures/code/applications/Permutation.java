package applications;

import utilities.*;

public class Permutation
{
   /** perm(x, 0, n) outputs all permutations of x[0:n] */
   public static void perm(Object [] list, int k, int m)
   {// Generate all permutations of list[k:m].
      int i;
      if (k == m)
      {// list[k:m] has one permutation, output it
         for (i = 0; i <= m; i++)
             System.out.print(list[i]);
         System.out.println();
      }
      else
      // list[k:m] has more than one permutation
      // generate these recursively 
        for (i = k; i <= m; i++)
        {
           MyMath.swap(list, k, i);
           perm(list, k+1, m);
           MyMath.swap(list, k, i);
        }
   }

   /** test perm */
   public static void main(String [] args)
   {
      Integer[] z = new Integer[3];
      for (int i = 0; i < z.length; i++)
         z[i] = new Integer(i + 1);

      System.out.println("Testing the permutation method");
      // output the array
      System.out.print("The test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();

      System.out.println("The permutations are");
      perm(z, 0, z.length - 1);
   }
}

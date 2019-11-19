
/** time for some Matrix operations */

package misc;

import dataStructures.*;
import wrappers.*;

public class TimeMatrix
{
   public static void main(String [] args)
   {
      int n = 500;  // number of rows and columns
      Matrix a = new Matrix(n, n);
      Matrix b = new Matrix(n, n);
      for (int i = 1; i <= n; i++)
         for (int j = 1; j <= n; j++)
         {
            if (i >= j && i - j < 4)
               a.set(i, j, new MyInteger(3));
            else
               a.set(i, j, new MyInteger(0));
            if (i <= j && j - i < 2)
               b.set(i, j, new MyInteger(5));
            else
               b.set(i, j, new MyInteger(0));
         }
    
      int m = 10;  // repetition factor
      long startTime = System.currentTimeMillis();
      // add the matrices
      for (int i = 0; i < m; i++)
         a.add(b);
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Matrix add took " + (elapsedTime / m) + "ms");

      startTime = System.currentTimeMillis();
      // multiply the matrices
      a.multiply(b);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Matrix multiply took " + elapsedTime + "ms");

   }
}

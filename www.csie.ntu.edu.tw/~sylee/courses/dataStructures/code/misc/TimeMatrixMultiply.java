

/** time matrix multiply methods */

package misc;

import utilities.*;

public class TimeMatrixMultiply
{
   public static void main(String [] args)
   {
      int n = 700;  // matrix size
      int [][] a = new int [n][n];
      int [][] b = new int [n][n];
      int [][] c = new int [n][n];
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
         {
            a[i][j] = 2;
            b[i][j] = 3;
         }

      long startTime = System.currentTimeMillis();
      MatrixOperations.squareMultiply(a, b, c, n);
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("squareMultiply took " + elapsedTime + " ms");

      startTime = System.currentTimeMillis();
      MatrixOperations.squareMultiply2(a, b, c, n);
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("squareMultiply2 took " + elapsedTime + " ms");

      startTime = System.currentTimeMillis();
      MatrixOperations.fastSquareMultiply(a, b, c, n);
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("fastSquareMultiply took " + elapsedTime + " ms");
   }
}

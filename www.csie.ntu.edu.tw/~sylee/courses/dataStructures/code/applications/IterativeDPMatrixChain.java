   
/** iterative version of dynamic programming solution for
  * the matrix multiplication chains problem */

package applications;

import utilities.*;

public class IterativeDPMatrixChain
{
   /** compute costs and kay for all Mij's iteratively
     * @param r[] is array of dimensions
     * @param c is cost matrix
     * @param kay is recurrence selectors */
   public static void matrixChain(int [] r, int [][] c, int [][] kay)
   {
      int q = r.length - 2;   // number of matrices
   
      // initialize c[i][i], c[i][i+1], and kay[i][i+1]
      for (int i = 1; i < q; i++)
      {
         c[i][i] = 0;
         c[i][i + 1] = r[i] * r[i + 1] * r[i + 2];
         kay[i][i + 1] = i;
      }
      c[q][q] = 0;
      
      // compute remaining c's and kay's
      for (int s = 2; s < q; s++)
         for (int i = 1; i <= q - s; i++)
         {// min term for k = i
            c[i][i+s] = c[i][i] + c[i + 1][i + s]
                        + r[i] * r[i + 1] * r[i + s + 1];
            kay[i][i + s] = i;
   
            // remaining min terms
            for (int k = i + 1; k < i + s; k++)
            {
               int t = c[i][k] + c[k + 1][i + s]
                       + r[i] * r[k + 1] * r[i + s + 1];
               if (t < c[i][i + s])
               {// smaller min term, update c and kay
                  c[i][i + s] = t;
                  kay[i][i + s] = k;
               }
            }      
         }
   }
   
   /** output best way to compute Mij */
   public static void traceback(int [][] kay, int i, int j)
   {
      if (i == j)  // only one matrix
         return;
      traceback(kay, i, kay[i][j]);
      traceback(kay, kay[i][j] + 1, j);
      System.out.println("Multiply M " + i + ", " + kay[i][j] +
               " and M " + (kay[i][j] + 1) + ", " + j);
   }
   
   /** driver program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // initialize
      System.out.println("Enter number of matrices");
      int q = keyboard.readInteger();
      int [] r = new int [q + 2];
      int [][] kay = new int [q + 1][q + 1];
      int [][] c = new int [q + 1][q + 1];

      // input matrix data
      for (int i = 1; i <= q; i++)
      {
         System.out.println("Enter number of rows in matrix " + i);
         r[i] = keyboard.readInteger();
      }
      System.out.println("Enter number of columns in matrix " + q);
      r[q + 1] = keyboard.readInteger();

      // compute cost of best way to multiply
      matrixChain(r, c, kay);
      System.out.println("Minimum cost is " + c[1][q]);

      System.out.println( "\nCost matrix is");
      for (int i = 1; i <= q; i++)
      {
         for (int j = 1; j < i; j++)
            System.out.print(0 + " ");
         for (int j = i; j <= q; j++)
            System.out.print(c[i][j] + " ");
         System.out.println();
      }
      
      System.out.println("\nkay matrix is");
      for (int i = 1; i <= q; i++)
      {
         for (int j = 1; j <= i; j++)
            System.out.print(0 + " ");
         for (int j = i + 1; j <= q; j++)
            System.out.print(kay[i][j] + " ");
         System.out.println();
      }
      
      // output optimal multiplication sequence
      traceback(kay, 1, q);
   }
}


/** second version of recursive dynamic programming solution for
  * the matrix multiplication chains problem, this version
  * computes each c(i,j) at most once */ 

package applications;

import utilities.*;

public class RecursiveDPMatrixChain2
{
   static int [] r;      // r[i] is number of rows in matrix i
   static int [][] kay;  // recurence selector
   static int [][] c;
   
   /** set class data members and invoke method c */
   public static int matrixChain(int [] r, int [][] kay)
   {
      RecursiveDPMatrixChain2.r = r;
      RecursiveDPMatrixChain2.kay = kay;
      c = new int [r.length - 1][r.length - 1];
      return c(1, r.length - 2);
   }
   
   /** return c(i,j) and compute kay[i][j] = kay(i,j) */
   private static int c(int i, int j)
   {
      // check if already computed
      if (c[i][j] > 0)  // c(i,j) was computed earlier
         return c[i][j];

      // c(i,j) not computed before, compute it now
      if (i == j)
         return 0;  // one matrix
      if (i == j - 1)
      {// two matrices
         kay[i][i + 1] = i;
         c[i][j] = r[i] * r[i + 1] * r[i + 2];
         return c[i][j];
      }
   
      // more than two matrices
      // set u to min term for k = i
      int u = c(i,i) + c(i + 1, j) + r[i] * r[i + 1] * r[j + 1];
      kay[i][j] = i;
   
      // compute remaining min terms and update u
      for (int k = i + 1; k < j; k++)
      {
         int t = c(i, k) + c(k + 1, j) + r[i] * r[k + 1] * r[j + 1];
         if (t < u)
         {// smaller min term found, update u and kay[i][j]
            u = t;
            kay[i][j] = k;
         }
      }
   
      c[i][j] = u;
      return c[i][j];
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

      // input matrix data
      for (int i = 1; i <= q; i++)
      {
         System.out.println("Enter number of rows in matrix " + i);
         r[i] = keyboard.readInteger();
      }
      System.out.println("Enter number of columns in matrix " + q);
      r[q + 1] = keyboard.readInteger();

      // compute cost of best way to multiply
      System.out.println("Minimum cost is " + matrixChain(r, kay));

      // output optimal multiplication sequence
      traceback(kay, 1, q);
   }
}

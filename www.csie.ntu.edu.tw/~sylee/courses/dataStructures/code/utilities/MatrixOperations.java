
/** matrix operations */

package utilities;

public class MatrixOperations
{
   /** Add two rows x cols integer matrices.
     * Computes c = a + b. */
   public static void add (int [][] a, int [][] b, int [][] c,
                           int rows, int cols)
   {
      for (int i = 0; i < rows; i++)
         for (int j = 0; j < cols; j++)
            c[i][j] = a[i][j] + b[i][j];
   }

   /** in-place transpose of matrix
     * a[0:rows-1][0:rows-1] */
   public static void transpose(int [][] a, int rows)
   {
      for (int i = 0; i < rows; i++)
         for (int j = i+1; j < rows; j++)
         {
            // swap a[i][j] and a[j][i]
            int t = a[i][j];
            a[i][j] = a[j][i];
            a[j][i] = t;
        }  
   }
   
   /** Multiply two n x n integer matrices.
     * Computes c = a * b. */
   public static void squareMultiply(int [][] a, int [][] b,
                                     int [][] c, int n)
   {
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
         {
            int sum = 0;
            for (int k = 0; k < n; k++)
               sum += a[i][k] * b[k][j];
            c[i][j] = sum;
         }
   }
   
   /** Multiply two n x n integer matrices.
     * Computes c = a * b. */
   public static void squareMultiply2(int [][] a, int [][] b,
                                      int [][] c, int n)
   {
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            c[i][j] = 0;

      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            for (int k = 0; k < n; k++)
               c[i][j] += a[i][k] * b[k][j];
   }
   
   /** Multiply two n x n integer matrices.
     * Computes c = a * b. */
   public static void fastSquareMultiply(int [][] a, int [][] b,
                                         int [][] c, int n)
   {
      for (int i = 0; i < n; i++)
         for (int j = 0; j < n; j++)
            c[i][j] = 0;

      for (int i = 0; i < n; i++)
         for (int k = 0; k < n; k++)
            for (int j = 0; j < n; j++)
               c[i][j] += a[i][k] * b[k][j];
   }

   /** Multiply the m x n integer matrix a
     * and the n x p integer matrix b to get
     * the m x p integer matrix c.
     * Computes c = a * b. */
   public static void multiply(int [][] a, int [][] b, int [][] c,
                               int m, int n, int p)
   {
      for (int i = 0; i < m; i++)
         for (int j = 0; j < p; j++)
         {// compute c[i][j]
            int sum = 0;
            for (int k = 0; k < n; k++)
               sum += a[i][k] * b[k][j];
            c[i][j] = sum;
         }
   }

   public static void main(String [] args)
   {
      // define variables
      int rows = 2,
          cols = 2;
      int [][] a = new int [rows][cols],
               b = new int [rows][cols],
               c = new int [rows][cols];

      // initialize a and b
      a[0][0] = 1; a[0][1] = 2; a[1][0] = 3; a[1][1] = 4;
      b[0][0] = 1; b[0][1] = 2; b[1][0] = 3; b[1][1] = 4;

      // output a and b
      System.out.println("The first matrix is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(a[i][j] + "\t");
         System.out.println();
      }

      System.out.println("The second matrix is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(b[i][j] + "\t");
         System.out.println();
      }

      // test add
      add(a, b, c, rows, cols);

      System.out.println("Their sum is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(c[i][j] + "\t");
         System.out.println();
      }

      // test transpose
      transpose(a, rows);

      System.out.println("The transpose of the first matrix is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(a[i][j] + "\t");
         System.out.println();
      }

      // reset a
      transpose(a, rows);

      // test square multiply methods
      squareMultiply(a, b, c, rows);
      System.out.println("The product of the first two matrices is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(c[i][j] + "\t");
         System.out.println();
      }

      fastSquareMultiply(a, b, c, rows);
      System.out.println("The product of the first two matrices is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(c[i][j] + "\t");
         System.out.println();
      }

      // test multiply
      multiply(a, b, c, rows, rows, rows);
      System.out.println("The product of the first two matrices is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(c[i][j] + "\t");
         System.out.println();
      }
   }
}

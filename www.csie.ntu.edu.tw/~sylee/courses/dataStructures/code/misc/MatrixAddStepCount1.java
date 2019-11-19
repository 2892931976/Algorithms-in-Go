
/** counting the steps in matrix addition */

package misc;

public class MatrixAddStepCount1
{
   static int count = 0;

   /** Add two rows x cols integer matrices.
     * Computes c = a + b. */

   public static void add (int [][] a, int [][] b, int [][] c,
                           int rows, int cols)
   {
      for (int i = 0; i < rows; i++)
      {
         count++; // preceding for loop
         for (int j = 0;  j < cols; j++)
         {
            count++; // preceding for loop
            c[i][j] = a[i][j] + b[i][j];
            count++; // assignment
         }
         count++; // last time of j for loop
      }
      count++; // last time of i for loop
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

      add(a, b, c, rows, cols);

      System.out.println("Their sum is");
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0; j < cols; j++)
            System.out.print(c[i][j] + "\t");
         System.out.println();
      }

      System.out.println("The step count is " + count);
   }
}

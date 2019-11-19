

/** Counting the steps in matrix addition.
  * Simplified version. */

package misc;

public class MatrixAddStepCount2
{
   static int count = 0;

   /** Add two rows x cols integer matrices.
     * Computes c = a + b. */

   public static void add (int [][] a, int [][] b, int [][] c,
                           int rows, int cols)
   {
      for (int i = 0; i < rows; i++)
      {
         for (int j = 0;  j < cols; j++)
            count += 2;
         count += 2;
      }
      count++;
   }

   public static void main(String [] args)
   {
      // define variables
      int rows = 2,
          cols = 2;
      int [][] a = new int [rows][cols],
               b = new int [rows][cols],
               c = new int [rows][cols];

      add(a, b, c, rows, cols);

      System.out.println("The step count is " + count);
   }
}

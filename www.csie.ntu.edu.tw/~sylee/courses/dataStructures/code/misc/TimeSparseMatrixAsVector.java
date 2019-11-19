
/** time for some SparseMatrixAsVector operations */

package misc;

import dataStructures.*;
import wrappers.*;
import utilities.*;

public class TimeSparseMatrixAsVector
{
   public static void main(String [] args)
   {
      MyInputStream keyboard = new MyInputStream();
      SparseMatrixAsVector a = SparseMatrixAsVector.
                               input(new MyInteger(0), keyboard);
      SparseMatrixAsVector b = SparseMatrixAsVector.
                               input(new MyInteger(0), keyboard);
                     
      int m = 500;  // repetition factor
      long startTime = System.currentTimeMillis();
      // add the matrices
      for (int i = 0; i < m; i++)
         a.add(b);
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsVector add took "
                         + (elapsedTime / m) + "ms");


      startTime = System.currentTimeMillis();
      // transpose the matrices
      for (int i = 0; i < m; i++)
         a.transpose();
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsVector transpose took "
                         + (elapsedTime / m) + "ms");


   }
}

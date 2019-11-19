
/** time for some SparseMatrixAsExtendedArrayList operations */

package misc;

import dataStructures.*;
import wrappers.*;
import utilities.*;

public class TimeSparseMatrixAsExtendedArrayList
{
   public static void main(String [] args)
   {
      MyInputStream keyboard = new MyInputStream();
      SparseMatrixAsExtendedArrayList a =
            SparseMatrixAsExtendedArrayList.
                               input(new MyInteger(0), keyboard);
      SparseMatrixAsExtendedArrayList b =
            SparseMatrixAsExtendedArrayList.
                               input(new MyInteger(0), keyboard);
                     
      int m = 500;  // repetition factor
      long startTime = System.currentTimeMillis();
      // add the matrices
      for (int i = 0; i < m; i++)
         a.add(b);
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsExtendedArrayList add took "
                         + (elapsedTime / m) + "ms");


      startTime = System.currentTimeMillis();
      // transpose the matrices
      for (int i = 0; i < m; i++)
         a.transpose();
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsExtendedArrayList transpose took "
                         + (elapsedTime / m) + "ms");


   }
}

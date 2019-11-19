
/** time for some SparseMatrixAsExtendedArrayLinearList operations */

package misc;

import dataStructures.*;
import wrappers.*;
import utilities.*;

public class TimeSparseMatrixAsExtendedArrayLinearList
{
   public static void main(String [] args)
   {
      MyInputStream keyboard = new MyInputStream();
      SparseMatrixAsExtendedArrayLinearList a =
            SparseMatrixAsExtendedArrayLinearList.
                               input(new MyInteger(0), keyboard);
      SparseMatrixAsExtendedArrayLinearList b =
            SparseMatrixAsExtendedArrayLinearList.
                               input(new MyInteger(0), keyboard);
                     
      int m = 500;  // repetition factor
      long startTime = System.currentTimeMillis();
      // add the matrices
      for (int i = 0; i < m; i++)
         a.add(b);
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsExtendedArrayLinearList add took "
                         + (elapsedTime / m) + "ms");


      startTime = System.currentTimeMillis();
      // transpose the matrices
      for (int i = 0; i < m; i++)
         a.transpose();
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SparseMatrixAsExtendedArrayLinearList transpose took "
                         + (elapsedTime / m) + "ms");


   }
}

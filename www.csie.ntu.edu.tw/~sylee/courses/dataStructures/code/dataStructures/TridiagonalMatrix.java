

/** tridiagonal matrix mapped into a one-dimensional array */

package dataStructures;

import java.util.*;
import utilities.*;
import wrappers.*;

public class TridiagonalMatrix
{
   // data members
   int rows;            // matrix dimension
   Object zero;         // zero element
   Object [] element;   // element array

   // constructor
   /** @throws IllegalArgumentException when theRows < 1 */
   public TridiagonalMatrix(int theRows, Object theZero)
   {
      // validate theRows
      if (theRows < 1)
         throw new IllegalArgumentException
               ("number of rows must be > 0");
   
      // create and initialize the matrix
      rows = theRows;
      zero = theZero;
      element = new Object [3 * rows - 2];
      for (int i = 0; i < 3 * rows - 2; i++)
         element[i] = zero;
   }
   
   // methods
   /** @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > rows */
   void checkIndex(int i, int j)
   {
      if (i < 1 || j < 1 || i > rows || j > rows)
         throw new IndexOutOfBoundsException
                   ("i = " + i + " j = " + j +
                    " rows = " + rows + " cols = " + rows);
   }

   /** @return the element this(i,j)
     * @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > rows */
   public Object get(int i, int j)
   {
      checkIndex(i, j);

      // determine element to return
      switch (i - j)
      {
         case 1: // lower diagonal
                 return element[i - 2];
         case 0: // main diagonal
                 return element[rows + i - 2];
         case -1: // upper diagonal
                 return element[2 * rows + i - 2];
         default: return zero;
      }
   }
   
   /** set this(i,j) = newValue 
     * @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > rows
     * @throws IllegalArgumentException when you try
     * to set a nontridiagonal element to nonzero */
   public void set(int i, int j, Object newValue)
   {
      checkIndex(i, j);

      // store newValue
      switch (i - j)
      {
         case 1: // lower diagonal
            element[i - 2] = newValue; break;
         case 0: // main diagonal
            element[rows + i - 2] = newValue; break;
         case -1: // upper diagonal
            element[2 * rows + i - 2] = newValue; break;
         default: if (!((Zero) newValue).equalsZero())
                      throw new IllegalArgumentException
                         ("elements not on tridiagonal must be zero");
      }
   }
   
   
   /** test program */
   public static void main(String [] args)
   {
      // create the matrix
      TridiagonalMatrix x = new TridiagonalMatrix(20, new MyInteger(0));

      // set a few elements
      x.set(1, 1, new MyInteger(22));
      x.set(5, 5, new MyInteger(44));
      x.set(8, 5, new MyInteger(0));
      x.set(7, 8, new MyInteger(55));

      // retrieve a few elements
      System.out.println(x.get(7,8));
      System.out.println(x.get(5,5));
      System.out.println(x.get(1,1));
      System.out.println(x.get(10,1));
      System.out.println(x.get(1,5));
   }
}

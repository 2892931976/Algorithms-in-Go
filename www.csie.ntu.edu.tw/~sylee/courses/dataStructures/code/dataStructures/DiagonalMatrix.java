
/** diagonal matrix mapped into a one-dimensional array */

package dataStructures;

import java.util.*;
import utilities.*;
import wrappers.*;

public class DiagonalMatrix
{
   // data members
   int rows;            // matrix dimension
   Object zero;         // zero element
   Object [] element;   // element array

   // constructor
   /** @throws IllegalArgumentException when theRows < 1 */
   public DiagonalMatrix(int theRows, Object theZero)
   {
      // validate theRows
      if (theRows < 1)
         throw new IllegalArgumentException("number of rows must be > 0");
   
      // create and initialize the matrix
      rows = theRows;
      zero = theZero;
      element = new Object [rows];
      for (int i = 0; i < rows; i++)
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
      if (i == j)
         return element[i-1];  // diagonal element
      else
         return zero;          // nondiagonal element
   }
   
   /** set this(i,j) = newValue
     * @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > rows
     * @throws IllegalArgumentException when you try
     * to set a nondiagonal element to nonzero */
   public void set(int i, int j, Object newValue)
   {
      checkIndex(i, j);

      if (i == j)
         // save the diagonal element
         element[i - 1] = newValue;
      else
         // nondiagonal element, newValue must be zero
         if (!((Zero) newValue).equalsZero())
            throw new IllegalArgumentException
                  ("nondiagonal elements must be zero");
   }
   
   
   /** test program */
   public static void main(String [] args)
   {
      // create the matrix
      DiagonalMatrix x = new DiagonalMatrix(20, new MyInteger(0));

      // set a few elements
      x.set(1, 1, new MyInteger(22));
      x.set(5, 5, new MyInteger(44));
      x.set(8, 5, new MyInteger(0));

      // retrieve a few elements
      System.out.println(x.get(5,5));
      System.out.println(x.get(1,1));
      System.out.println(x.get(10,1));
   }
}

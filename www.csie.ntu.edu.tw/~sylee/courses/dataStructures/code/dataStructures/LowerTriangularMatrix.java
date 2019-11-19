

/** lower triangular matrix mapped into a one-dimensional array */

package dataStructures;

import java.util.*;
import utilities.*;
import wrappers.*;

public class LowerTriangularMatrix
{
   // data members
   int rows;            // matrix dimension
   Object zero;         // zero element
   Object [] element;   // element array

   // constructor
   /** @throws IllegalArgumentException when theRows < 1 */
   public LowerTriangularMatrix(int theRows, Object theZero)
   {
      // validate theRows
      if (theRows < 1)
         throw new IllegalArgumentException
               ("number of rows must be > 0");
   
      // create and initialize the matrix
      rows = theRows;
      zero = theZero;
      element = new Object [rows * (rows + 1) / 2];
      for (int i = 0; i <  rows * (rows + 1) / 2; i++)
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

      // (i,j) in lower triangle iff i >= j
      if (i >= j)
         return element[i * (i - 1) / 2 + j - 1];
      else
         return zero;
   }
   
   /** set this(i,j) = newValue
     * @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > rows
     * @throws IllegalArgumentException when you try
     * to set a nonlowertriangle element to nonzero */
   public void set(int i, int j, Object newValue)
   {
      checkIndex(i, j);

      // store newValue
      // (i,j) in lower triangle iff i >= j
      if (i >= j)
         element[i * (i - 1) / 2 + j - 1] = newValue;
      else
         if (!((Zero) newValue).equalsZero())
            throw new IllegalArgumentException
                  ("elements not in lower triangle must be zero");
   }
   
   
   /** test program */
   public static void main(String [] args)
   {
      // create the matrix
      LowerTriangularMatrix x = new LowerTriangularMatrix(20, new MyInteger(0));

      // set a few elements
      x.set(1, 1, new MyInteger(22));
      x.set(5, 3, new MyInteger(44));
      x.set(8, 5, new MyInteger(0));
      x.set(10, 2, new MyInteger(55));

      // retrieve a few elements
      System.out.println(x.get(10,2));
      System.out.println(x.get(5,3));
      System.out.println(x.get(1,1));
      System.out.println(x.get(10,14));
      System.out.println(x.get(8,5));
   }
}

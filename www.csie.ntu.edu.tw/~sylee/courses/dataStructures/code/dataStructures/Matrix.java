
/** row-major representation of a matrix */

package dataStructures;

import utilities.*;
import wrappers.*;

public class Matrix implements CloneableObject
{
   // data members
   int rows, cols;      // matrix dimensions
   Object [] element;   // element array

   // constructor
   public Matrix(int theRows, int theColumns)
   {
      // validate theRows and theColumns
      if (theRows < 0 || theColumns < 0)
         throw new IllegalArgumentException
               ("number of rows and columns must be >= 0");
      if ((theRows == 0 && theColumns != 0) ||
          (theRows != 0 && theColumns == 0))
         throw new IllegalArgumentException
             ("both the number of rows and columns must equal " +
              "zero or both must be > 0");
   
      // create the matrix
      rows = theRows;
      cols = theColumns;
      element = new Object [rows * cols];
   }
   
   // methods
   /** @return a clone of the matrix */
   public Object clone()
   {
      // create a new matrix
      Matrix x = new Matrix(rows, cols);
   
      // copy each element of this into x
      for (int i = 0; i < rows * cols; i++)
         x.element[i] = ((CloneableObject) element[i]).clone();
     
      return x;
   }
   
   /** copy the references in m into this */
   public void copy(Matrix m)
   {
      if (this != m) // not a copy to self
      {
         rows = m.rows;
         cols = m.cols; 
         element = new Object [rows * cols];
   
         // copy each reference
         for (int i = 0; i < rows * cols; i++)
            element[i] = m.element[i];
      }
   }
   
   /** @throws IndexOutOfBoundsException when i < 1
     * or j < 1 or i > rows or j > cols */
   void checkIndex(int i, int j)
   {
      if (i < 1 || j < 1 || i > rows || j > cols)
         throw new IndexOutOfBoundsException
                   ("i = " + i + " j = " + j +
                    " rows = " + rows + " cols = " + cols);
   }
   
   /** @return the element this(i,j)
     * @throws IndexOutOfBoundsException when i or j invalid */
   public Object get(int i, int j)
   {
      checkIndex(i, j);
      return element[(i - 1) * cols + j - 1];
   }
   
   /** set this(i,j) = newValue
     * @throws IndexOutOfBoundsException when i or j invalid */
   public void set(int i, int j, Object newValue)
   {
      checkIndex(i, j);
      element[(i - 1) * cols + j - 1] = newValue;
   }
   
   /** @return this + m 
     * @throws IllegalArgumentException when matrices are incompatible */
   public Matrix add(Matrix m)
   {
      if (rows != m.rows || cols != m.cols)
         throw new IllegalArgumentException
               ("The matrices are incompatible");
   
      // create result matrix w
      Matrix w = new Matrix(rows, cols);
      int numberOfTerms = rows * cols;
      for (int i = 0; i < numberOfTerms; i++)
         w.element[i] = ((Computable) element[i]).add(m.element[i]);
   
      return w;
   }

   /** @return this - m
     * @throws IllegalArgumentException when matrices are incompatible */
   public Matrix subtract(Matrix m)
   {
      if (rows != m.rows || cols != m.cols)
         throw new IllegalArgumentException
               ("The matrices are incompatible");
   
      // create result matrix w
      Matrix w = new Matrix(rows, cols);
      int numberOfTerms = rows * cols;
      for (int i = 0; i < numberOfTerms; i++)
         w.element[i] = ((Computable) element[i]).subtract(m.element[i]);
   
      return w;
   }


   /** @return this * m
     * @throws IllegalArgumentException when matrices are incompatible */
   public Matrix multiply(Matrix m)
   {
      if (cols != m.rows)
         throw new IllegalArgumentException
               ("The matrices are incompatible");
   
      Matrix w = new Matrix(rows, m.cols);  // result matrix
   
      // define cursors for this, m, and w
      // initial values give location of (1,1) element
      int ct = 0, cm = 0, cw = 0;
   
      // compute w(i,j) for all i and j
      for (int i = 1; i <= rows; i++)
      {// compute row i of result
         for (int j = 1; j <= m.cols; j++)
         {// compute first term of w(i,j)
            Computable sum =  (Computable) ((Computable)element[ct])
                               .multiply(m.element[cm]);
   
            // add in remaining terms
            for (int k = 2; k <= cols; k++) 
            {
               ct++;  // next term in row i of this
               cm += m.cols;  // next in column j of m
               sum.increment(((Computable) element[ct]).multiply
                              (m.element[cm]));
            }
            w.element[cw++] = sum;  // save w(i,j)
   
            // reset to start of row and next column
            ct -= cols - 1;
            cm = j;
         }
   
         // reset to start of next row and first column
         ct += cols;
         cm = 0;
      }
   
      return w;
   }
   
   /** increment all elements of this by x */
   public void increment(Object x)
   {
      for (int i = 0; i < rows * cols; i++)
          ((Computable) element[i]).increment(x);
   }
   
   /** convert the matrix into a string so it can be output */
   public String toString()
   {
      StringBuffer s = new StringBuffer(); 
      int k = 0;  // index into element array

      for (int i = 0; i < rows; i++)
      // one row per line
      {
         // do row i
         for (int j = 0; j < cols; j++)
            s.append(element[i * cols + j].toString() + " ");
   
         // row i finished
         s.append("\n");
      }
   
      // create equivalent String
      return new String(s);
   }
   
   /** test program */
   public static void main(String [] args)
   {
      try {
         Matrix x = new Matrix(3,2);
         Matrix y, z = null;
         MyInteger q = null;
         for (int i = 1; i <= 3; i++)
            for (int j = 1; j <= 2; j++)
            {
               q = new MyInteger(2 * i + j);
               x.set(i, j, q);
            }

         System.out.println("x(3,1) = " + x.get(3,1));
         System.out.println("Matrix x is");
         System.out.println(x);

         // test clone and copy
         y = (Matrix) x.clone();
         // cannot copy into a null z
         z = new Matrix(0,0);
         z.copy(x);
         // change the last matrix entry of x
         q.setValue(0);  // also changes z but not y
         System.out.println("Matrix x is now");
         System.out.println(x);
         System.out.println("Matrix y (the clone of original x) is");
         System.out.println(y);
         System.out.println("Matrix z (the copy of original x) is");
         System.out.println(z);

         // test increment
         y.increment(new MyInteger(2));
         System.out.println("y incremented by 2 is");
         System.out.println(y);
         System.out.println("x is");
         System.out.println(x);

         // test add
         System.out.println("y + x is");
         System.out.println(y.add(x));


         // create a compatile matrix for multiply
         Matrix w = new Matrix(2,3);
         for (int i = 1; i <= 2; i++)
            for (int j = 1; j <= 3; j++)
               w.set(i, j, new MyInteger(i + j));
         System.out.println("Matrix w is");
         System.out.println(w);

         // test multiply
         System.out.println("y * w is");
         System.out.println(y.multiply(w));
      }
      catch (Exception e)
      {System.out.println(e);}
   }
}

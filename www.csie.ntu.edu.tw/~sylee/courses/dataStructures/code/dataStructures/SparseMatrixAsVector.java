
/** sparse matrix represented as a Vector */

package dataStructures;

import java.util.*;
import java.lang.reflect.*;
import utilities.*;
import wrappers.*;

public class SparseMatrixAsVector
{
   // nested top-level classes
   static class MatrixTerm
   {
      // data members
      int row;       // row index of the term
      int col;       // column index of the term
      Object value;  // term value
   
      // constructors
      MatrixTerm(int theRow, int theColumn, Object theValue)
      {
         row = theRow;
         col = theColumn;
         value = theValue;
      }

      MatrixTerm() {}
   
      /** convert the term into a string so it can be output */
      public String toString()
      {
         return new String("row = " + row + " column = " + col +
                           " value = " + value);
      }
   }

   /** term and index pair */
   static class TIPair
   {
      // data members
      MatrixTerm term;
      int index;

      // constructor
      TIPair(MatrixTerm theTerm, int theIndex)
      {
         term = theTerm;
         index = theIndex;
      }
   }

   // data members
   int rows;       // number of rows in matrix
   int cols;       // number of columns in matrix
   Object zero;    // zero element
   Vector terms;   // list of nonzero terms

   // constructors
   public SparseMatrixAsVector(int theRows, int theColumns,
                               int estimatedMaxSize, Object theZero)
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
      zero = theZero;
      terms = new Vector(estimatedMaxSize);
   }
   
   /** use a default estimated maximum size of 1 */
   public SparseMatrixAsVector(int theRows, int theColumns,
                               Object theZero)
      {this(theRows, theColumns, 1, theZero);}
   
   /** defaults are rows = cols = estimatedMaxSize = 1 */
   public SparseMatrixAsVector(Object theZero)
      {this(1, 1, 1, theZero);}
   
   // methods
   /** convert the matrix into a string so it can be output */
   public String toString()
   {
      StringBuffer s = new StringBuffer(); 

      // put matrix characteristics into s
      s.append("rows = " + rows + "  columns = " + cols + "\n");
      s.append("number of nonzero terms = " + terms.size() + "\n");
   
      // put terms, one per line into s
      // use a Vector enumerator
      Enumeration y = terms.elements();
      while (y.hasMoreElements())
         s.append(y.nextElement().toString() + "\n");
   
      // create equivalent String
      return new String(s);
   }
   
   /** input a sparse matrix into this from the given input stream */
   public static SparseMatrixAsVector input(Object theZero,
                                            MyInputStream stream)
   {
      Method inputMethod;
      Object [] inputMethodArgs = {stream};
      Class [] parameterTypes = {MyInputStream.class};
      try
      {
         // get the proper method to be used to read in the values
         inputMethod = theZero.getClass().
                          getMethod("input", parameterTypes);

         // create a default matrix for input
         SparseMatrixAsVector x = new SparseMatrixAsVector(theZero);
   
         // input matrix characteristics
         System.out.println("Enter number of rows, columns, " +
                            "and nonzero terms");
         x.rows = stream.readInteger();
         x.cols = stream.readInteger();
         int size = stream.readInteger();
         x.terms = new Vector(size);
      
         // should validate input values here, left as an exercise
   
         // input the nonzero terms
         for (int i = 0; i < size; i++)
         {
            System.out.println("Enter row and column of term " + (i+1));
            MatrixTerm newTerm = new MatrixTerm();
            newTerm.row = stream.readInteger();
            newTerm.col = stream.readInteger();
            newTerm.value = inputMethod.invoke(null, inputMethodArgs);
            // should validate input, left as an exercise
   
            x.terms.add(newTerm);  // put into the Vector
         }
         return x;
      }
      catch (Exception e)
      {System.out.println(e); throw new IllegalArgumentException
          ("bad input");
      }
   }

   /** @return the transpose of this
     * matrix values are not cloned */
   public SparseMatrixAsVector transpose()
   {
      int size = terms.size();  // number of nonzero terms
      // create result matrix
      SparseMatrixAsVector t = new SparseMatrixAsVector(cols,
                                    rows, size, zero);
      // make it look like t has size elements already
      t.terms.setSize(size);
   
      // initialize to compute transpose
      // default initial values are 0
      int [] colSize = new int[cols + 1];
      int [] rowNext = new int[cols + 1];
       
      // find number of entries in each column of this
      // use a Vector enumerator
      Enumeration y = terms.elements();
      while (y.hasMoreElements())
         colSize[((MatrixTerm) y.nextElement()).col]++;  
      
      // find the starting point of each row of t
      // rowNext[1] is 0 by default
      for (int i = 2; i <= cols; i++)
         rowNext[i] = rowNext[i - 1] + colSize[i - 1];  
      
      // perform the transpose copying from this to t
      // initialize enumerator
      y = terms.elements();
      while (y.hasMoreElements())
      {
         MatrixTerm x = (MatrixTerm) y.nextElement();
         MatrixTerm w = new MatrixTerm(x.col, x.row, x.value);
         int j = rowNext[x.col]++; // position in t
         // change the element at j
         t.terms.setElementAt(w, j);
      }
      return t;
   }

   /** @return next term in the matrix and its index */
   static TIPair nextPair(Enumeration enum, int columns)
   {
      if (enum.hasMoreElements())
      {
         MatrixTerm t = (MatrixTerm) enum.nextElement();
         // row-major index plus number of columns in matrix
         int index = t.row * columns + t.col;
         return new TIPair(t, index);
      }
      else return null;
   }

   /** @return this + m */
   public SparseMatrixAsVector add(SparseMatrixAsVector m)
   {
      // verify compatibility
      if (rows != m.rows || cols != m.cols)
         throw new IllegalArgumentException
            ("the matrices are incompatible");
   
      // create result matrix
      SparseMatrixAsVector w = new SparseMatrixAsVector
                                    (rows, cols, zero);
                                     
      // define enumerators for this and m
      Enumeration et = terms.elements();
      Enumeration em = m.terms.elements();
      TIPair tPair = nextPair(et, cols);  // a pair from this
      TIPair mPair = nextPair(em, cols);  // a pair from m

      // move through this and m adding like terms
      while (tPair != null && mPair != null)
          if (tPair.index < mPair.index)
          {// term from this comes first in row-major order
             MatrixTerm q = tPair.term;
             w.terms.add(new MatrixTerm(q.row, q.col, q.value));
             tPair = nextPair(et, cols);
          }
          else if (tPair.index == mPair.index)
               {// both in same position
                  // append to w only if sum is not zero
                  Zero sum = (Zero) ((Computable) tPair.term.value)
                                      .add(mPair.term.value);
                  if (!sum.equalsZero())
                     w.terms.add(new MatrixTerm(
                       tPair.term.row, tPair.term.col, sum));
                  tPair = nextPair(et, cols);
                  mPair = nextPair(em, cols);
               }
               else
               {// term from m comes first in row-major order
                  MatrixTerm q = mPair.term;
                  w.terms.add(new MatrixTerm(q.row, q.col, q.value));
                  mPair = nextPair(em, cols);
               }
   
      // copy over remaining terms from this
      if (tPair != null)
      {
         MatrixTerm q = tPair.term;
         w.terms.add(new MatrixTerm(q.row, q.col, q.value));
      }
      while (et.hasMoreElements())
      {
         MatrixTerm q = (MatrixTerm) et.nextElement();
         w.terms.add(new MatrixTerm(q.row, q.col, q.value));
      }

      // copy over remaining terms from m
      if (mPair != null)
      {
         MatrixTerm q = mPair.term;
         w.terms.add(new MatrixTerm(q.row, q.col, q.value));
      }
      while (em.hasMoreElements())
      {
         MatrixTerm q = (MatrixTerm) em.nextElement();
         w.terms.add(new MatrixTerm(q.row, q.col, q.value));
      }
      return w;
   }

   /** test program */
   public static void main(String [] args)
   {
      MyInteger myZero = new MyInteger(0);
      SparseMatrixAsVector a, b;

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();

      // test input and output
      a = SparseMatrixAsVector.input(myZero, keyboard);
      System.out.println("The matrix a is");
      System.out.println(a);
      System.out.println();
      b = SparseMatrixAsVector.input(myZero, keyboard);
      System.out.println("The matrix b is");
      System.out.println(b);
      System.out.println();

      // test transpose
      System.out.println("The transpose of a is");
      System.out.println(a.transpose());
      System.out.println();

      // test add
      System.out.println("The sum of a and b is");
      System.out.println(a.add(b));
      System.out.println();
   }
}

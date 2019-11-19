

/** iterator for FormulaBasedLinearList */

package dataStructures;

import java.util.*;

public class FormulaBasedLinearListIterator
             implements Enumeration
{
   // data members
   private FormulaBasedLinearList list;
      // list to be enumerated
   private int nextIndex;  // index of next element

   // constructor
   public FormulaBasedLinearListIterator
             (FormulaBasedLinearList theList)
   {
      list = theList;
      nextIndex = 0;
   }

   // methods
   /** @return true iff list has more elements */
   public boolean hasMoreElements()
   {
      return nextIndex < list.size;
   }

   /** @return next element in list
     * @exception ArrayIndexOutOfBoundsException
     * thrown if there is no next element */

   public Object nextElement()
   {
      if (nextIndex < list.size)
         return list.element[nextIndex++];
      else throw new ArrayIndexOutOfBoundsException
              ("FormualBasedLinearListIterator."
                + "nextElement: No next element");
   }

   /** test program */
   public static void main(String [] args)
   {
      // create the list [0, 1, 2, 3, 4]
      FormulaBasedLinearList x = new FormulaBasedLinearList();
      x.insertElementAt(new Integer(4), 0);
      x.insertElementAt(new Integer(3), 0);
      x.insertElementAt(new Integer(2), 0);
      x.insertElementAt(new Integer(1), 0);
      x.insertElementAt(new Integer(0), 0);

      // output using an iterator
      Enumeration y = x.elements();
      while (y.hasMoreElements())
         System.out.print(y.nextElement() + " ");
      System.out.println();
   }
}

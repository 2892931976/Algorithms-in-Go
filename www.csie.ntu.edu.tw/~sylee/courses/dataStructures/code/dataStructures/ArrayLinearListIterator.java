

/** iterator for ArrayLinearList */

package dataStructures;

import java.util.*;

class ArrayLinearListIterator implements Iterator
{
   // data members
   private ArrayLinearList list;
      // list to be iterated
   private int nextIndex;  // index of next element

   // constructor
   public ArrayLinearListIterator(ArrayLinearList theList)
   {
      list = theList;
      nextIndex = 0;
   }

   // methods
   /** @return true iff the list has a next element */
   public boolean hasNext()
      {return nextIndex < list.size;}

   /** @return next element in list
     * @throws NoSuchElementException
     * when there is no next element */
   public Object next()
   {
      if (nextIndex < list.size)
         return list.element[nextIndex++];
      else
         throw new NoSuchElementException("No next element");
   }

   /** unsupported method */
   public void remove()
   {
      throw new UnsupportedOperationException
                ("remove not supported");
   }   

   /** test program */
   public static void main(String [] args)
   {
      // create the list [0, 1, 2, 3, 4]
      ArrayLinearList x = new ArrayLinearList();
      x.add(0, new Integer(4));
      x.add(0, new Integer(3));
      x.add(0, new Integer(2));
      x.add(0, new Integer(1));
      x.add(0, new Integer(0));

      // output using an iterator
      Iterator y = x.iterator();
      while (y.hasNext())
         System.out.print(y.next() + " ");
      System.out.println();
   }
}

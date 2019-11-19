

/** array-based implementation of LinearList
  * the iterator class is a member class */

package dataStructures;

import java.util.*;
import utilities.*;

public class ArrayLinearListWithIterator implements LinearList
{
   // data members
   protected Object [] element;  // array of elements
   protected int size;           // number of elements in array

   // constructors
   /** create a list with initial capacity initialCapacity
     * @throws IllegalArgumentException when
     * initialCapacity < 1 */
   public ArrayLinearListWithIterator(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
                   ("initialCapacity must be >= 1");
      // size has the default initial value of 0
      element = new Object [initialCapacity];
   }

   /** create a list with initial capacity 10 */
   public ArrayLinearListWithIterator()
   {// use default capacity of 10
      this(10);
   }

   // methods
   /** @return true iff list is empty */
   public boolean isEmpty()
       {return size == 0;}

   /** @return current number of elements in list */
   public int size()
      {return size;}


   /** @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   void checkIndex(int index)
   {
      if (index < 0 || index >= size)
         throw new IndexOutOfBoundsException
             ("index = " + index + "  size = " + size);
   }


   /** @return element with specified index
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   public Object get(int index)
   {
      checkIndex(index);
      return element[index];
   }
   
   /** @return index of first occurrence of theElement,
     * return -1 if theElement not in list */
   public int indexOf(Object theElement)
   {
      // search element[] for theElement
      for (int i = 0; i < size; i++)
         if (element[i].equals(theElement))
            return i;

      // theElement not found
      return -1;
   }      
   
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1
     * @return removed element */
   public Object remove(int index)
   {
      checkIndex(index);

      // valid index, shift elements with higher index
      Object removedElement = element[index];
      for (int i = index + 1; i < size; i++)
         element[i-1] = element[i];
  
      element[--size] = null;   // enable garbage collection
      return removedElement;
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size */
   public void add(int index, Object theElement)
   {
      if (index < 0 || index > size)
         // invalid list position
         throw new IndexOutOfBoundsException
             ("index = " + index + "  size = " + size);

      // valid index, make sure we have space
      if (size == element.length)
         // no space, double capacity
         element = ChangeArrayLength.changeLength1D(element, 2 * size);

      // shift elements right one position
      for (int i = size - 1; i >= index; i--)
         element[i + 1] = element[i];

      element[index] = theElement;

      size++;
   }
   
   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 

      // put elements into the buffer
      for (int i = 0; i < size; i++)
         if (element[i] == null)
            s.append("null, ");
         else
            s.append(element[i].toString() + ", ");
      
      if (size > 0)
         s.delete(s.length() - 2, s.length());  // remove last ", "
         
      s.append("]");

      // create equivalent String
      return new String(s);
   }

   /** create and return an iterator */
   public Iterator iterator()
      {return new ArrayLinearListIterator();}

   private class ArrayLinearListIterator implements Iterator
   {
      // data member
      private int nextIndex;  // index of next element
   
      // constructor
      public ArrayLinearListIterator()
      {
         // nextIndex has the default initial value 0
      }
   
      // methods
      /** @return true iff list has a next element */
      public boolean hasNext()
         {return nextIndex < size;}
   
      /** @return next element in list
        * @throws NoSuchElementException
        * when there is no next element */
      public Object next()
      {
         if (nextIndex < size)
            return element[nextIndex++];
         else
            throw new NoSuchElementException("No next element");
      }

      /** unsupported method */
      public void remove()
      {
         throw new UnsupportedOperationException
                   ("remove not supported");
      }   
   }

   /** test program */
   public static void main(String [] args)
   {
      // create the list [0, 1, 2, 3, 4]
      ArrayLinearListWithIterator x =
                 new ArrayLinearListWithIterator();
      x.add(0, new Integer(0));
      x.add(1, new Integer(1));
      x.add(2, new Integer(2));
      x.add(3, new Integer(3));
      x.add(4, new Integer(4));

      // output using an iterator
      Iterator y = x.iterator();
      while (y.hasNext())
         System.out.print(y.next() + " ");
      System.out.println();
   }
}

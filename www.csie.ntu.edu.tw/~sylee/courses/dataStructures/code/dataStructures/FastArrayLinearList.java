
/** array-based implementation of LinearList */

package dataStructures;

import java.util.*;
import wrappers.*;
import utilities.*;

public class FastArrayLinearList implements LinearList
{
   // data members
   protected Object [] element;  // array of elements
   protected int size;           // number of elements in array

   // constructors
   /** create a list with initial capacity initialCapacity
     * @throws IllegalArgumentException when
     * initialCapacity < 1 */
   public FastArrayLinearList(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
               ("initialCapacity must be >= 1");
      // size has the default initial value of 0
      element = new Object [initialCapacity];
   }

   /** create a list with initial capacity 10 */
   public FastArrayLinearList()
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
         if (element[i].equals(theElement)) return i;

      // theElement not found
      return -1;
   }      
   
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   public Object remove(int index)
   {
      checkIndex(index);

      // valid index, shift elements with higher index
      Object removedElement = element[index];
      System.arraycopy(element, index + 1, element, index, size - index - 1);
  
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
         element = ChangeArrayLength.changeLength1D
                      (element, 2 * element.length);

      // shift elements right one position
      System.arraycopy(element, index, element, index + 1, size - index);

      element[index] = theElement;

      size++;
   }
   
   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 
      if (size > 0)
      {// nonempty list
         // do first element
         s.append(element[0].toString());
         // do remaining elements
         for (int i = 1; i < size; i++)
            s.append(", " + element[i].toString());
      }
      s.append("]");

      // create equivalent String
      return new String(s);
   }

   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      LinearList x = new FastArrayLinearList();

      // test size
      System.out.println("Initial size is " + x.size());

      // test isEmpty
      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      // test add
      x.add(0, new Integer(2));
      x.add(0, new Integer(6));
      x.add(0, new Integer(1));
      x.add(0, new Integer(4));
      System.out.println("List size is " + x.size());

      // test toString
      System.out.println("The list is " + x);

      // test indexOf
      int index = x.indexOf(new Integer(4));
      if (index < 0)
         System.out.println("4 not found");
      else System.out.println("The index of 4 is " + index);

      index = x.indexOf(new Integer(3));
      if (index < 0)
         System.out.println("3 not found");
      else System.out.println("The index of 3 is " + index);

      // test get
      System.out.println("Element at 0 is " + x.get(0));
      System.out.println("Element at 3 is " + x.get(3));

      // test remove
      x.remove(1);
      System.out.println("The list is " + x);
      x.remove(2);
      System.out.println("The list is " + x);

      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      System.out.println("List size is " + x.size());
   }
}

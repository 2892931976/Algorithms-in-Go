
/** formula-based implementation of LinearList */

package dataStructures;

import java.util.*;
import wrappers.*;
import utilities.*;

public class FormulaBasedLinearList implements LinearList
{
   // data members
   protected Object [] element;  // array of elements
   protected int size;           // number of elements in array

   // constructors
   /** create a list with initial capacity initialCapacity
     * @exception IllegalArgumentException thrown
     * if initialCapacity < 1 */
   public FormulaBasedLinearList(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
            ("FormulaBasedLinearList.constructor: " +
             "initialCapacity must be >= 1");
      // size has the default initial value of 0
      element = new Object [initialCapacity];
   }

   /** create a list with initial capacity 10 */
   public FormulaBasedLinearList()
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

   /** @return element with specified index
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size - 1 */
   public Object elementAt(int index)
   {
      if (index < 0 || index >= size)
         // invalid list position
         throw new IllegalArgumentException
            ("FormulaBasedLinearList.elementAt: " +
             "index must be between 0 and size - 1");

      return element[index];
   }
   
   /** @return index of first occurrence of elem,
     * return -1 if elem not in list */
   public int indexOf(Object elem)
   {
      // search element[] for elem
      for (int i = 0; i < size; i++)
         if (element[i].equals(elem)) return i;

      // elem not found
      return -1;
   }      
   
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size - 1 */
   public void removeElementAt(int index)
   {
      if (index < 0 || index >= size)
         // invalid list position
         throw new IllegalArgumentException
            ("FormulaBasedLinearList.removeElementAt: " +
             "index must be between 0 and size - 1");

      // valid index, shift elements with higher index
      for (int i = index + 1; i < size; i++)
         element[i-1] = element[i];
  
      size--;
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size */
   public void insertElementAt(Object obj, int index)
   {
      if (index < 0 || index > size)
         // invalid list position
         throw new IllegalArgumentException
            ("FormulaBasedLinearList.insertElementAt: " +
             "index must be between 0 and size");

      // valid index, make sure we have space
      if (size == element.length)
         // no space, double capacity
         element = ChangeArraySize.changeSize1D
                      (element, 2 * element.length);

      // shift elements right one position
      for (int i = size - 1; i >= index; i--)
         element[i + 1] = element[i];

      element[index] = obj;

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

   /** create and return an enumerator */
   public Enumeration elements()
      {return new FormulaBasedLinearListEnumerator(this);}

   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      LinearList x = new FormulaBasedLinearList();

      // test size
      System.out.println("Initial size is " + x.size());

      // test isEmpty
      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      // test insertElementAt
      x.insertElementAt(new MyInteger(2), 0);
      x.insertElementAt(new MyInteger(6), 1);
      x.insertElementAt(new MyInteger(1), 0);
      x.insertElementAt(new MyInteger(4), 2);
      System.out.println("List size is " + x.size());

      // test toString
      System.out.println("The list is " + x);

      // test indexOf
      int index = x.indexOf(new MyInteger(4));
      if (index < 0)
         System.out.println("4 not found");
      else System.out.println("The index of 4 is " + index);

      index = x.indexOf(new MyInteger(3));
      if (index < 0)
         System.out.println("3 not found");
      else System.out.println("The index of 3 is " + index);

      // test elementAt
      System.out.println("Element at 0 is " + x.elementAt(0));
      System.out.println("Element at 3 is " + x.elementAt(3));

      // test removeElementAt
      x.removeElementAt(1);
      System.out.println("The list is " + x);
      x.removeElementAt(2);
      System.out.println("The list is " + x);

      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      System.out.println("List size is " + x.size());
   }
}

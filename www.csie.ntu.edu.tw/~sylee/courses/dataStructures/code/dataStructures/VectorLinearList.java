/** array-based implementation of LinearList using a Vector */

package dataStructures;

import java.util.*;
import wrappers.*;
import utilities.*;

public class VectorLinearList implements LinearList
{
   // data members
   protected Vector element;

   // constructors
   /** create a list with initial capacity initialCapacity
     * @exception IllegalArgumentException thrown
     * if initialCapacity < 1 */
   public VectorLinearList(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
            ("VectorLinearList.constructor: " +
             "initialCapacity must be >= 1");
      element = new Vector(initialCapacity);
   }

   /** create a list with initial capacity 10 */
   public VectorLinearList()
   {// use default capacity of 10
      this(10);
   }

   // methods
   /** @return true iff list is empty */
   public boolean isEmpty()
       {return element.isEmpty();}

   /** @return current number of elements in list */
   public int size()
      {return element.size();}

   /** @return element with specified index
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size - 1 */

   public Object elementAt(int index)
   {
      try {return element.elementAt(index);}
      catch (Exception e) {
         throw new IllegalArgumentException
            ("VectorLinearList.elementAt: " +
             "index must be between 0 and size - 1");
         }
   }
   
   /** @return index of first occurrence of elem,
     * return -1 if elem not in list */
   public int indexOf(Object elem)
      {return element.indexOf(elem);}
   
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size - 1 */
   public void removeElementAt(int index)
   {
      try {element.removeElementAt(index);}
      catch (Exception e) {
         throw new IllegalArgumentException
            ("VectorLinearList.removeElementAt: " +
             "index must be between 0 and size - 1");
         }
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @exception IllegalArgumentException thrown
     * if index is not between 0 and size */
   public void insertElementAt(Object obj, int index)
   {
      try {element.insertElementAt(obj, index);}
      catch (Exception e) {
         throw new IllegalArgumentException
            ("VectorLinearList.insertElementAt: " +
             "index must be between 0 and size");
         }
   }
   
   /** convert to a string */
   public String toString()
   {
      return element.toString();
   }

   /** create and return an enumerator */
   public Enumeration elements()
   {
      return element.elements();
   }

   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      LinearList x = new VectorLinearList();

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

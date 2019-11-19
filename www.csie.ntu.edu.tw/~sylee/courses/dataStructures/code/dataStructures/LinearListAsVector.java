/** formula-based implementation of LinearList using a Vector */

package dataStructures;

import java.util.*;

public class LinearListAsVector implements LinearList
{
   // data members
   protected Vector element;

   // constructors
   /** create a list with initial capacity initialCapacity
     * @throws IllegalArgumentException when
     * initialCapacity < 1 */
   public LinearListAsVector(int initialCapacity)
   {
      if (initialCapacity < 1)
         throw new IllegalArgumentException
                   ("initialCapacity must be >= 1");
      element = new Vector(initialCapacity);
   }

   /** create a list with initial capacity 10 */
   public LinearListAsVector()
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
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1 */
   public Object get(int index)
   {
      try {return element.get(index);}
      catch (Exception e)
      {
         throw new IndexOutOfBoundsException
                   ("index = " + index + "  size = " + size());
      }
   }
   
   /** @return index of first occurrence of theElement,
     * return -1 if theElement not in list */
   public int indexOf(Object theElement)
      {return element.indexOf(theElement);}
   
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1
     * $return removed element */
   public Object remove(int index)
   {
      try {return element.remove(index);}
      catch (Exception e)
      {
         throw new IndexOutOfBoundsException
                   ("index = " + index + "  size = " + size());
      }
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size */
   public void add(int index, Object theElement)
   {
      try {element.add(index, theElement);}
      catch (Exception e)
      {
         throw new IndexOutOfBoundsException
                   ("index = " + index + "  size = " + size());
      }
   }
   
   /** convert to a string */
   public String toString()
      {return element.toString();}


   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      LinearList x = new LinearListAsVector();

      // test size
      System.out.println("Initial size is " + x.size());

      // test isEmpty
      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      // test put
      x.add(0, new Integer(2));
      x.add(1, new Integer(6));
      x.add(0, new Integer(1));
      x.add(2, new Integer(4));
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
      System.out.println(x.remove(1) + " removed");
      System.out.println("The list is " + x);
      System.out.println(x.remove(2) + " removed");
      System.out.println("The list is " + x);

      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      System.out.println("List size is " + x.size());
   }
}

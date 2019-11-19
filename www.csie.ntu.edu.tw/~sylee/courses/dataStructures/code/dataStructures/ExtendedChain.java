
/** Chain extended to include methods
  * clear and add (at end).  */

package dataStructures;

import java.util.*;

public class ExtendedChain extends Chain
                           implements ExtendedLinearList
{
   // data member
   protected ChainNode lastNode;

   // methods
   /** Remove the element with specified index.
     * All elements with higher index have their
     * index reduced by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size - 1
     * @return removed element */
   public Object remove(int index)
   {// overrides Chain.remove
      checkIndex(index);

      Object removedElement;
      if (index == 0)
      {  // remove first node
         removedElement = firstNode.element;
         firstNode = firstNode.next;
         if (size == 1)
            // list becomes empty
            lastNode = null;  // needed for garbage collection
      }
      else 
      {  // use q to get to predecessor of desired node
         ChainNode q = firstNode;
         for (int i = 0; i < index - 1; i++)
            q = q.next;

         removedElement = q.next.element;
         q.next = q.next.next; // remove desired node
         if (q.next == null)
            lastNode = q;
      }
      size--;
      return removedElement;
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @exception IndexOutOfBoundsException thrown
     * if index is not between 0 and size */
   public void add(int index, Object theElement)
   {// overrides Chain.add
      if (index < 0 || index > size)
         // invalid list position
         throw new IndexOutOfBoundsException
             ("index = " + index + "  size = " + size);

      if (index == 0)
      {// insert at front
          firstNode = new ChainNode(theElement, firstNode);
          if (firstNode.next == null)
             lastNode = firstNode;
      }
      else
      {// insert in interior or end  
          // find predecessor of new element
          ChainNode p = firstNode;
          for (int i = 0; i < index - 1; i++)
             p = p.next;

          // insert after p
          p.next = new ChainNode(theElement, p.next);
          if (p.next.next == null) lastNode = p.next;
      }
      size++;
   }
   
   /** Make the chain empty. */
   public void clear()
   {
      firstNode = lastNode = null;
      size = 0;
   }

   /** Add theElement to the right end of the chain. */
   public void add(Object theElement)
   {
      ChainNode y = new ChainNode(theElement, null);
      if (firstNode == null)
         // chain is empty
         firstNode = lastNode = y;
      else
      {   // attach y next to lastNode
          lastNode.next = y;
          lastNode = y;
      }
      size++;
   }

   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      ExtendedChain x = new ExtendedChain();

      // test size
      System.out.println("Initial size is " +
                         x.size());

      // test isEmpty
      if (x.isEmpty())
         System.out.println("The list is empty");
      else System.out.println("The list is not empty");

      // test add
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
      else
         System.out.println("The index of 4 is " + index);

      index = x.indexOf(new Integer(3));
      if (index < 0)
         System.out.println("3 not found");
      else
         System.out.println("The index of 3 is " + index);

      // test get
      System.out.println("Element at 0 is " +
                         x.get(0));
      System.out.println("Element at 3 is " +
                         x.get(3));

      // test remove
      System.out.println(x.remove(1) + " removed");
      System.out.println("The list is " + x);
      System.out.println(x.remove(2) + " removed");
      System.out.println("The list is " + x);

      if (x.isEmpty())
         System.out.println("The list is empty");
      else
         System.out.println("The list is not empty");

      System.out.println("List size is " + x.size());

      // test clear
      x.clear();
      if (x.isEmpty())
         System.out.println("The list is empty");
      else
         System.out.println("The list is not empty");

      System.out.println("List size is " + x.size());

      // test add
      x.add(new Integer(5));
      x.add(new Integer(6));
      x.add(new Integer(7));
      System.out.println("The list is " + x);

      System.out.println(x.remove(2) + " removed");
      x.add(new Integer(8));
      System.out.println("The list is " + x);
   }
}

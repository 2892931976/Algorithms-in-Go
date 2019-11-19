
/** linked implementation of LinearList
  * using simulated pointers */

package dataStructures;

import java.util.*;

public class SimulatedChain implements LinearList
{
   // data members
   protected int firstNode;
   protected int size;
   public static SimulatedSpace1 S = new SimulatedSpace1(10);

   // constructor
   /** create a list that is empty */
   public SimulatedChain(int initialCapacity)
   {
       firstNode = -1;
       // size has the default initial value 0
   }

   public SimulatedChain()
      {this(0);}

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

      // move to desired node
      int currentNode = firstNode;
      for (int i = 0; i < index; i++)
         currentNode = S.node[currentNode].next;

      return S.node[currentNode].element;
   }
   
   /** @return index of first occurrence of elem,
     * return -1 if elem not in list */
   public int indexOf(Object elem)
   {
      // search the chain for elem
      int currentNode = firstNode;
      int index = 0;  // index of currentNode
      while (currentNode != -1 &&
             !S.node[currentNode].element.equals(elem))
      {
         // move to next node
         currentNode = S.node[currentNode].next;
         index++;
      }

      // make sure we found matching element
      if (currentNode == -1) return -1;
      else return index;
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

      int removedNode;

      // removed node with index'th element from the chain
      // save node index of removed node in removedNode
      if (index == 0)
      {  // remove first node
         removedNode = firstNode;
         firstNode = S.node[firstNode].next;
      }
      else 
      {  // use q to get to index-1'th
         int q = firstNode;
         for (int i = 0; i < index - 1; i++)
            q = S.node[q].next;

         removedNode = S.node[q].next;

         // element to remove is in node removedNode, remove this node
         S.node[q].next = S.node[removedNode].next;
      }

      Object removedElement = S.node[removedNode].element;
      S.deallocateNode(removedNode);
      size--;
      return removedElement;
   }
   
   /** Insert an element with specified index.
     * All elements with equal or higher index
     * have their index increased by 1.
     * @throws IndexOutOfBoundsException when
     * index is not between 0 and size */
   public void add(int index, Object obj)
   {
      if (index < 0 || index > size)
         // invalid list position
         throw new IndexOutOfBoundsException
                   ("index = " + index + "  size = " + size);

      if (index == 0)
         // insert at front
         firstNode = S.allocateNode(obj, firstNode);
      else
      {   // find index - 1'th node
          int p = firstNode;
          for (int i = 0; i < index - 1; i++)
             p = S.node[p].next;

          // insert after p
          S.node[p].next = S.allocateNode(obj,
                              S.node[p].next);
      }
      size++;
   }
   
   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 

      // put elements into the buffer
      int currentNode = firstNode;
      while(currentNode != -1)
      {
         if (S.node[currentNode].element == null)
            s.append("null, ");
         else
            s.append(S.node[currentNode].element.toString() + ", ");
         currentNode = S.node[currentNode].next;
      }
      if (size > 0)
         s.delete(s.length() - 2, s.length());  // remove last ", "
      s.append("]");

      // create equivalent String
      return new String(s);
   }

   /** create and return an iterator */
   public Iterator elements()
   {
      return new SimulatedChainIterator();
   }

   /** simulated chain iterator */
   private class SimulatedChainIterator implements Iterator
   {
      // data member
      private int nextNode;
   
      // constructor
      public SimulatedChainIterator()
         {nextNode = firstNode;}
   
      // methods
      /** @return true iff list has a next element */
      public boolean hasNext()
         {return nextNode != -1;}
   
      /** @return next element in list
        * @throws NoSuchElementException
        * when there is no next element */
      public Object next()
      {
         if (nextNode != -1)
         {
            Object obj = S.node[nextNode].element;
            nextNode = S.node[nextNode].next;
            return obj;
         }
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
      // create simulated space with 100 nodes
      //SimulatedChain.S = new SimulatedSpace1(100);

      // test default constructor
      SimulatedChain x = new SimulatedChain();

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


package dataStructures;

import java.util.*;

public class SortedChain implements Dictionary
{
   // top-level nested class
   protected static class SortedChainNode
   {
      // data members
      protected Comparable key;
      protected Object element;
      protected SortedChainNode next;

      // constructors
      protected SortedChainNode() {}
     
      protected SortedChainNode(Object theKey, Object theElement)
      {
         key = (Comparable) theKey;
         element = theElement;
      }

      protected SortedChainNode(Object theKey, Object theElement,
                                SortedChainNode theNext)
      {
         key = (Comparable) theKey;
         element = theElement;
         next = theNext;
      }
   }

   // data members of SortedChain
   protected SortedChainNode firstNode;
   protected int size;

   // constructors
   /** create an empty sorted chain */
   public SortedChain(int initialCapacity)
   {
       // the default initial values of firstNode and size
       // are null and 0, respectively
   }

   public SortedChain()
      {this(0);}

   // methods
   /** @return true iff the chain is empty */
   public boolean isEmpty()
       {return size == 0;}

   /** @return current number of elements in list */
   public int size()
      {return size;}

   /** @return element with specified key
     * @return null if there is no matching element */
   public Object get(Object theKey)
   {
      SortedChainNode currentNode = firstNode;

      // search for match with theKey
      while (currentNode != null && 
             currentNode.key.compareTo(theKey) < 0)
         currentNode = currentNode.next;

      // verify match
      if (currentNode != null && currentNode.key.equals(theKey))
         // yes, found match
         return currentNode.element;

      // no match
      return null;
   }
   
   /** insert an element with the specified key
     * overwrite old element if there is already an
     * element with the given key
     * @return old element (if any) with key theKey */
   public Object put(Object theKey, Object theElement)
   {
      SortedChainNode p = firstNode,
                      tp = null; // tp trails p
   
      // move tp so that theElement can be inserted after tp
      while (p != null && p.key.compareTo(theKey) < 0)
      {
         tp = p;
         p = p.next;
      }
   
      // check if there is a matching element
      if (p != null && p.key.equals(theKey))
      {// replace old element
         Object elementToReturn = p.element;
         p.element = theElement;
         return elementToReturn;
      }
   
      // no match, set up node for theElement
      SortedChainNode q = new SortedChainNode(theKey, theElement, p);
   
      // insert node just after tp
      if (tp == null) firstNode = q;
      else tp.next = q;

      size++;
      return null;
   }

   /** @return matching element and remove it
     * @return null if no matching element */
   public Object remove(Object theKey)
   {
      SortedChainNode p = firstNode,
                      tp = null; // tp trails p
      
      // search for match with theKey
      while (p != null && p.key.compareTo(theKey) < 0)
      {
         tp = p;
         p = p.next;
      }
   
      // verify match
      if (p != null && p.key.equals(theKey)) 
      {// found a match
         Object e = p.element;  // the matching element
   
         // remove p from the chain
         if (tp == null) firstNode = p.next;  // p is first node
         else tp.next = p.next;
   
         size--;
         return e;
      }
   
      // no matching element to remove
      return null;
   }
   
   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 
      if (firstNode != null)
      {// nonempty chain
         // do first element
         s.append(firstNode.element.toString());
         // do remaining elements
         SortedChainNode currentNode = firstNode.next;
         while (currentNode != null)
         {
            s.append(", " + currentNode.element.toString());
            currentNode = currentNode.next;
         }
      }
      s.append("]");

      // create equivalent String
      return new String(s);
   }

   /** create and return an iterator */
   public Iterator iterator()
      {return new SortedChainIterator();}

   /** sorted chain iterator */
   private class SortedChainIterator implements Iterator
   {
      // data member
      private SortedChainNode nextNode;
   
      // constructor
      public SortedChainIterator()
         {nextNode = firstNode;}
   
      // methods
      /** @return true iff list has more elements */
      public boolean hasNext()
         {return nextNode != null;}
   
      /** @return next element in list
        * @throws NoSuchElementException
        * if there is no next element */
      public Object next()
      {
         if (nextNode != null)
         {
            Object obj = nextNode.element;
            nextNode = nextNode.next;
            return obj;
         }
         else throw new NoSuchElementException
                        ("No next element");
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
      // test default constructor
      SortedChain x = new SortedChain();

      // test put
      x.put(new Integer(2), new Integer(12));
      System.out.println("The list is " + x);
      x.put(new Integer(6), new Integer(16));
      System.out.println("The list is " + x);
      x.put(new Integer(1), new Integer(11));
      System.out.println("The list is " + x);
      x.put(new Integer(4), new Integer(14));
      System.out.println("The list is " + x);
      x.put(new Integer(6), new Integer(26));
      System.out.println("The list is " + x);

      // test get
      System.out.println("element " + x.get(new Integer(2))
                         + " has key 2");
      System.out.println("element " + x.get(new Integer(1))
                         + " has key 1");
      System.out.println("element " + x.get(new Integer(6))
                         + " has key 6");

      // test remove
      System.out.println("removed element " + x.remove(new Integer(2))
                         + " with key 2");
      System.out.println("The list is " + x);
      System.out.println("removed element " + x.remove(new Integer(1))
                         + " with key 1");
      System.out.println("The list is " + x);
      System.out.println("removed element " + x.remove(new Integer(6))
                         + " with key 6");
      System.out.println("The list is " + x);
   }
}

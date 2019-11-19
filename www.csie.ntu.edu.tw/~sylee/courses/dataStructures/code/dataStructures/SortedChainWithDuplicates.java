

package dataStructures;

import java.util.*;

public class SortedChainWithDuplicates extends SortedChain
{
   /** insert an element with the specified key
     * overrides insert method of SortedChain which
     * does not permit duplicate keys */
   public Object put(Object theKey, Object theElement)
   {
      SortedChainNode p = firstNode,
                      tp = null; // tp trails p
   
      // move tp so that theElement can be inserted after tp
      while (p != null && ((Comparable) p.key).compareTo(theKey) < 0)
      {
         tp = p;
         p = p.next;
      }
   
      // set up node for theElement
      SortedChainNode q = new SortedChainNode(theKey, theElement, p);
   
      // insert node just after tp
      if (tp == null) firstNode = q;
      else tp.next = q;

      size++;
      return null;
   }

   /** test program */
   public static void main(String [] args)
   {
      // test default constructor
      SortedChainWithDuplicates x = new SortedChainWithDuplicates();

      // test put
      x.put(new Integer(2), new Integer(12));
      System.out.println("The list is " + x);
      x.put(new Integer(6), new Integer(16));
      System.out.println("The list is " + x);
      x.put(new Integer(2), new Integer(11));
      System.out.println("The list is " + x);
      x.put(new Integer(6), new Integer(14));

      System.out.println("The list is " + x);

      // test get
      System.out.println("element " + x.get(new Integer(2))
                         + " has key 2");
      System.out.println("element " + x.get(new Integer(6))
                         + " has key 6");

      // test remove
      System.out.println("removed element " + x.remove(new Integer(2))
                         + " with key 2");
      System.out.println("The list is " + x);
      System.out.println("The list size is " + x.size());
      System.out.println("removed element " + x.remove(new Integer(6))
                         + " with key 6");
      System.out.println("removed element " + x.remove(new Integer(2))
                         + " with key 2");
      System.out.println("The list size is " + x.size());
      System.out.println("The list is " + x);
      System.out.println("removed element " + x.remove(new Integer(6))
                         + " with key 6");
      System.out.println("The list is " + x);
      System.out.println("The list size is " + x.size());
   }
}

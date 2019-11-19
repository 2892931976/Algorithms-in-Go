

package dataStructures;

import java.util.*;

public class SkipList implements Dictionary
{
   // top-level nested class
   protected static class SkipNode
   {
      // data members
      protected Comparable key;
      protected Object element;
      protected SkipNode [] next;

      // constructor
      protected SkipNode(Object theKey, Object theElement, int size)
      {
         key = (Comparable) theKey;
         element = theElement;
         next = new SkipNode [size];
      }
   }

   // data members of SkipList
   protected float prob;         // probability used to decide level number
   protected int maxLevel;       // max permissible chain level
   protected int levels;         // max current nonempty chain
   protected int size;           // current number of elements
   protected Comparable tailKey; // a large key
   protected SkipNode headNode;  // head node
   protected SkipNode tailNode;  // tail node
   protected SkipNode [] last;   // last node seen on each level
   protected Random r;           // needed for random numbers

   // constructor
   /** create an empty skip list
     * @param largekey used as key in tail node
     * all elements must have a smaller key than this
     * @param maxElements largest number of elements
     * to be stored in the dictionary
     * @param theProb probability that element on one
     * level is also on the next level */
   public SkipList(Comparable largeKey, int maxElements, float theProb)
   {
      prob = theProb;
      maxLevel = (int) Math.round(Math.log(maxElements) / 
                                  Math.log(1/prob)) - 1;
      // size and levels have default initial value 0
      tailKey = largeKey;
   
      // create head & tail nodes and last array
      headNode = new SkipNode (null, null, maxLevel + 1);
      tailNode = new SkipNode (tailKey, null, 0);
      last = new SkipNode [maxLevel + 1];
   
      // headNode points to tailNode at all levels initially
      for (int i = 0; i <= maxLevel; i++)
          headNode.next[i] = tailNode;

      r = new Random();  // initialize random number generator
   }

   // methods
   /** @return true iff the skip list is empty */
   public boolean isEmpty()
       {return size == 0;}

   /** @return current number of elements in the skip list */
   public int size()
      {return size;}

   /** @return element with specified key
     * @return null if there is no matching element */
   public Object get(Object theKey)
   {
      if (tailKey.compareTo(theKey) <= 0)
         return null;  // no matching element possible
   
      // position p just before possible node with theKey
      SkipNode p = headNode;
      for (int i = levels; i >= 0; i--)              // go down levels
         while (p.next[i].key.compareTo(theKey) < 0) // follow level i
            p = p.next[i];                           // pointers
   
      // check if next node has theKey
      if (p.next[0].key.equals(theKey))
         return p.next[0].element; 
      return null;  // no matching element
   }

   /** search for theKey saving last nodes seen at each
     * level in the array last
     * @return node that might contain theKey */
   SkipNode search(Object theKey)
   {
      // position p just before possible node with theKey
      SkipNode p = headNode;
      for (int i = levels; i >= 0; i--)
      {
         while (p.next[i].key.compareTo(theKey) < 0)
            p = p.next[i];
         last[i] = p;  // last level i node seen
      }
      return (p.next[0]);
   }

   /** @return a random level number <= maxLevel */
   int level()
   {
      int lev = 0;
      while (r.nextFloat() <= prob)
         lev++;
      return (lev <= maxLevel) ? lev : maxLevel;
   }
   
   /** insert an element with the specified key
     * overwrite old element if there is already an
     * element with the given key 
     * @return old element (if any) with key theKey
     * @throws IllegalArgumentException when
     * theKey >= largeKey = tailKey */
   public Object put(Object theKey, Object theElement)
   {
      if (tailKey.compareTo(theKey) <= 0) // key too large
         throw new IllegalArgumentException("key is too large");
      
      // see if element with theKey already present
      SkipNode p = search(theKey);
      if (p.key.equals(theKey))
      {// update p.element
         Object elementToReturn = p.element;
         p.element = theElement;
         return elementToReturn;
      }
   
      // not present, determine level for new node
      int lev = level(); // level of new node
      // fix lev to be <= levels + 1
      if (lev > levels)
      {
         lev = ++levels;
         last[lev] = headNode;
      }
   
      // get and insert new node just after p
      SkipNode y = new SkipNode (theKey, theElement, lev + 1);
      for (int i = 0; i <= lev; i++)
      {// insert into level i chain
         y.next[i] = last[i].next[i];
         last[i].next[i] = y;
      }
      size++;
      return null;
   }

   /** @return matching element and remove it
     * @return null if no matching element */
   public Object remove(Object theKey)
   {
      if (tailKey.compareTo(theKey) <= 0) // too large
         return null;
   
      // see if matching element present
      SkipNode p = search(theKey);
      if (!p.key.equals(theKey)) // not present
         return null;
   
      // delete node from skip list
      for (int i = 0; i <= levels &&
                      last[i].next[i] == p; i++)
         last[i].next[i] = p.next[i];
      
      // update Levels
      while (levels > 0 && headNode.next[levels] == tailNode)
         levels--;
      
      size--;
      return p.element;
   }
   
   /** convert to a string */
   public String toString()
   {
      StringBuffer s = new StringBuffer("["); 

      // follow level 0 chain
      SkipNode currentNode = headNode.next[0];
      if (currentNode != tailNode)
      {// nonempty list
         // do first element
         s.append(currentNode.element.toString());
         // do remaining elements
         currentNode = currentNode.next[0];
         while(currentNode != tailNode)
         {
            s.append(", " + currentNode.element.toString());
            currentNode = currentNode.next[0];
         }
      }
      s.append("]");

      // create equivalent String
      return new String(s);
   }

   /** create and return an iterator */
   public Iterator iterator()
      {return new SkipListIterator();}

   /** sorted chain iterator */
   private class SkipListIterator implements Iterator
   {
      // data member
      private SkipNode nextNode;
   
      // constructor
      public SkipListIterator()
         {nextNode = headNode.next[0];}
   
      // methods
      /** @return true iff list has more elements */
      public boolean hasNext()
         {return nextNode != tailNode;}
   
      /** @return next element in list
        * @throws NoSuchElementException
        * when if there is no next element */
      public Object next()
      {
         if (nextNode != tailNode)
         {
            Object obj = nextNode.element;
            nextNode = nextNode.next[0];
            return obj;
         }
         else throw new NoSuchElementException("No next element");
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
      // test constructor
      SkipList x = new SkipList(new Integer(1001), 100, (float) 0.5);

      // test put
      int n = 20;
      for (int i = 1; i <= n; i++)
         x.put(new Integer(2 * i), new Integer(i));
      System.out.println("The list is");
      System.out.println(x);

      for (int i = 1; i <= n + 1; i++)
         x.put(new Integer(2 * i - 1), new Integer(n + i));
      System.out.println("The list is\n" + x);

      // test get
      System.out.println("element " + x.get(new Integer(1))
                         + " has key 1");
      System.out.println("element " + x.get(new Integer(2))
                         + " has key 2");
      System.out.println("element " + x.get(new Integer(6))
                         + " has key 6");

      // test remove
      for (int i = 1; i <= n + 1; i++)
         System.out.println("removed " + x.remove(new Integer(2 * i - 1)));
      System.out.println("The list is\n" + x);
   }
}

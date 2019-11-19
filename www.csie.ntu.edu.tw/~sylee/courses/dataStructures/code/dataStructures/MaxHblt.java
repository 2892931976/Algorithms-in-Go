   
/** height biased leftist trees */

package dataStructures;

public class MaxHblt implements MaxPriorityQueue
{
   // top-level nested class 
   static class HbltNode
   {
      // data members
      Comparable element;
      int s;                 // shortest value
      HbltNode leftChild;    // pointer to left subtree
      HbltNode rightChild;   // pointer to right subtree

      // constructor
      private HbltNode(Comparable theElement, int theS)
      {
         element = theElement;
         s = theS;
      }
   }

   // data members of MaxHblt
   HbltNode root;   // pointer to tree root
   int size;        // number of elements in tree

   // only default constructor available

   // methods
   /** @return true iff the tree is empty */
   public boolean isEmpty()
       {return size == 0;}

   /** @return number of elements in the tree */
   public int size()
       {return size;}

   /** @return maximum element
     * @return null if the tree is empty */
   public Comparable getMax()
   {
      if (size == 0) return null;
      else return root.element;
   }

   /** meld the max leftist trees this and x
     * on exit, this is the result */
   public void meld(MaxHblt x)
   {
      root = meld(root, x.root);
      size += x.size;
   }
   
   /** meld the leftist trees with roots x and y
     * @return pointer to root of resulting tree */
   private static HbltNode meld(HbltNode x, HbltNode y)
   {
      if (y == null)
         return x;   // y is empty
      if (x == null)
         return y;   // x is empty
   
      // neither is empty, swap x and y if necessary
      if (x.element.compareTo(y.element) < 0)
      {// swap x and y
         HbltNode t = x;
         x = y;
         y = t;
      }
      // now x.element >= y.element

      x.rightChild = meld(x.rightChild, y);

      // swap subtrees of x if necessary and set x.s
      if (x.leftChild == null)
      {// left subtree is empty, swap the subtrees
          x.leftChild = x.rightChild;
          x.rightChild = null;
          x.s = 1;
       }
      else
      {  // swap only if left subtree has a smaller s value
         if (x.leftChild.s < x.rightChild.s)
         {// swap subtrees
             HbltNode t = x.leftChild;
             x.leftChild = x.rightChild;  
             x.rightChild = t;
         }
         // update s value
         x.s = x.rightChild.s + 1;
      }
      return x;
   }
   

   /** put theElement into the leftist tree */
   public void put(Comparable theElement)
   {
      HbltNode q = new HbltNode (theElement, 1);
      // meld q and original tree
      root = meld(root, q);
      size++;
   }
   
   /** remove max element and return it */
   public Comparable removeMax()
   {
      if (size == 0) return null;   // tree is empty
   
      // tree not empty
      Comparable x = root.element;  // save max element
      root = meld(root.leftChild, root.rightChild);
      size--;
      return x;
   }
   
   /** initialize leftist tree to elements in array theElements */
   public void initialize(Comparable [] theElements, int theSize)
   {
      size = theSize;
      ArrayQueue q = new ArrayQueue(size);
      // initialize queue of trees
      for (int i = 1; i <= size; i++)
         // create trees with one node each
         q.put(new HbltNode(theElements[i], 1));
   
      // repeatedly meld from queue q
      for (int i = 1; i <= size - 1; i++)
      {  // remove and meld two trees from the queue
         HbltNode b = (HbltNode) q.remove();
         HbltNode c = (HbltNode) q.remove();
         b = meld(b, c);
         // put melded tree on queue
         q.put(b);
      }
   
      if (size > 0)
         root = (HbltNode) q.remove();
   }
   
   /** postorder listing */
   private static StringBuffer s;
   public String toString()
   {
      s = new StringBuffer(); 
      s.append("The " + size + " elements, in postorder, are [");
      if (root == null)
         s.append("]");
      else
      {
         postOrderString(root);
         s.setCharAt(s.length() - 2, ']');  // replace last comma
      }
      return new String(s);
   }

   /** actual postorder string generator */
   private static void postOrderString(HbltNode t)
   {
      if (t != null)
      {
         postOrderString(t.leftChild);
         postOrderString(t.rightChild);
         s.append(t.element + ", ");
      }
  }

   /** test program */
   public static void main(String [] args)
   {
      // test constructor and put
      MaxHblt h = new MaxHblt();
      h.put(new Integer(10));
      h.put(new Integer(20));
      h.put(new Integer(5));

      // test toString
      System.out.println("Elements in postorder are");
      System.out.println(h);
      System.out.println();

      h.put(new Integer(15));
      h.put(new Integer(30));

      System.out.println(h);
      System.out.println();

      // test remove max
      System.out.println("The max element is " + h.getMax());
      System.out.println("Deleted max element " + h.removeMax());
      System.out.println("Deleted max element " + h.removeMax());
      System.out.println(h);
      System.out.println();

      // test initialize
      Comparable [] z = new Comparable [10];
      for (int i = 1; i < 10; i++)
         z[i] = new Integer(i);
      h.initialize(z, 9);
      System.out.println(h);
   }
}

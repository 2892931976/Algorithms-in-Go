
/** binary search with visit */

package dataStructures;

import java.lang.reflect.*;

public class BinarySearchTreeWithVisit extends BinarySearchTree
{
   /** insert an element with the specified key
     * if there is already an element with this key,
     * simply visit the element
     * @return visited element (if any) */
   public Object put(Object theKey, Object theElement, Method theVisit)
   {
      BinaryTreeNode p = root,     // search pointer
                     pp = null;    // parent of p
      Comparable elementKey = (Comparable) theKey;
      // find place to insert theElement
      while (p != null)
      {// examine p.element.key
         pp = p;
         // move p to a child
         if (elementKey.compareTo(((Data) p.element).key) < 0)
            p = p.leftChild;
         else if (elementKey.compareTo(((Data) p.element).key) > 0)
                 p = p.rightChild;
              else
              {// visit element with same key
                 Object [] visitArgs = new Object [1];
                 visitArgs[0] = ((Data) p.element).element;
                 try {theVisit.invoke(null, visitArgs);}
                 catch (Exception e)
                    {System.out.println(e);}
                 return visitArgs[0];
              }
      }
   
      // get a node for theElement and attach to pp
      BinaryTreeNode r = new BinaryTreeNode
                             (new Data(elementKey, theElement));
      if (root != null)
         // the tree is not empty
         if (elementKey.compareTo(((Data) pp.element).key) < 0)
            pp.leftChild = r;
         else
            pp.rightChild = r;
      else // insertion into empty tree
         root = r;
      return null;
   }
}

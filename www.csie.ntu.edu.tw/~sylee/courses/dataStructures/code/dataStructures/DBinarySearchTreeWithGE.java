
package dataStructures;

public class DBinarySearchTreeWithGE extends DBinarySearchTree
{
   /** @return element with smallest key >= theKey
     * @return null if no element has key >= theKey */
   public Object getGreaterThanOrEqual(Object theKey)
   {
      BinaryTreeNode currentNode = root;
      Object bestElement = null; // element with smallest key
                                 // >= theKey found so far
      // search the tree
      while (currentNode != null)
         // is currentNode.element.key a candidate
         if (((Data) currentNode.element).key.compareTo(theKey) >= 0)
         {// yes, currentNode.element.element is
          // a better candidate than bestElement
            bestElement = ((Data) currentNode.element).element;
            // smaller keys in left subtree only
            currentNode = currentNode.leftChild;
         }
         else
            // no, currentNode.element.key is too small
            // try right subtree
            currentNode = currentNode.rightChild;

      return bestElement;
   }
}

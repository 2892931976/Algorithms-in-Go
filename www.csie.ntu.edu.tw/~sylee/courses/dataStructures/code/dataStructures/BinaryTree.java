/** binary tree interface */

package dataStructures;

import java.lang.reflect.*;

public interface BinaryTree
{
   public boolean isEmpty();
   public Object root();
   public void makeTree(Object root, Object left, Object right);
   public BinaryTree removeLeftSubtree();
   public BinaryTree removeRightSubtree();
   public void preOrder(Method visit);
   public void inOrder(Method visit);
   public void postOrder(Method visit);
   public void levelOrder(Method visit);
}

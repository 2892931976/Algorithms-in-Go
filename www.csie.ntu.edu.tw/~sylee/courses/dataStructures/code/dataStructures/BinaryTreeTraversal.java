
/** binary tree traversal methods */

package dataStructures;

public class BinaryTreeTraversal
{
   /** visit method that prints the element in the node */ 
   public static void visit(BinaryTreeNode t)
      {System.out.print(t.element + " ");}

   /** preorder traversal */
   public static void preOrder(BinaryTreeNode t)
   {
      if (t != null)
      {
         visit(t);                // visit tree root
         preOrder(t.leftChild);   // do left subtree
         preOrder(t.rightChild);  // do right subtree
      }
   }

   /** inorder traversal */
   public static void inOrder(BinaryTreeNode t)
   {
      if (t != null)
      {
         inOrder(t.leftChild);   // do left subtree
         visit(t);               // visit tree root
         inOrder(t.rightChild);  // do right subtree
      }
   }

   /** postorder traversal */
   public static void postOrder(BinaryTreeNode t)
   {
      if (t != null)
      {
         postOrder(t.leftChild);   // do left subtree
         postOrder(t.rightChild);  // do right subtree
         visit(t);                 // visit tree root
      }
   }

   /** level order traversal */
   public static void levelOrder(BinaryTreeNode t)
   {
      ArrayQueue q = new ArrayQueue();
      while (t != null) 
      {
         visit(t);  // visit t
   
         // put t's children on queue
         if (t.leftChild != null) 
            q.put(t.leftChild);
         if (t.rightChild != null)
            q.put(t.rightChild);
   
         // get next node to visit
         t = (BinaryTreeNode) q.remove();
      }
    }

   /** test program */
   public static void main(String [] args)
   {
      BinaryTreeNode y = new BinaryTreeNode(new Integer(2));
      BinaryTreeNode z = new BinaryTreeNode(new Integer(3));
      BinaryTreeNode x = new BinaryTreeNode(new Integer(1), y, z);

      System.out.println("The elements in preorder are");
      preOrder(x);
      System.out.println();

      System.out.println("The elements in inorder are");
      inOrder(x);
      System.out.println();

      System.out.println("The elements in postorder are");
      postOrder(x);
      System.out.println();

      System.out.println("The elements in level order are");
      levelOrder(x);
   }
}

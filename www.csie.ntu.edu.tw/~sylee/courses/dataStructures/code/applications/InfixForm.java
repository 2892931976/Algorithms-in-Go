
/** fully parenthesized infix form */

package applications;

import dataStructures.*;

public class InfixForm
{
   public static void infix(BinaryTreeNode t)
   {
      if (t != null) 
      {
         System.out.print("(");     
         infix(t.getLeftChild());   // output left operand
         System.out.print(t);       // output operator
         infix(t.getRightChild());  // output right operand
         System.out.print(")");
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      BinaryTreeNode y = new BinaryTreeNode(new Integer(2));
      BinaryTreeNode z = new BinaryTreeNode(new Integer(3));
      BinaryTreeNode x = new BinaryTreeNode(new Integer(1), y, z);

      System.out.println("The fully parenthesized infix form is");
      infix(x);
   }
}

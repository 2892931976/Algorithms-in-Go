
/** place signal boosters in a binary distribution tree */

package applications;

import java.lang.reflect.*;
import dataStructures.*;

public class PlaceBoosters
{
   private static class Booster
   {
      // data members
      int degradeToLeaf,       // degradation to leaf
          degradeFromParent;   // degradation from parent
      boolean boosterHere;     // true iff booster placed here

      // methods
      public Booster(int fromParent)
         {degradeFromParent = fromParent;}

      public String toString()
         {return boosterHere + " " + degradeToLeaf + " " 
                 + degradeFromParent;}
   }
   
   // class data member of PlaceBoosters
   static int tolerance;
   
   /** visit method to place boosters */
   public static void placeBoosters(BinaryTreeNode x)
   {// Compute degradation at x.  Place booster
    // here if degradation exceeds tolerance.

      // initialize degradation at x
      Booster elementX = (Booster) x.getElement();
      elementX.degradeToLeaf = 0;

      // compute degradation from left subtree of x and
      // place a booster at the left child of x if needed
      BinaryTreeNode y = x.getLeftChild();
      if (y != null)
      {// x has a nonempty left subtree
         Booster elementY = (Booster) y.getElement();
         int degradation = elementY.degradeToLeaf +
                           elementY.degradeFromParent;
         if (degradation > tolerance)
         {// place booster at y
            elementY.boosterHere = true;
            elementX.degradeToLeaf = elementY.degradeFromParent;
         }
         else // no booster needed at y
            elementX.degradeToLeaf = degradation;
      }

      // compute degradation from right subtree of x and
      // place a booster at the right child of x if needed
      y = x.getRightChild();
      if (y != null) 
      {// x has a nonempty right subtree
         Booster elementY = (Booster) y.getElement();
         int degradation = elementY.degradeToLeaf +
                           elementY.degradeFromParent;
         if (degradation > tolerance)
         {// place booster at y
            elementY.boosterHere = true;
            degradation = elementY.degradeFromParent;
         }
         elementX.degradeToLeaf = Math.max(elementX.degradeToLeaf,
                                           degradation);
      }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // create distribution tree
      LinkedBinaryTree u = new LinkedBinaryTree();
      LinkedBinaryTree x = new LinkedBinaryTree();
      u.makeTree(new Booster(2), x, x);
      LinkedBinaryTree v = new LinkedBinaryTree();
      v.makeTree(new Booster(1), u, x);
      u.makeTree(new Booster(2), x, x);
      LinkedBinaryTree w = new LinkedBinaryTree();
      w.makeTree(new Booster(2), u, x);
      u.makeTree(new Booster(3), v, w);
      v.makeTree(new Booster(2), x, x);
      w.makeTree(new Booster(3), x, x);
      LinkedBinaryTree y = new LinkedBinaryTree();
      y.makeTree(new Booster(2), v, w);
      w.makeTree(new Booster(2), x, x);
      LinkedBinaryTree t = new LinkedBinaryTree();
      t.makeTree(new Booster(3), y, w);
      v.makeTree(new Booster(0), t, u);

      tolerance = 3;
      Class [] paramType = {BinaryTreeNode.class};
                                      // type of parameter for visit
      Method thePlaceMethod = null;   // method to place boosters

      try
      {
         Class pb = PlaceBoosters.class;
         thePlaceMethod = pb.getMethod("placeBoosters", paramType);
      }
      catch (Exception e) {}
         // exception not possible

      v.postOrder(thePlaceMethod);
      v.postOrderOutput();
   }
}

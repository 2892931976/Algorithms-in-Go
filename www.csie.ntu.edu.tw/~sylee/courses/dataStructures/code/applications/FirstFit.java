
/** first fit bin packing */

package applications;

import dataStructures.*;
import utilities.*;

public class FirstFit
{
   // top-level nested class
   public static class Bin implements Playable
   {
      // data members
      int unusedCapacity;
   
      // constructor
      public Bin(int theUnusedCapacity)
         {unusedCapacity = theUnusedCapacity;}
   
      // method of Playable
      public boolean winnerOf(Playable theBin)
      {// define for a max winner tree
         if (unusedCapacity >= ((Bin) theBin).unusedCapacity)
            return true;
         else
            return false;
      }
   }
   
   /** output first fit packing into bins of size binCapacity
     * @param objectSize[1:objectSize.length-1] are the object sizes */
   public static void firstFitPack(int [] objectSize, int binCapacity)
   {
      int n = objectSize.length - 1;         // number of objects
      Bin [] bin = new Bin [n + 1];          // bins
      ExtendedCWTree winTree = new ExtendedCWTree();
      
      // initialize n bins and winner tree
      for (int i = 1; i <= n; i++)
         bin[i] = new Bin(binCapacity);  // initial unused capacity
      winTree.initialize(bin);
      
      // put objects in bins
      for (int i = 1; i <= n; i++)
      {// put object i into a bin
         // find first bin with enough capacity
         int child = 2;  // start search at left child of root
         while (child < n)
         {
            int winner = winTree.getWinner(child);
            if (bin[winner].unusedCapacity < objectSize[i])
               child++ ;  // first bin is in right subtree
            child *= 2;   // move to left child
         }
   
         int binToUse;          // will be set to bin to use
         child /= 2;            // undo last left-child move
         if (child < n)
         {// at a tree node
            binToUse = winTree.getWinner(child);
            // if binToUse is right child, need to check
            // bin binToUse-1.  No harm done by checking
            // bin binToUse-1 even if binToUse is left child.
            if (binToUse > 1 &&
                bin[binToUse - 1].unusedCapacity >= objectSize[i])
               binToUse--;
          }
         else  // arises when n is odd
            binToUse = winTree.getWinner(child / 2);
   
         System.out.println("Pack object " + i + " in bin " + binToUse);
         bin[binToUse].unusedCapacity -= objectSize[i];
         winTree.rePlay(binToUse);
      }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of objects and bin capacity");
      int n = keyboard.readInteger();   // number of objects
      int binCapacity = keyboard.readInteger();
      if (n < 2)
      {
         System.out.println("Too few objects");
         System.exit(1);
      }

      // input the object sizes objectSize[1:n]
      int [] objectSize = new int [n + 1];
      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter space requirement of object " + i);
         objectSize[i] = keyboard.readInteger();
         if (objectSize[i] > binCapacity)
         {
           System.out.println("Object too large to fit in a bin");
           System.exit(1);
         }
      }

      // output the packing
      firstFitPack(objectSize, binCapacity);
   }
}

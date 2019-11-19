
/** best fit bin packing */

package applications;

import dataStructures.*;
import utilities.*;

public class BestFit
{
   // top-level nested class
   public static class BinNode
   {
      // data members
      int id,             // bin identifier
          unusedCapacity;

      // constructor
      public BinNode(int theId, int theCapacity)
      {
         id = theId;
         unusedCapacity = theCapacity;
      }
   }
   
   /** output best-fit packing into bins of size binCapacity
     * @param objectSize[1:objectSize.length-1] are the object sizes */
   public static void bestFitPack(int [] objectSize, int binCapacity)
   {
      int n = objectSize.length - 1;    // number of objects
      int binsUsed = 0;
      DBinarySearchTreeWithGE theTree;  // tree of bin capacities
      theTree = new DBinarySearchTreeWithGE();
      
      // pack objects one by one
      for (int i = 1; i <= n; i++)
      {// pack object i
         // find best bin
         BinNode bestBin = (BinNode) theTree.getGreaterThanOrEqual
                               (new Integer(objectSize[i]));
         if (bestBin == null)
            // no bin large enough, start a new bin
            bestBin = new BinNode(++binsUsed, binCapacity);
         else
            // remove best bin from theTree
            bestBin = (BinNode) theTree.remove
                      (new Integer(bestBin.unusedCapacity));
      
         System.out.println("Pack object " + i + " in bin " + bestBin.id);
   
         // update unused capacity and put bin
         // in tree unless unused capacity is 0
         bestBin.unusedCapacity -= objectSize[i];
         if (bestBin.unusedCapacity > 0)
            theTree.put(new Integer(bestBin.unusedCapacity), bestBin);
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
      bestFitPack(objectSize, binCapacity);
   }
}

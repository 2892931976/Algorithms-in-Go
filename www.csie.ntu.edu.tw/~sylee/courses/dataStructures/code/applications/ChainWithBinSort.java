
/** bin sort as a member of a subclass of Chain */

package applications;

import utilities.*;
import dataStructures.Chain;
import wrappers.*;

public class ChainWithBinSort extends Chain
{
   /** sort the chain c using the bin sort method */
   public static void binSort(int range)
   {// Sort by score.
      // create the bins
      ChainNode [] bottom = new ChainNode[range + 1];
      ChainNode [] top = new ChainNode[range + 1];
      // default initial values of bottom[] and top[] are null
      
      // distribute to bins
      int b;  // bin index
      for (; firstNode != null; firstNode = firstNode.next)
      {// add the element to a bin
         b = ((ScoreObject) firstNode.element).score;
         if (bottom[b] == null)  // bin is empty
            bottom[b] = top[b] = first;
         else
         {// bin b is not empty
            top[b].next = firstNode;
            top[b] = firstNode;
         }
      }
      
      // collect from bins into sorted chain
      ChainNode y;
      for (b = 0; b <= range; b++)
         if (bottom[b] != null)
         {// bin not empty
           if (y == null) // first nonempty bin
             first = bottom[b];
           else // not first nonempty bin
             y.next = bottom[b];
           y = top[b];
        }
      if (y != null) y.next = null;
   }


   /** test program */
   public static void main(String [] args)
   {
      StudentRecord s;
      Chain c = new Chain();
      for (int i = 1; i <= 20; i++) {
         s = new StudentRecord("", i/2);
         c.insertElementAt(s,0);
         }

      // output elements to be sorted
      System.out.println("The initial scores are");
      System.out.println(c);

      // sort by score
      c.binSort(10);

      // output in sorted order
      System.out.println("The sorted scores are");
      System.out.println(c);

   }
}

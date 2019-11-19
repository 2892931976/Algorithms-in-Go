

/** Chain extended to include the a method that
  * sorts using the bin sort technique.  */

package dataStructures;

public class ChainWithBinSort extends Chain
{
   /** sort the chain using the bin sort method */
   public void binSort(int range)
   {// Sort by score.
      // create the bins
      ChainNode [] bottom = new ChainNode[range + 1];
      ChainNode [] top = new ChainNode[range + 1];
      // default initial values of bottom[] and top[] are null
      
      // distribute to bins
      for (; firstNode != null; firstNode = firstNode.next)
      {// add the element to a bin
         int theBin = ((ScoreObject) firstNode.element).score;
         if (bottom[theBin] == null)  // bin is empty
            bottom[theBin] = top[theBin] = firstNode;
         else
         {// theBin is not empty
            top[theBin].next = firstNode;
            top[theBin] = firstNode;
         }
      }
      
      // collect from bins into sorted chain
      ChainNode y = null;
      for (int theBin = 0; theBin <= range; theBin++)
         if (bottom[theBin] != null)
         {// bin not empty
           if (y == null) // first nonempty bin
             firstNode = bottom[theBin];
           else // not first nonempty bin
             y.next = bottom[theBin];
           y = top[theBin];
        }
      if (y != null)
         y.next = null;
   }


   /** test program */
   public static void main(String [] args)
   {
      StudentRecord s;
      ChainWithBinSort c = new ChainWithBinSort();
      for (int i = 1; i <= 20; i++) {
         s = new StudentRecord("", i/2);
         c.add(0, s);
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

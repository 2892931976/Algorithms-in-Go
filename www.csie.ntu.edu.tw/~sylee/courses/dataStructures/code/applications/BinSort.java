
/** bin sort using Chain methods */

package applications;

import dataStructures.Chain;

public class BinSort
{
   /** sort theChain using the bin sort method */
   public static void binSort(Chain theChain, int range)
   {// Sort by score.
      // initialize the bins
      Chain[] bin = new Chain[range + 1];
      for (int i = 0; i <= range; i++)
         bin[i] = new Chain();

      // distribute elements from theChain to bins
      int numberOfElements = theChain.size();
      for (int i = 1; i <= numberOfElements; i++)
      {
         ScoreObject theElement = (ScoreObject) theChain.remove(0);
         bin[theElement.score].add(0, theElement);
      }
   
      // collect elements from bins
      for (int j = range; j >= 0; j--)
         while (!bin[j].isEmpty())
         {
            ScoreObject theElement = (ScoreObject) bin[j].remove(0);
            theChain.add(0, theElement);
         }
   }


   /** test program */
   public static void main(String [] args)
   {
      StudentRecord s;
      Chain c = new Chain();
      for (int i = 1; i <= 20; i++)
      {
         s = new StudentRecord("", i/2);
         c.add(0, s);
      }

      // output elements to be sorted
      System.out.println("The initial scores are");
      System.out.println(c);

      // sort by score
      binSort(c,10);

      // output in sorted order
      System.out.println("The sorted scores are");
      System.out.println(c);

   }
}

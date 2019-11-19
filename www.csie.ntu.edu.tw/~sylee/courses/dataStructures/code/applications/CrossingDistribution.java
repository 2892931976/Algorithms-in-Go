
/** crossing distribution using a Vector */

package applications;

import dataStructures.*;

public class CrossingDistribution
{
   
   public static void main(String [] args)
   {
      // define the instance to be solved
      // connections at bottom of channel, theC[1:10]
      int [] theC = {0, 8, 7, 4, 2, 5, 1, 9, 3, 10, 6};
      // crossing numbers, k[1:10]
      int [] k = {0, 7, 6, 3, 1, 2, 0, 2, 0, 1, 0};
      int n = 10;        // number of pins on either side of channel
      int theK = 22;     // total number of crossings

      // create data structures
      ArrayLinearList list = new ArrayLinearList(n);
      int [] theA = new int [n + 1];   // top-half permutation
      int [] theB = new int [n + 1];   // bottom-half permutation
      int [] theX = new int [n + 1];   // center connections
      
      int crossingsNeeded = theK / 2;  // remaining number of crossings
                                       // needed in top half
   
      // scan wires right to left
      int currentWire = n;
      while (crossingsNeeded > 0)
      {// need more crossings in top half
         if (k[currentWire] < crossingsNeeded)
         {// use all crossings from currentWire
            list.add(k[currentWire], new Integer(currentWire));
            crossingsNeeded -= k[currentWire];
         }
         else
         {// use only crossingsNeeded crossings from currentWire
            list.add(crossingsNeeded, new Integer(currentWire));
            crossingsNeeded = 0;
         }
         currentWire--;
      }
   
      // determine wire permutation at center
      // first currentWire wires have same ordering
      for (int i = 1; i <= currentWire; i++)
         theX[i] = i;
   
      // ordering of remaining wires is from list
      for (int i = currentWire + 1; i <= n; i++)
         theX[i] = ((Integer) list.get(i - currentWire - 1)).intValue();
    
      // compute top-half permutation
      for (int i = 1; i <= n; i++)
         theA[theX[i]] = i;
   
      // compute bottom-half permutation
      for (int i = 1; i <= n; i++)
         theB[i] = theC[theX[i]];
      
      System.out.print("A is ");
      for (int i = 1; i <= n; i++)
         System.out.print(theA[i] + " ");
      System.out.println();
      
      System.out.print("B is ");
      for (int i = 1; i <= n; i++)
         System.out.print(theB[i] + " ");
      System.out.println();
   }
}

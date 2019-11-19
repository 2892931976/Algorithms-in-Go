/** railroad car rearrangement using stacks */

package applications;

import dataStructures.*;

public class RailroadWithStacks
{
   // data members
   private static ArrayStack [] track; // array of holding tracks
   private static int numberOfCars;
   private static int numberOfTracks;
   private static int smallestCar; // smallest car in any holding track
   private static int itsTrack;    // holding track with car smallestCar

   /** output the smallest car from the holding tracks */
   private static void outputFromHoldingTrack()
   {
      // remove smallestCar from itsTrack
      track[itsTrack].pop();
      System.out.println("Move car " + smallestCar + " from holding "
                         + "track " + itsTrack + " to output track");
   
      // find new smallestCar and itsTrack by checking top of all stacks
      smallestCar = numberOfCars + 2;
      for (int i = 1; i <= numberOfTracks; i++)
         if (!track[i].empty() &&
             ((Integer) track[i].peek()).intValue() < smallestCar)
         {
            smallestCar = ((Integer) track[i].peek()).intValue();
            itsTrack = i;
         }
   }
   
  /** put car c into a holding track
    * @return false iff there is no feasible holding track for this car */
   private static boolean putInHoldingTrack(int c)
   {
      // find best holding track for car c
      // initialize
      int bestTrack = 0,               // best track so far
          bestTop = numberOfCars + 1;  // top car in bestTrack
   
      // scan tracks
      for (int i = 1; i <= numberOfTracks; i++)
         if (!track[i].empty())
         {// track i not empty
             int topCar = ((Integer) track[i].peek()).intValue();
             if (c < topCar && topCar < bestTop)
             {
                // track i has smaller car at top
                bestTop = topCar;
                bestTrack = i;
             }
         }
         else // track i empty
            if (bestTrack == 0) bestTrack = i;
         
      if (bestTrack == 0) return false; // no feasible track
   
      // add c to bestTrack
      track[bestTrack].push(new Integer(c));
      System.out.println("Move car " + c + " from input track "
                         + "to holding track " + bestTrack);
   
      // update smallestCar and itsTrack if needed
      if (c < smallestCar)
      {
          smallestCar = c;
          itsTrack = bestTrack;
      }
   
      return true;
   }
   
  /** rearrange railroad cars beginning with the initial order
    * inputOrder[1:theNumberOfCars]
    * @return true if successful, false if impossible. */
   public static boolean railroad(int [] inputOrder,
                         int theNumberOfCars, int theNumberOfTracks)
   {
      numberOfCars = theNumberOfCars;
      numberOfTracks = theNumberOfTracks;

      // create stacks track[1:numberOfTracks] for use as holding tracks
      track = new ArrayStack [numberOfTracks + 1];
      for (int i = 1; i <= numberOfTracks; i++)
         track[i] = new ArrayStack();
   
      int nextCarToOutput = 1;
      smallestCar = numberOfCars + 1;  // no car in holding tracks
   
      // rearrange cars
      for (int i = 1; i <= numberOfCars; i++)
         if (inputOrder[i] == nextCarToOutput)
         {// send car inputOrder[i] straight out
             System.out.println("Move car " + inputOrder[i]
                                + " from input track to output track");
             nextCarToOutput++;
    
             // output from holding tracks
             while (smallestCar == nextCarToOutput)
             {
                outputFromHoldingTrack();
                nextCarToOutput++;
             }
         }
         else
         // put car inputOrder[i] in a holding track
            if (!putInHoldingTrack(inputOrder[i]))
               return false;

      return true;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // int [] p = {0, 5, 8, 1, 7, 4, 2, 9, 6, 3};
      int [] p = {0, 3, 6, 9, 2, 4, 7, 1, 8, 5};
      System.out.println("Input permutation is 369247185");
      railroad(p, 9, 3);
   }
}

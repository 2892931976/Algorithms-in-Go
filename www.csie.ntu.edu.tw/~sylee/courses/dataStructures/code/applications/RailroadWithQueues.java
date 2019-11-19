
/** railroad car rearrangement using queues */

package applications;

import dataStructures.*;

public class RailroadWithQueues
{
   // data members
   private static ArrayQueue [] track;    // array of holding tracks
   private static int numberOfCars;
   private static int numberOfTracks;
   private static int smallestCar;        // smallest car in any holding track
   private static int itsTrack;           // holding track with car smallestCar

   /** output the smallest car from the holding tracks */
   private static void outputFromHoldingTrack()
   {
      // remove smallestCar from itsTrack
      track[itsTrack].remove();
      System.out.println("Move car " + smallestCar + " from holding track "
                         + itsTrack + " to output track");
   
      // find new smallestCar and itsTrack by checking all queue fronts
      smallestCar = numberOfCars + 2;
      for (int i = 1; i <= numberOfTracks; i++)
         if (!track[i].isEmpty() &&
            ((Integer) track[i].getFrontElement()).intValue() < smallestCar)
         {
            smallestCar = ((Integer) track[i].getFrontElement())
                                             .intValue();
            itsTrack = i;
         }
   }
   

  /** put car c into a holding track
    * @return false iff there is no feasible holding track for this car */
   private static boolean putInHoldingTrack(int c)
   {
      // find best holding track for car c
      // initialize
      int bestTrack = 0,  // best track so far
          bestLast =  0;  // last car in bestTrack
   
      // scan tracks
      for (int i = 1; i <= numberOfTracks; i++)
         if (!track[i].isEmpty())
         {// track i not empty
             int lastCar = ((Integer) track[i].getRearElement())
                                              .intValue();
             if (c > lastCar && lastCar > bestLast)
             {
                // track i has bigger car at its rear
                bestLast = lastCar;
                bestTrack = i;
             }
         }
         else // track i empty
            if (bestTrack == 0) bestTrack = i;
         
      if (bestTrack == 0) return false; // no feasible track
   
      // add c to bestTrack
      track[bestTrack].put(new Integer(c));
      System.out.println("Move car " + c + " from input track "
                         + "to holding track " + bestTrack);
   
      // update smallestCar and itsTrack if needed
      if (c < smallestCar)
      {   smallestCar = c;
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
      // keep last track open for output
      numberOfTracks = theNumberOfTracks - 1;

      // create queues track[1:numberOfTracks] for use as holding tracks
      track = new ArrayQueue [numberOfTracks + 1];
      for (int i = 1; i <= numberOfTracks; i++)
         track[i] = new ArrayQueue();
   
      int nextCarToOutput = 1;
      smallestCar = numberOfCars + 1;  // no car in holding tracks
   
      // rearrange cars
      for (int i = 1; i <= numberOfCars; i++)
         if (inputOrder[i] == nextCarToOutput)
         {// send car inputOrder[i] straight out
             System.out.println("Move car " + inputOrder[i] + " from input "
                                + "track to output track");
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

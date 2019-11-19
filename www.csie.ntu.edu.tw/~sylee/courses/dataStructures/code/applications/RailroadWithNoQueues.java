

/** railroad car rearrangement using no explicit queues */

package applications;

public class RailroadWithNoQueues
{
   // data members
   private static int [] whichTrack;  // track that has the car
   private static int [] lastCar;     // last car in track
   private static int numberOfCars;
   private static int numberOfTracks;

   /** move car c from its holding track to the output track */
   private static void outputFromHoldingTrack(int c)
   {
      System.out.println("Move car " + c + " from holding track "
                         + whichTrack[c] + " to output track");
   
      // if c was the last car in its track, the track is now empty
      if (c == lastCar[whichTrack[c]])
         lastCar[whichTrack[c]] = 0;
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
         if (lastCar[i] != 0)
         {// track i not empty
             if (c > lastCar[i] && lastCar[i] > bestLast)
             {
                // track i has bigger car at its rear
                bestLast = lastCar[i];
                bestTrack = i;
             }
         }
         else // track i empty
            if (bestTrack == 0) bestTrack = i;
         
      if (bestTrack == 0) return false; // no feasible track
   
      // add c to bestTrack
      whichTrack[c] = bestTrack;
      lastCar[bestTrack] = c;
      System.out.println("Move car " + c + " from input track "
                         + "to holding track " + bestTrack);
   
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

      // create the arrays lastCar and whichTrack
      whichTrack = new int [numberOfCars + 1];
      lastCar = new int [numberOfTracks + 1];
      // default initial values in lastCar are 0
   
      int nextCarToOutput = 1;
   
      // rearrange cars
      for (int i = 1; i <= numberOfCars; i++)
         if (inputOrder[i] == nextCarToOutput)
         {// send car inputOrder[i] straight out
             System.out.println("Move car " + inputOrder[i] + " from input "
                                + "track to output track");
             nextCarToOutput++;
    
             // output from holding tracks
             while (nextCarToOutput <= numberOfCars &&
                    whichTrack[nextCarToOutput] != 0)
             {
                outputFromHoldingTrack(nextCarToOutput);
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

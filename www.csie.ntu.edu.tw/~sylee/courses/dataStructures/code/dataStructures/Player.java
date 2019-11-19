
/** class used by CompleteWinnerTree test program */

package dataStructures;

public class Player implements Playable
{
   // data members
   int id;     // id of player
   int value;  // player's value

   // constructor
   public Player(int theId, int theValue)
   {
      id = theId;
      value = theValue;
   }

   // method of Playable
   public boolean winnerOf(Playable x)
   {
      if (value <= ((Player) x).value)
         return true;
      else
         return false;
   }
}

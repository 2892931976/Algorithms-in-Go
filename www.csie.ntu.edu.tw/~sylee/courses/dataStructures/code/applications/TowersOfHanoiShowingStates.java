// Towers of Hanoi with capability to show tower states

package applications;

import dataStructures.*;

public class TowersOfHanoiShowingStates
{
   // data member
   private static ArrayStack [] tower;  // the towers are tower[1:3]

   /** n disk Towers of Hanoi problem */
   public static void towersOfHanoi(int n)
   {// Preprocessor for showTowerStates
      
      // create three stacks, tower[0] is not used
      tower = new ArrayStack[4];
      for (int i = 1; i <= 3; i++)
         tower[i] = new ArrayStack();

      for (int d = n; d > 0; d--) // initialize
         tower[1].push(new Integer(d));  // add disk d to tower 1
      
      // move n disks from tower 1 to 2 using 3 as
      // intermediate tower
      showTowerStates(n, 1, 2, 3);
   }

   /** show the states of the three towers as disks are moved
     * @param n number of disks
     * @param x source tower
     * @param y destination tower
     * @param z intermediate tower */
   public static void showTowerStates(int n, int x, int y, int z)
   {// Move the top n disks from tower x to tower y.
    // Use tower z for intermediate storage.
       if (n > 0)
       {
          showTowerStates(n-1, x, z, y);
          Integer d = (Integer) tower[x].pop();  // move d from top of tower x
          tower[y].push(d);            // to top of tower y
          System.out.println("Move disk " + d + " from tower "
                             + x + " to top of tower " + y);
          // output statement should be replaced by showState() when
          // showState method has been implemented
          showTowerStates(n-1, z, y, x);
       }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      System.out.println("Moves for a three disk problem are");
      // 3 disk Towers of Hanoi
      towersOfHanoi(3);
   }
}

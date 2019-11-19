// Towers of Hanoi

package applications;


public class TowersOfHanoi
{
   /** output a sequence of disk moves for the Towers of Hanoi problem
     * @param n number of disks
     * @param x source tower
     * @param y destination tower
     * @param z intermediate tower */
   public static void towersOfHanoi(int n, int x, int y, int z)
   {// Move the top n disks from tower x to tower y.
    // Use tower z for intermediate storage.
       if (n > 0)
       {
          towersOfHanoi(n-1, x, z, y);
          System.out.println("Move top disk from tower " + x +
                             " to top of tower " + y);
          towersOfHanoi(n-1, z, y, x);
       }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      System.out.println("Moves for a three disk problem are");

      // move 3 disks from tower 1 to tower 2 using tower 3
      // as the intermediate tower
      towersOfHanoi(3, 1, 2, 3);
   }
}

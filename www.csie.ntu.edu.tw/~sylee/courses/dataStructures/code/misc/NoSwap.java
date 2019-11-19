
/** incorrect swap method */

package misc;


public class NoSwap
{
   /** Swap the integers x and y */
   public static void swap(int x, int y)
   {
      int temp = x;
      x = y;
      y = temp;
   }

   public static void main(String [] args)
   {
      int a = 2;
      int b = 3;
      
      System.out.println("a = " + a + " b = " + b);

      // test integer swap
      swap(a,b);

      // output the new values
      System.out.println("After swapping, a = "
                        + a + " and b = " + b);
   }
}

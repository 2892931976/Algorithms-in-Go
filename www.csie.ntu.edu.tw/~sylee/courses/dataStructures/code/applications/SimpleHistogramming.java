
/** simple histogramming program for nonnegative integer values */

package applications;

import utilities.*;

public class SimpleHistogramming
{
   public static void main(String [] args)
   {
      // define keyboard to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of elements and range");
      int n = keyboard.readInteger();   // number of elements
      int r = keyboard.readInteger();   // elements must be between
                                        // 0 and r
   
      // create histogram array h, initial values are 0
      int [] h = new int [r + 1];
   
      // input data and compute histogram
      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter element " + i);
         h[keyboard.readInteger()]++;
      }
   
      // output histogram
      System.out.println("Distinct elements and frequencies are");
      for (int i = 0; i <= r; i++)
         if (h[i] != 0)
            System.out.println(i + "   "  + h[i]);
   }
}

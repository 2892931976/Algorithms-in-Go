/** switch box routing */

package applications;

import dataStructures.*;
import utilities.*;

public class SwitchBox
{
   /** determine whether the switch box is routable
     * @param net[0..net.length-1] array of pin to net assignments
     * @param n number of pins */
   public static boolean checkBox(int [] net)
   {
      int n = net.length;   // number of pins
      ArrayStack s = new ArrayStack();
   
      // scan nets clockwise
      for (int i = 0; i < n; i++)
         // process pin i
         if (!s.empty())
            // check with top net
            if (net[i] == net[((Integer) s.peek()).intValue()])
               // net[i] is routable, delete from stack
               s.pop();
            else s.push(new Integer(i));
         else s.push(new Integer(i));
   
      // any unrouted nets left?
      if (s.empty())
      {// no nets remain
          System.out.println("Switch box is routable");
          return true;
       }
   
      System.out.println("Switch box is not routable");
      return false;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // input the number of pins and their net assignment
      System.out.println("Type number of pins in switch box");
      int n = keyboard.readInteger();

      // create net assignment array
      int [] net = new int[n];

      // input the net assignments
     System.out.println("Type net numbers for pins 1 through " + n);
     for (int i = 0; i < n; i++)
        net[i] = keyboard.readInteger();

     // see if the switch box is routable
     checkBox(net);
   }
}

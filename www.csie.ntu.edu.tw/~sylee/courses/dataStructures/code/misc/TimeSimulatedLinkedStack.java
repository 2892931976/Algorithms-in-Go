
/** time for 250000 push, peek, and pop operations using SimulatedLinkedStack */

package misc;

import dataStructures.*;

public class TimeSimulatedLinkedStack
{
   public static void main(String [] args)
   {
      SimulatedLinkedStack.S = new SimulatedSpace2(250000);
      SimulatedLinkedStack s = new SimulatedLinkedStack();
      int n = 250000;  // number of push operations
      Integer x = new Integer(2);
      long startTime = System.currentTimeMillis();
      // add n elements
      for (int i = 1; i <= n; i++)
        s.push(x);

      // now remove them
      for (int i = 1; i <= n; i++)
      {
        s.peek();
        s.pop();
      }
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("SimulatedLinkedStack took " + elapsedTime +
                         " ms when space started with capacity 10");
   }
}

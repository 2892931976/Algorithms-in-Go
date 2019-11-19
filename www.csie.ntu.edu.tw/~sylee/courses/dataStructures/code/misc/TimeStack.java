

/** time for 500000 push, peek, and pop operations using Stack */

package misc;

import java.util.*;

public class TimeStack
{
   public static void main(String [] args)
   {
      Stack s = new Stack();
      int n = 500000;  // number of push operations
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
      System.out.println("Stack took " + elapsedTime +
                         " ms when started with capacity 10");
   }
}

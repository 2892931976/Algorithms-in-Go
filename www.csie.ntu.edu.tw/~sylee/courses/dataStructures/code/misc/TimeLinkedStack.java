

/** time for 250000 push, peek, and pop operations using LinkedStack */

package misc;

import dataStructures.*;

public class TimeLinkedStack
{
   public static void main(String [] args)
   {
      LinkedStack s = new LinkedStack();
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
      System.out.println("LinkedStack took " + elapsedTime + " ms");
   }
}

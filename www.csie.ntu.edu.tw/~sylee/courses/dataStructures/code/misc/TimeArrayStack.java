

/** time for 500000 push, peek, and pop operations using ArrayStack */

package misc;

import dataStructures.*;

public class TimeArrayStack
{
   public static void main(String [] args)
   {
      ArrayStack s = new ArrayStack();
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
      System.out.println("Array stack took " + elapsedTime +
                         " ms when started with capacity 10");

      s = new ArrayStack(n);
      startTime = System.currentTimeMillis();
      // add n elements
      for (int i = 1; i <= n; i++)
        s.push(x);

      // now remove them
      for (int i = 1; i <= n; i++)
      {
        s.peek();
        s.pop();
      }
      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Array stack took " + elapsedTime +
                         " ms when started with capacity " + n);

   }
}

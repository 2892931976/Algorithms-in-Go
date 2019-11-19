
/** time for 500000 push, peek, and pop operations using 
  * DerivedArrayStackWithCatch */

package misc;

import dataStructures.*;

public class TimeDerivedArrayStackWithCatch
{
   public static void main(String [] args)
   {
      DerivedArrayStackWithCatch s = new DerivedArrayStackWithCatch();
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
      System.out.println("Derived array stack with catch took " + elapsedTime +
                         " ms when started with capacity 10");

      s = new DerivedArrayStackWithCatch(n);
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
      System.out.println("Derived array stack with catch took " + elapsedTime +
                         " ms when started with capacity " + n);

   }
}

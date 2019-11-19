

/** time for 500000 push, peek, and pop operations using FormulaStack */

package misc;

import dataStructures.*;
import utilities.*;
import wrappers.*;

public class TimeFormulaStack
{
   public static void main(String [] args)
   {
      FormulaStack s = new FormulaStack();
      int n = 500000;  // number of push operations
      MyInteger x = new MyInteger(2);
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
      System.out.println("Formula stack took " + elapsedTime +
                         " ms when started with capacity 10");

      s = new FormulaStack(n);
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
      System.out.println("Formula stack took " + elapsedTime +
                         " ms when started with capacity " + n);

   }
}

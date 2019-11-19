

/** time SimulatedChain operations */

package misc;

import dataStructures.*;
import java.util.*;

public class TimeSimulatedChain
{
   public static void main(String [] args)
   {
      SimulatedChain s = new SimulatedChain();
      int n = 40000;  // number of operations
      Integer x = new Integer(2);

      System.out.println("Timing SimulatedChain");

      // worst-case test
      System.out.println("Worst-case times in progress");

      // insert n elements
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(i,x);

      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms");


      // get test
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.get(i);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " gets took " + elapsedTime +
                         " ms");


      // now remove them
      startTime = System.currentTimeMillis();
      for (int i = n-1; i >= 0; i--)
        s.remove(i);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " removes took " + elapsedTime +
                         " ms"); 

      // average-case test
      System.out.println();
      System.out.println("Average-case times in progress");

      SimulatedChain.S = new SimulatedSpace1(40010);
      s = new SimulatedChain();
      Random r = new Random();
      n = 40000;


      // insert n elements
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
      {
        int j = r.nextInt();
        if (j < 0)
          j = -j;
        j = j % (i + 1);
        s.add(j,x);
      }

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms");



      // now remove them
      startTime = System.currentTimeMillis();
      for (int i = n; i > 0; i--)
      {
        int j = r.nextInt();
        if (j < 0)
          j = -j;
        j = j % i;
        s.remove(j);
      }

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " removes took " + elapsedTime +
                         " ms");


      // best-case test
      System.out.println();
      System.out.println("Best-case times in progress");
      SimulatedChain.S = new SimulatedSpace1(10);
      s = new SimulatedChain();
      n = 1000000;


      // insert n elements
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(0,x);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime + " ms");



      // now remove them
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.remove(0);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " removes took " + elapsedTime +
                         " ms");

   }
}

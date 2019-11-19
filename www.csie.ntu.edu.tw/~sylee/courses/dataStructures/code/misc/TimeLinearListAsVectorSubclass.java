


/** time LinearListAsVectorSubclass operations */

package misc;

import dataStructures.*;
import java.util.*;

public class TimeLinearListAsVectorSubclass
{
   public static void main(String [] args)
   {
      LinearListAsVectorSubclass s = new LinearListAsVectorSubclass();
      Integer x = new Integer(2);

      System.out.println("Timing LinearListAsVectorSubclass");

      // best-case test
      System.out.println("Best-case times in progress");
      int n = 1000000;

      // insert n elements
      long startTime = System.currentTimeMillis();

      int m = 10;
      for (int j = 0; j < m; j++)
      {
         s = new LinearListAsVectorSubclass();
         for (int i = 0; i < n; i++)
           s.add(i,x);
      }

      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + (elapsedTime / m) +
                         " ms when started with capacity 10");

      long insertTime = elapsedTime / m;

      // get test
      startTime = System.currentTimeMillis();
      m = 100;
      for (int j = 0; j < m; j++)
         for (int i = 0; i < n; i++)
           s.get(i);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " gets took " + (elapsedTime / m) +
                         " ms");


      // now remove them
      startTime = System.currentTimeMillis();
      m = 50;
      for (int j = 0; j < m; j++)
      {
         s = new LinearListAsVectorSubclass();
         for (int i = 0; i < n; i++)
           s.add(i,x);
         for (int i = n-1; i >= 0; i--)
           s.remove(i);
      }

      long elapsedTime1 = System.currentTimeMillis() - startTime;

      elapsedTime = elapsedTime1 / m - insertTime;

      System.out.println(n + " removes took " + elapsedTime +
                         " ms"); 


      // insert n elements
      startTime = System.currentTimeMillis();
      m = 20;
      for (int j = 0; j < m; j++)
      {
         s = new LinearListAsVectorSubclass(n);
         for (int i = 0; i < n; i++)
           s.add(i,x);
      }

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + (elapsedTime / m) +
                         " ms when started with capacity " + n);

      // average-case test
      System.out.println();
      System.out.println("Average-case times in progress");

      // random number overhead
      Random r = new Random();
      m = 1000000;
      startTime = System.currentTimeMillis();
      for (int i = 0; i < m; i++)
      {
        int j = r.nextInt();
        if (j < 0)
          j = -j;
        j = j % (i + 1);
      }

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(m + " randoms took " + elapsedTime + " ms");

      s = new LinearListAsVectorSubclass();
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
                         " ms when started with capacity 10");



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


      s = new LinearListAsVectorSubclass(n);

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
                         " ms when started with capacity " + n);

      // worst-case test
      System.out.println();
      System.out.println("Worst-case times in progress");
      s = new LinearListAsVectorSubclass();
      n = 40000;


      // insert n elements
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(0,x);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms when started with capacity 10");



      // now remove them
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.remove(0);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " removes took " + elapsedTime +
                         " ms");


      s = new LinearListAsVectorSubclass(n);

      // insert n elements
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(0,x);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms when started with capacity " + n);
   }
}



/** time ArrayList operations */


import dataStructures.*;
import java.util.*;

public class t
{
   public static void main(String [] args)
   {
      ArrayList s = new ArrayList();
      Integer x = new Integer(2);

      System.out.println("Timing ArrayList");

      // worst-case test
      System.out.println();
      System.out.println("Worst-case times in progress");
      s = new ArrayList();
      int n = 40000;


      // insert n elements
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(0,x);

      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms when started with capacity 10");



      // now remove them
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.remove(0);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " removes took " + elapsedTime +
                         " ms");


      s = new ArrayList(n);

      // insert n elements
      startTime = System.currentTimeMillis();
      for (int i = 0; i < n; i++)
        s.add(0,x);

      elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println(n + " inserts took " + elapsedTime +
                         " ms when started with capacity " + n);
   }
}

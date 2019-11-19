

/** inaccurate way to find the worst-case run time of insertion sort */

package misc;

import applications.*;

public class TimeInsertionSort1
{
   public static void main(String [] args)
   {
      int step = 10;

      System.out.println("The worst-case times, in milliseconds, are");
      System.out.println("n \telapsed time");
      for (int n = 0; n <= 1000; n += step)
      {
         // create element array
         Integer [] a = new Integer[n];

         // initialize array
         for (int i = 0; i < n; i++)
            a[i] = new Integer(n - i);

         long startTime = System.currentTimeMillis();

         // sort the elements
         InsertionSort2.insertionSort(a);

         long elapsedTime = System.currentTimeMillis() - startTime;

         System.out.println(n + "\t" + elapsedTime);

         if (n == 100) step = 100;
      }
   }
}

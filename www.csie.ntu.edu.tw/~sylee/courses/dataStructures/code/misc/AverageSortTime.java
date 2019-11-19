

/** average sort times */

package misc;

import java.util.*;
import applications.*;
import utilities.*;
import wrappers.*;

public class AverageSortTime
{
   public static void main(String [] args)
   {
      Random r = new Random();

      int step = 10;

      System.out.println("The average-case times, in milliseconds, are");
      System.out.println("n \trepetitions \telapsed time \ttime/sort");
      for (int n = 0; n <= 1000; n += step)
      {
         // create element arrays
         // MyInteger [] a = new MyInteger[n];
         MyInteger [] a = new MyInteger[n + 1];

         long startTime = System.currentTimeMillis();
         long counter = 0;
         while (counter < 200 ||
                System.currentTimeMillis( ) - startTime < 1000)
         {
            counter++;
            // create random data
            // for (int i = 0; i < n; i++)
            for (int i = 1; i <= n; i++)
               a[i] = new MyInteger(r.nextInt());

            // sort the elements
            // MergeSort.mergeSort(a);
            HeapSort.heapSort(a);
         }

         long elapsedTime = System.currentTimeMillis() - startTime;
         System.out.println(n + "\t" + counter + "\t\t" +
            + elapsedTime + "\t\t" + ((float) elapsedTime)/counter);

         if (n == 100) step = 100;
      }
   }
}

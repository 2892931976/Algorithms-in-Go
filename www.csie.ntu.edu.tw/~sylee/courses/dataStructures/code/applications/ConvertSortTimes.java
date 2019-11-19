/** create table format for sort times */
package applications;

import utilities.*;

public class ConvertSortTimes
{
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      int n = 20; // 20 sets per batch
      int b = 4;  // number of batches
      int [] size = new int [n];
      double [][] time = new double [b][n];
      for (int j = 0; j < b; j++)
         for (int i = 0; i < n; i++)
         {
            size[i] = keyboard.readInteger();
            time[j][i] = keyboard.readDouble();
            time[j][i] = keyboard.readDouble();
            time[j][i] = keyboard.readDouble();
         }

      // output in desired form
      for (int i = 0; i < n; i++)
      {
         System.out.print(size[i]);
         for (int j = 0; j < b; j++)
            System.out.print(" & " + time[j][i]);
         System.out.println();
      }
   }
}

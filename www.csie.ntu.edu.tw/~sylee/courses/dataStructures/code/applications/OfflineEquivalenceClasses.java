/** solution to the offline equivalence classes problem */

package applications;

import java.util.*;
import dataStructures.*;
import utilities.*;

public class OfflineEquivalenceClasses
{
   /** input the relation pairs and output the classes */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // input the number of elements, n
      System.out.println("Enter number of elements");
      int n = keyboard.readInteger();
      if (n < 2)
      {
         System.out.println("Too few elements");
         System.exit(1);   // terminate
      }

      // input the number of relations, r
      System.out.println("Enter number of relations");
      int r = keyboard.readInteger();
      if (r < 1)
      {
         System.out.println("Too few relations");
         System.exit(1);
      }

      // create an array of empty stacks, list[0] not used
      ArrayStack [] list = new ArrayStack [n + 1];
      for (int i = 1; i <= n; i++)
         list[i] = new ArrayStack();
                  
      // input the r relations and put on stacks
      for (int i = 1; i <= r; i++)
      {
          System.out.println("Enter next relation/pair");
          int a = keyboard.readInteger();
          int b = keyboard.readInteger();
          list[a].push(new Integer(b));
          list[b].push(new Integer(a));
      }
      
      // initialize to output equivalence classes
      ArrayStack unprocessedList = new ArrayStack();
      boolean [] out = new boolean [n + 1];
      // default boolean value is false
      
      // output equivalence classes
      for (int i = 1; i <= n; i++)
        if (!out[i])
        {// start of a new class
            System.out.print("Next class is: " + i + " ");
            out[i] = true;
            unprocessedList.push(new Integer(i));
            // get rest of class from unprocessedList
            while (!unprocessedList.empty())
            {
               int j = ((Integer) unprocessedList.pop()).intValue();
    
               // elements on list[j] are in the same class
               while (!list[j].empty())
               {
                  int q = ((Integer) list[j].pop()).intValue();
                  if (!out[q])  // q not yet output
                  {
                      System.out.print(q + " ");
                      out[q] = true;
                      unprocessedList.push(new Integer(q));
                  }
               }
            }
            System.out.println();
         }
   
      System.out.println("End of list of equivalence classes");
   }  
}

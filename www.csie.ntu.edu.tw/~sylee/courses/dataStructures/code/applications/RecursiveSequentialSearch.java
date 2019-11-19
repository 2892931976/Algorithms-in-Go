

/** recursive sequential search */

package applications;

import wrappers.*;

public class RecursiveSequentialSearch
{
   // private data members for the class
   private static Object [] a;   // search array
   private static Object x;      // search object

   /** Search the unordered array b for y.
     * Driver for true recursive method rSearch.
     * @return position of y if found; -1 otherwise */
   public static int sequentialSearch(Object [] b, Object y)
   {
      a = b;  // set search array
      x = y;  // set search object
      return rSearch(a.length - 1);
   }
   
   /** Recursively search a[0:lastPosition] for x.
     * CAUTION: Object.equals must be overriden for this
     * method to work correctly */
   private static int rSearch(int lastPosition)
   {
      if (lastPosition < 0)
         return -1;
      if (x.equals(a[lastPosition]))
         return lastPosition;
      return rSearch(lastPosition - 1);
   }

   /** test program */
   public static void main(String [] args)
   {
      MyInteger [] a = {new MyInteger(0),
                        new MyInteger(5),
                        new MyInteger(3),
                        new MyInteger(4),
                        new MyInteger(7),
                        new MyInteger(2),
                        new MyInteger(6)};
      MyInteger x = new MyInteger(20);

      for (int i = 0; i <= a.length; i++)
      {// search for x
         int j = sequentialSearch(a, x);
         if (j < 0)
            System.out.println(x + " is not in the array");
         else
            System.out.println("a[" + j + "] = " + x);
         // look for next array object
         if (i < a.length) x = a[i];
      }
   }
}

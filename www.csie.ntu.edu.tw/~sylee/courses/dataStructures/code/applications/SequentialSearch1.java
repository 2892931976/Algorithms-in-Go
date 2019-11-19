
/** sequential search */

package applications;

import wrappers.*;

public class SequentialSearch1
{
   /** Search the unordered array a for x.
     * CAUTION: Object.equals must be overriden for this
     * method to work correctly
     * @return position of x if found; -1 otherwise */
   public static int sequentialSearch(Object [] a, Object x)
   {
      int i;
      for (i = 0; i < a.length && !x.equals(a[i]); i++);
      if (i == a.length) return -1;
      else return i;
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

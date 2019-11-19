

/** sequential search assuming last array
  * position is free */

package applications;


public class SequentialSearch2
{
   /** Search the unordered array a for x.
     * CAUTION: Object.equals must be overriden for this
     * method to work correctly
     * @return position of x if found; -1 otherwise */
   public static int sequentialSearch(Object [] a, Object x)
   {
      a[a.length - 1] = x; // assume extra position available
      int i;
      for (i = 0; !x.equals(a[i]); i++);

      if (i == a.length - 1) return -1;
      else return i;
   }

   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(0),
                      new Integer(5),
                      new Integer(3),
                      new Integer(4),
                      new Integer(7),
                      new Integer(2),
                      new Integer(6),
                      null};  // dummy
      Integer x = new Integer(20);

      for (int i = 0; i < a.length; i++)
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

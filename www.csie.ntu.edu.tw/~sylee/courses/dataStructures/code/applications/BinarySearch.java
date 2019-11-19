

/** binary search of sorted array */

package applications;

public class BinarySearch
{
   /** Search the ordered array a for x.
     * CAUTION: Object.equals must be overriden for this
     * method to work correctly
     * @return position of x if found; -1 otherwise */
   public static int binarySearch(Comparable [] a, Comparable x)
   {// Search a[0] <= a[1] <= ... <= a[a.length-1] for x.
      int left = 0;
      int right = a.length - 1;
      while (left <= right)
      {
         int middle = (left + right)/2;
         if (x.equals(a[middle]))
            return middle;
         if (x.compareTo(a[middle]) > 0)
            left = middle + 1;
         else
            right = middle - 1;
      }
      return -1; // x not found
   }
   
   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(10),
                      new Integer(15),
                      new Integer(18),
                      new Integer(24),
                      new Integer(27),
                      new Integer(32),
                      new Integer(36)};
      Integer x = new Integer(20);

      for (int i = 0; i <= a.length; i++)
      {// search for x
         int j = binarySearch(a, x);
         if (j < 0)
            System.out.println(x + " is not in the array");
         else
            System.out.println("a[" + j + "] = " + x);
         // look for next array object
         if (i < a.length) x = a[i];
      }
   }
}


/** insert into a sorted array */

package applications;

public class Insert
{
   /** insert x into the sorted array a[0:n-1]
     * a remains sorted after the insertion 
     * @throws IllegalArgumentException when
     * array a cannot accomodate the extra element */
   public static void insert(Comparable [] a, int n, Comparable x)
   {
      if (a.length < n + 1)
         throw new IllegalArgumentException
               ("array not large enough");

      // find proper place for x
      int i;
      for (i = n - 1; i >= 0 && x.compareTo(a[i]) < 0; i--)
         a[i+1] = a[i];

      a[i+1] = x;  // insert x
   }

   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(12),
                      new Integer(15),
                      new Integer(17),
                      new Integer(18),
                      new Integer(22),
                      new Integer(29),
                      new Integer(30),
                      new Integer(40),
                      new Integer(55),
                      new Integer(0),   // dummy
                      new Integer(0)};  // dummy

      // output initial elements
      System.out.println("The initial elements are");
      for (int i = 0; i < a.length - 2; i++)
         System.out.print(a[i] + " ");
      System.out.println();
      
      Integer x = new Integer(16);
      insert(a, 9, x);
      x = new Integer(10);
      insert(a, 10, x);

      // output in sorted order
      System.out.println("After insertion, the elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}

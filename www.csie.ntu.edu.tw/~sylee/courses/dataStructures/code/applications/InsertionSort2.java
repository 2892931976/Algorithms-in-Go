

/** insertion sort */

package applications;

public class InsertionSort2
{
   /** sort the array a using the insertion sort method */
   public static void insertionSort(Comparable [] a)
   {
      for (int i = 1; i < a.length; i++)
      {  // insert a[i] into a[0:i-1]
         Comparable t = a[i];
   
         // find proper place for t
         int j;
         for (j = i - 1;
              j >= 0 && t.compareTo(a[j]) < 0; j--)
            a[j+1] = a[j];
   
         a[j+1] = t;  // insert t = original a[i]
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      Integer [] a = {new Integer(3),
                      new Integer(2),
                      new Integer(4),
                      new Integer(1),
                      new Integer(6),
                      new Integer(9),
                      new Integer(8),
                      new Integer(7),
                      new Integer(5),
                      new Integer(0)};

      // output elements to be sorted
      System.out.println("The elements are");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();

      // sort the elements
      insertionSort(a);

      // output in sorted order
      System.out.println("The sorted order is");
      for (int i = 0; i < a.length; i++)
         System.out.print(a[i] + " ");
      System.out.println();
   }
}

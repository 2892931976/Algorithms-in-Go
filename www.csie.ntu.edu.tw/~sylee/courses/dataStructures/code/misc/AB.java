

package misc;

public class AB
{
  /** method to do this
   @param x input array */
   public static void d(int[] x, int n)
   {
      for (int i = 0; i < n; i += 2)
         x[i] += 2;
      int i = 1;
      while (i <= n/2)
      {
         x[i] += x[i+1];
         i++;
      }
   }
   
   public static void main(String [] args)
   {
      int [] y = {1,2,3,4,5,6,7,8,9,10,0,0,0,0,0};
      int n = 10;
      d(y,n);
      for (int i = 0; i < n; i++)
         System.out.print(y[i] + " ");
      System.out.println();
   }
}

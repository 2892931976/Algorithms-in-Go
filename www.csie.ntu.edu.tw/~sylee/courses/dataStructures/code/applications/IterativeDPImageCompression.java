
/** iterative dynamic programming image compression */

package applications;

import utilities.*;

public class IterativeDPImageCompression
{
   static final int maxLength = 256;   // max segment length
   static final int header = 11;       // size of segment header

   /** iterative dynamic programming segment combining code
     * l and b are inputs, s and kay are computed
     * @param l[i] is length of segment i
     * @param b[i] is bits/pixel for segment i
     * @param s[i] is min space for first i segments
     * @param kay[] is recurrence selector */
   public static void vBits(int [] l, int [] b, int [] s, int [] kay)
   {
      int n = l.length - 1;   // number of segments
      s[0] = 0;
      // compute s[i] using Eq. 15.3
      for (int i = 1; i <= n; i++)
      {
         // compute min term for k = 1
         int lsum = l[i],
             bmax = b[i];
         int best = s[i - 1] + lsum * bmax;
         kay[i] = 1;
   
         // compute for remaining k and update
         for (int k = 2; k <= i && lsum + l[i - k + 1] <= maxLength; k++)
         {
            lsum += l[i - k + 1];
            if (bmax < b[i - k + 1])
               bmax = b[i - k + 1];
            if (best > s[i - k] + lsum * bmax)
            {
               best = s[i-k] + lsum * bmax;
               kay[i] = k;
            }
         }
   
         s[i] = best + header;
      }
   }

   /** output the optimal segment boundaries */
   public static void traceback(int [] kay, int n)
   {
      if (n == 0)
         return;
      traceback(kay, n - kay[n]);
      System.out.println("New segment begins at " + (n - kay[n] + 1));
   }
   
   /** driver program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // initialize
      System.out.println("Enter number of segments");
      int n = keyboard.readInteger();
      int [] l = new int [n + 1];
      int [] b = new int [n + 1];
      int [] kay = new int [n + 1];
      int [] es = new int [n + 1];

      // input segment data
      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter length and bits/pixel for segment " + i);
         l[i] = keyboard.readInteger();
         b[i] = keyboard.readInteger();
      }

      // compute minimum length
      vBits(l, b, es, kay);
      System.out.println("Minimum length is " + es[n]);
      System.out.println("kay values are");
      for (int i = 1; i <= n; i++)
         System.out.print(kay[i] + " ");
      System.out.println();

      // output boundaries
      traceback(kay, n);
   }
}

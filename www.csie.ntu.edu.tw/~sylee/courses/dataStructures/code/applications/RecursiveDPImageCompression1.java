/** first version of recursive dynamic programming image compression */

package applications;

import utilities.*;

public class RecursiveDPImageCompression1
{
   static final int maxLength = 256;   // max segment length
   static final int header = 11;        // size of segment header
   static int [] kay;      // recurrence selector
   static int [] l;        // l[i] is number of pixels in segment i
   static int [] b;        // b[i] is bits per pixel of segment i
   
   /** set class data members and invoke method s */
   public static int vBits(int [] l, int [] b, int [] kay)
   {
      RecursiveDPImageCompression1.l = l;
      RecursiveDPImageCompression1.b = b;
      RecursiveDPImageCompression1.kay = kay;
      return s(l.length - 1);
   }

   /** recursively compute s(i) and kay[i] using
     * the dynamic programming recurrence */
   private static int s(int i)
   {
      if (i == 0)
         return 0;

      // compute min term of Eq. 15.3 for k = 1
      int lsum = l[i],
          bmax = b[i];
      int best = s(i - 1) + lsum * bmax;
      kay[i] = 1;
   
      // compute min term for remaining k and find min
      for (int k = 2; k <= i && lsum + l[i - k + 1] <= maxLength; k++)
      {
         lsum += l[i - k + 1];
         if (bmax < b[i - k + 1])
            bmax = b[i - k + 1];
         int t = s(i - k);     
         if (best > t + lsum * bmax)
         {// found a smaller term
            best = t + lsum * bmax;
            kay[i] = k;
         }
     }   
   
      return best + header;
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

      // input segment data
      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter length and bits/pixel for segment " + i);
         l[i] = keyboard.readInteger();
         b[i] = keyboard.readInteger();
      }

      // compute minimum length
      System.out.println("Minimum length is " + vBits(l, b, kay));
      System.out.println("kay values are");
      for (int i = 1; i <= n; i++)
         System.out.print(kay[i] + " ");
      System.out.println();

      // output boundaries
      traceback(kay, n);
   }
}

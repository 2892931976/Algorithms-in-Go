
/** iterative dynamic programming code to find the
  * maximum noncrossing subset */

package applications;

import utilities.*;

public class IterativeDPNoncrossingSubset
{
   /** compute size[i][j] for all i and j */
   public static void mns(int [] theC, int [][] size)
   {
      int numberOfNets = theC.length - 1;
      // initialize size[1][*]
      for (int j = 0; j < theC[1]; j++)
         size[1][j] = 0;
      for (int j = theC[1]; j <= numberOfNets; j++)
         size[1][j] = 1;
   
      // compute size[i][*], 1 < i < numberOfNets
      for (int i = 2; i < numberOfNets; i++)
      {
         for (int j = 0; j < theC[i]; j++)
            size[i][j] = size[i - 1][j];
         for (int j = theC[i]; j <= numberOfNets; j++)
            size[i][j] = Math.max(size[i - 1][j],
                             size[i - 1][theC[i] - 1] + 1);
      }
   
      size[numberOfNets][numberOfNets] =
          Math.max(size[numberOfNets - 1][numberOfNets],
                   size[numberOfNets - 1][theC[numberOfNets] - 1] + 1);
   }
         
   /** put max noncrossing subset in net[0:sizeOfMNS-1]
     * @return size of MNS */
   public static int traceback(int [] theC, int [][] size, int [] net)
   {
      int numberOfNets = theC.length - 1;
      int maxAllowed = numberOfNets;   // max bottom pin number allowed
      int sizeOfMNS = 0;
      for (int i = numberOfNets; i > 1; i--)
         // is net i in MNS?
         if (size[i][maxAllowed] != size[i - 1][maxAllowed])
         {// yes, net i is in the MNS
            net[sizeOfMNS++] = i;
            maxAllowed = theC[i] - 1;
         }
   
      // is net 1 in MNS?
      if (maxAllowed >= theC[1])
         net[sizeOfMNS++] = 1;  // yes

      return sizeOfMNS;
   }
   
   /** driver program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // initialize
      System.out.println("Enter number of nets");
      int n = keyboard.readInteger();
      int [][] size = new int [n + 1][n + 1];
      int [] c = new int [n + 1];

      // input net data
      System.out.println("Enter the C values");
      for (int i = 1; i <= n; i++)
         c[i] = keyboard.readInteger();

      // compute size[][]
      mns(c, size);
      
      System.out.println("matrix size is");
      for (int i = 1; i < n; i++)
      {
         for (int j = 1; j <= n; j++)
            System.out.print(size[i][j] + " ");
         System.out.println();
      }
      
      // determine the MNS and its size
      int [] net = new int [n + 1];
      int s = traceback(c, size, net);
      
      System.out.print("\nMaximum non-crossing subset is ");
      for (int i = 0; i < s; i++)
         System.out.print(net[i] + " ");
      System.out.println();
   }
}

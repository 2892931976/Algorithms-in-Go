
/** backtracking method to find best board arrangement */

package applications;

import utilities.*;

public class RecursiveBTBoard
{
   // class data members
   static int numberOfBoards;
   static int numberOfNets;
   static int [] partial;          // partial board permutation
   static int [] bestPermutationSoFar;
   static int [] boardsWithNet;
   static int [] boardsInPartialWithNet;
   static int leastDensitySoFar;
   static int [][] board;          // 2D board array
   
   /** preprocessor for recursive backtracking method
     * @return density of best arrangement */
   public static int arrangeBoards(int [][] theBoard,
                                   int theNumberOfNets,
                                   int [] bestPermutation)
   {
      // set class data members
      numberOfBoards = theBoard.length - 1;
      numberOfNets = theNumberOfNets;
      partial = new int [numberOfBoards + 1];
      bestPermutationSoFar = bestPermutation;
      boardsWithNet = new int [numberOfNets + 1];
      boardsInPartialWithNet = new int [numberOfNets + 1];
      leastDensitySoFar = numberOfNets + 1;
      board = theBoard;
   
      // initialize partial to identity permutation
      // and compute boardsWithNet[]
      for (int i = 1; i <= numberOfBoards; i++)
      {
         partial[i] = i;
         for (int j = 1; j <= numberOfNets; j++)
            boardsWithNet[j] += board[i][j];
      }
   
      // find best arrangement
      rBoard(1, 0);
      return leastDensitySoFar;
   }

   /** recursive backtracking search of permutation tree */
   private static void rBoard(int currentLevel, int densityOfPartial)
   {// search from a node at level currentLevel
      if (currentLevel == numberOfBoards)
      {// all boards placed, we are at a better permutation
         for (int j = 1; j <= numberOfBoards; j++)
            bestPermutationSoFar[j] = partial[j];
         leastDensitySoFar = densityOfPartial;
      }
      else // try out subtrees
         for (int j = currentLevel; j <= numberOfBoards; j++)
         {// try child with board partial[j] as next one
   
            // update boardsInPartialWithNet[] 
            // and compute density at last slot
            int density = 0;
            for (int k = 1; k <= numberOfNets; k++)
            {
               boardsInPartialWithNet[k] += board[partial[j]][k];
               if (boardsInPartialWithNet[k] > 0 &&
                   boardsWithNet[k] != boardsInPartialWithNet[k])
                  density++;
            }
   
            // update density to be overall density of partial arrangement
            if (densityOfPartial > density)
               density = densityOfPartial;
   
            // search subtree only if it may contain a better arrangement
            if (density < leastDensitySoFar)
            {// move to child
               MyMath.swap(partial, currentLevel, j);
               rBoard(currentLevel + 1, density);
               MyMath.swap(partial, currentLevel, j);
            }
   
            // reset boardsInPartialWithNet[]
            for (int k = 1; k <= numberOfNets; k++)
               boardsInPartialWithNet[k] -= board[partial[j]][k];
         }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // input number of boards and nets
      System.out.println("Enter number of boards and number of nets");
      int n = keyboard.readInteger();
      int m = keyboard.readInteger();

      // define and input board array
      int [][] board = new int [n + 1][m + 1];
      System.out.println("Enter net matrix");
      for (int i =1; i <= n; i++) 
         for (int j = 1; j <= m; j++)
            board[i][j] = keyboard.readInteger();
   
      // define array for best board arrangement
      int [] p = new int [n + 1];

      System.out.println("\nMinimum density is " + arrangeBoards(board, m, p));
      System.out.println("Optimal arrangement is ");
      for (int i = 1; i <= n; i++)
         System.out.print(p[i] + " ");
      System.out.println();
   }
}

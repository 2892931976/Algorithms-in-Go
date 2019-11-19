
/** least-cost branch-and-bound method to find best board arrangement */

package applications;

import dataStructures.*;
import utilities.*;

public class LeastCostBBBoard
{
   // top-level nested class
   private static class HeapNode implements Comparable
   {
      // data members
      int partialDensity;             // density of partial arrangement
      int [] boardsInPartialWithNet; 
      int sizeOfPartial;       // partial arrangement is
                               // partial[1:sizeOfPartial]
      int [] partial;          // partial[sizeOfPartial+1:numberOfBoards]
                               // gives remaining boards
                               // to be added to partial[1:sizeOfPartial]

      // constructor
      private HeapNode(int thePartialDensity,
                       int [] theBoardsInPartialWithNet,
                       int theSizeOfPartial,
                       int [] thePartial)
      {
         partialDensity = thePartialDensity;
         boardsInPartialWithNet = theBoardsInPartialWithNet;
         sizeOfPartial = theSizeOfPartial;
         partial = thePartial;
      }

      public int compareTo(Object x)
      {
         int xPartialDensity = ((HeapNode) x).partialDensity;
         if (partialDensity < xPartialDensity) return -1;
         if (partialDensity == xPartialDensity) return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return partialDensity == ((HeapNode) x).partialDensity;}
   }

   /** least-cost branch-and-bound code
     * @param board 2-D board array
     * @return density of best arrangement */
   public static int leastCostBBBoards(int [][] board, int numberOfNets,
                                       int [] bestPermutation)
   {
      int numberOfBoards = board.length - 1;
      MinHeap liveNodeMinHeap = new MinHeap();

      // initialize first E-node (partialDensity,
      // boardsInPartialWithNet, sizeOfPartial, partial)
      HeapNode eNode = new HeapNode(0, new int [numberOfNets + 1],
                                    0, new int [numberOfBoards + 1]);

      // set eNode.boardsInPartialWithNet[i] = number of boards
      // in partial[1:s] with net i
      // set eNode.partial[i] = i, initial permutation
      // set eNode.boardsWithNet[i] = number of boards with net i
      int [] boardsWithNet = new int [numberOfNets + 1];
      for (int i = 1; i <= numberOfBoards; i++)
      {
         eNode.partial[i] = i;
         for (int j = 1; j <= numberOfNets; j++)
            boardsWithNet[j] += board[i][j];
      }

      int leastDensitySoFar = numberOfNets + 1;
      int [] bestPermutationSoFar = null;
      
      do
      {// expand E-node
         if (eNode.sizeOfPartial == numberOfBoards - 1)
         {// one child only
            int localDensityAtLastBoard = 0;
            for (int j = 1; j <= numberOfNets; j++)
               localDensityAtLastBoard +=
                    board[eNode.partial[numberOfBoards]][j];
            if (localDensityAtLastBoard < leastDensitySoFar)
            {// better permutation
               bestPermutationSoFar = eNode.partial;
               leastDensitySoFar = Math.max(localDensityAtLastBoard,
                                            eNode.partialDensity);
            }
         }
         else
         {// generate children of E-node
            for (int i = eNode.sizeOfPartial + 1;
                 i <= numberOfBoards; i++)
            {
               HeapNode hNode = new HeapNode(0, new int
                  [numberOfNets + 1], 0, new int [numberOfBoards + 1]);
               for (int j = 1; j <= numberOfNets; j++)
                  // acccount for nets in new board
                  hNode.boardsInPartialWithNet[j] =
                                     eNode.boardsInPartialWithNet[j]
                                     + board[eNode.partial[i]][j];
   
               int localDensityAtNewBoard = 0;
               for (int j = 1; j <= numberOfNets; j++)
                  if (hNode.boardsInPartialWithNet[j] > 0 &&
                    boardsWithNet[j] != hNode.boardsInPartialWithNet[j])
                     localDensityAtNewBoard++;
   
               hNode.partialDensity = Math.max(localDensityAtNewBoard,
                                               eNode.partialDensity);
               if (hNode.partialDensity < leastDensitySoFar)
               {// may lead to better leaf
                  hNode.sizeOfPartial = eNode.sizeOfPartial + 1;
                  for (int j = 1; j <= numberOfBoards; j++)
                     hNode.partial[j] = eNode.partial[j];
                  hNode.partial[hNode.sizeOfPartial] = eNode.partial[i];
                  hNode.partial[i] = eNode.partial[hNode.sizeOfPartial];
                  liveNodeMinHeap.put(hNode);
               }
            }
         }
   
         // next E-node
         eNode = (HeapNode) liveNodeMinHeap.removeMin();
      } while (eNode != null &&
               eNode.partialDensity < leastDensitySoFar);
      
      for (int i = 1; i <= numberOfBoards; i++)
         bestPermutation[i] = bestPermutationSoFar[i];
      return leastDensitySoFar;
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

      System.out.println("\nMinimum density is " +
                         leastCostBBBoards(board, m, p));
      System.out.println("Optimal arrangement is ");
      for (int i = 1; i <= n; i++)
         System.out.print(p[i] + " ");
      System.out.println();
   }
}

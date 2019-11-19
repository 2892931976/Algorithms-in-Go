/** tile a defective chessboard */

package applications;

import utilities.*;

public class Chessboard
{
   // data member
   int [][] board;   // the chessboard
   int tile;         // current tile to use

   // constructor
   public Chessboard(int size)
   {
      board = new int [size][size];
      tile = 1;
   }
   
   /** @param topRow is row number of top-left corner of board
     * @param topColumn is column number of top-left corner of board
     * @param defectRow is row number of defective square
     * @param defectColumn is column number of defective square
     * @param size is length of one side of chess board */
   public void tileBoard(int topRow, int topColumn,
                         int defectRow, int defectColumn, int size)
   {
      if (size == 1) return;
      int tileToUse = tile++,
          quadrantSize = size / 2;
      
      // tile top-left quadrant
      if (defectRow < topRow + quadrantSize &&
          defectColumn < topColumn + quadrantSize)
         // defect is in this quadrant
         tileBoard(topRow, topColumn, defectRow, defectColumn,
                   quadrantSize);
      else
      {// no defect in this quadrant
         // place a tile in bottom-right corner
         board[topRow + quadrantSize - 1][topColumn + quadrantSize - 1]
              = tileToUse;
         // tile the rest
         tileBoard(topRow, topColumn, topRow + quadrantSize - 1,
                   topColumn + quadrantSize - 1, quadrantSize);
      }
      
      // tile top-right quadrant
      if (defectRow < topRow + quadrantSize &&
          defectColumn >= topColumn + quadrantSize)
         // defect is in this quadrant
         tileBoard(topRow, topColumn + quadrantSize, defectRow,
                   defectColumn, quadrantSize);
      else
      {// no defect in this quadrant
         // place a tile in bottom-left corner
         board[topRow + quadrantSize - 1][topColumn + quadrantSize]
              = tileToUse;
         // tile the rest
         tileBoard(topRow, topColumn + quadrantSize, topRow +
                quadrantSize - 1, topColumn + quadrantSize, quadrantSize);
       }
      
      // tile bottom-left quadrant
      if (defectRow >= topRow + quadrantSize &&
          defectColumn < topColumn + quadrantSize)
         // defect is in this quadrant
         tileBoard(topRow + quadrantSize, topColumn, defectRow,
                   defectColumn, quadrantSize);
      else
      {// place a tile in top-right corner
         board[topRow + quadrantSize][topColumn + quadrantSize - 1]
              = tileToUse;
         // tile the rest
         tileBoard(topRow + quadrantSize, topColumn, topRow + quadrantSize,
                   topColumn + quadrantSize - 1, quadrantSize);
      }
      
      // tile bottom-right quadrant
      if (defectRow >= topRow + quadrantSize && 
          defectColumn >= topColumn + quadrantSize)
         // defect is in this quadrant
         tileBoard(topRow + quadrantSize, topColumn + quadrantSize,
                   defectRow, defectColumn, quadrantSize);
      else
      {// place tile tileToUse in top-left corner
         board[topRow + quadrantSize][topColumn + quadrantSize]
              = tileToUse;
         // tile the rest
         tileBoard(topRow + quadrantSize, topColumn + quadrantSize,
             topRow + quadrantSize, topColumn + quadrantSize, quadrantSize);
       }
   }
   
   /** output the tiled chessboard */
   public void outputBoard(int size)
   {
      for (int i = 0; i < size; i++)
      {
         for (int j = 0; j < size; j++)
            System.out.print(board[i][j] + "\t");
         System.out.println();
      }
   }
   
   /** test tileBoard */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      // input the chessboard size
      System.out.println("Enter k, board size is 2^k");
      System.out.println("k should be in the range 0 through 6");
      int k = keyboard.readInteger();
      if (k < 0 || k > 6)
         {System.out.println("k out of range");
          System.exit(1);
      }
      int size = 1;
      for (int i = 1; i <= k; i++)
         size += size;

      Chessboard c = new Chessboard(size);

      // input location of defect
      System.out.println("Enter location of defect");
      int defectRow = keyboard.readInteger();
      int defectColumn = keyboard.readInteger();
      if (defectRow < 1 || defectRow > size || defectColumn < 1
          || defectColumn > size)
      {
         System.out.println("Improper defect location");
         System.exit(1);
      }

      // convert to array indexes and mark location with 0
      defectRow--;
      defectColumn--;

      // tile the board
      c.tileBoard(0, 0, defectRow, defectColumn, size);

      c.outputBoard(size);
   }
}

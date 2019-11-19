/** find and ouput a path in a maze */

package applications;

import dataStructures.*;
import utilities.*;

public class RatInAMaze
{
   // top-level nested class
   private static class Position
   {
      // data members
      private int row;   // row number of the position
      private int col;   // column number of the position

      // constructor
      private Position(int theRow, int theCol)
      {
         row = theRow;
         col = theCol;
      }

      // convert to string suitable for output
      public String toString()
      {
         return new String(row + " " + col);
      }
   }
   
   // data members
   private static byte [][] maze;
   private static int size;   // number of rows and columns in the maze
   private static ArrayStack path;  // path to current position
   
   // methods
   /* not yet implemented */
   private static void welcome() {}
   
   /** input the maze */
   private static void inputMaze()
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter maze size");
      size = keyboard.readInteger();

      // create and input the maze array
      maze = new byte [size + 2][size + 2];
      System.out.println("Enter the maze in row-major order");
      for (int i = 1; i <= size; i++)
         for (int j = 1; j <= size; j++)
             maze[i][j] = keyboard.readByte();
   }
   
   /** find a path from (1,1) to the exit (size, size)
     * @return true if successful, false if impossible */
   private static boolean findPath()
   {
      path = new ArrayStack();
   
      // initialize offsets
      Position [] offset = new Position [4];
      offset[0] = new Position(0, 1);   // right
      offset[1] = new Position(1, 0);   // down
      offset[2] = new Position(0, -1);  // left
      offset[3] = new Position(-1, 0);  // up
      
      // initialize wall of obstacles around maze
      for (int i = 0; i <= size + 1; i++)
      {
         maze[0][i] = maze[size + 1][i] = 1; // bottom and top
         maze[i][0] = maze[i][size + 1] = 1; // left and right
      }
   
      Position here = new Position(1, 1);
      maze[1][1] = 1; // prevent return to entrance
      int option = 0; // next move
      int lastOption = 3;
      
      // search for a path
      while (here.row != size || here.col != size)
      {// not at exit
          // find a neighbor to move to
          // won't compile without explicit initialization
          int r = 0, c = 0;   // row and column of neighbor
          while (option <= lastOption)
          {
             r = here.row + offset[option].row;
             c = here.col + offset[option].col;
             if (maze[r][c] == 0) break;
             option++; // next option
          }
    
          // was a neighbor found?
          if (option <= lastOption)  // yes
          {// move to maze[r][c]
              path.push(here);
              here = new Position(r, c);
              // set to 1 to prevent revisit
              maze[r][c] = 1;
              option = 0;
          }
          else
          {// no neighbor to move to, back up
              if (path.empty()) return false;  // no place to back up to
              Position next = (Position) path.pop();
              if (next.row == here.row)
                 option = 2 + next.col - here.col;
              else option = 3 + next.row - here.row;
              here = next;
          }
      }
   
      return true;  // at exit
   }
   
   /** output path to exit */
   private static void outputPath()
   {
      System.out.println("The path is");
      while (!path.empty())
         System.out.println(path.pop());
   }
   
   /** entry point for rat in a maze program */
   public static void main(String [] args)
   {
      welcome();
      inputMaze();
      if (findPath()) outputPath();
      else System.out.println("No path");
   }
}

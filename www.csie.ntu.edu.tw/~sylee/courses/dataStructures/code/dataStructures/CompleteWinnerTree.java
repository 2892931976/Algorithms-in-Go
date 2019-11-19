
/** array-based winner trees using complete binary trees */

package dataStructures;

import utilities.*;
import exceptions.*;

public class CompleteWinnerTree implements WinnerTree
{
   // data members
   int lowExt;           // lowest-level external nodes
   int offset;           // 2^log(n-1) - 1
   int [] tree;          // array for winner tree
   Playable [] player;   // array of players

   // only default constructor available

   /** @return the winner of the tournament
     * @return 0 if there are no players */
   public int getWinner()
      {return (tree == null) ? 0 : tree[1];}

   /** initialize winner tree for thePlayer[1:thePlayer.length-1]
     * @throws IllegalArgumentException when the
     * number of players is less than 2 */
   public void initialize(Playable [] thePlayer)
   {
      int n = thePlayer.length - 1;
      if (n < 2)
         throw new IllegalArgumentException
                   ("must have at least 2 players");

      player = thePlayer;
      tree = new int [n];
   
      // compute  s = 2^log (n-1)
      int i, s;
      for (s = 1; 2 * s <= n - 1; s += s);
   
      lowExt = 2 * (n - s);
      offset = 2 * s - 1;
   
      // play matches for lowest-level external nodes
      for (i = 2; i <= lowExt; i += 2)
         play((offset + i) / 2, i - 1, i);
   
      // handle remaining external nodes
      if (n % 2 == 1)
      {// special case for odd n, play internal and exteral node
         play(n/2, tree[n - 1], lowExt + 1);
         i = lowExt + 3;
      }
      else i = lowExt + 2;
   
      // i is left-most remaining external node
      for (; i <= n; i += 2)
         play((i - lowExt + n - 1) / 2, i - 1, i);
   }
   
   /** play matches beginning at tree[p]
     * @param leftChild is left child of p
     * @param rightChild is right child of p */
   void play(int p, int leftChild, int rightChild)
   {
      tree[p] = player[leftChild].winnerOf(player[rightChild]) ?
                      leftChild : rightChild;
   
      // more matches possible if at right child
      while (p % 2 == 1 && p > 1)
      {// at a right child
         tree[p / 2] = player[tree[p - 1]].winnerOf(player[tree[p]]) ?
                          tree[p - 1] : tree[p];
         p /= 2;  // go to parent
      }
   }
   
   /** replay matches for player thePlayer
     * @throws IndexOutOfBoundsException when
     * there is no player with index thePlayer */
   public void rePlay(int thePlayer)
   {
      int n = player.length - 1;  // number of players
      if (thePlayer <= 0 || thePlayer > n)
         throw new IndexOutOfBoundsException
                   ("no player " + thePlayer);
   
      int matchNode,       // node where next match is to be played
          leftChild,       // left child of matchNode
          rightChild;      // right child of matchNode
   
      // find first match node and its children
      if (thePlayer <= lowExt)
      {// begin at lowest level
         matchNode = (offset + thePlayer) / 2;
         leftChild = 2 * matchNode - offset;
         rightChild = leftChild + 1;
      }
      else
      {
         matchNode = (thePlayer - lowExt + n - 1) / 2;
         if (2 * matchNode == n - 1)
         {
            leftChild = tree[2 * matchNode];
            rightChild = thePlayer;
         }
         else
         {
            leftChild = 2 * matchNode - n + 1 + lowExt;
            rightChild = leftChild + 1;
         }
      }
   
      tree[matchNode] = player[leftChild].winnerOf(player[rightChild])
                               ? leftChild : rightChild;

      // special case for second match
      if (matchNode == n - 1 && n % 2 == 1)
      {
         matchNode /= 2;   // move to parent
         tree[matchNode] = player[tree[n - 1]].winnerOf
                           (player[lowExt + 1]) ?
                           tree[n - 1] : lowExt + 1;
      }
   
      // play remaining matches
      matchNode /= 2;  // move to parent
      for (; matchNode >= 1; matchNode /= 2)
         tree[matchNode] = player[tree[2 * matchNode]].winnerOf
                           (player[tree[2 * matchNode + 1]]) ?
                           tree[2 * matchNode] : tree[2 * matchNode + 1];
   }
   
   public void output()
   {
      int n = player.length - 1;
      System.out.println("size = " + n  + " lowExt = "  + lowExt
                          + " offset = "  + offset); 
      System.out.println("Winner tree pointers are");
      for (int i = 1; i < n; i++)
         System.out.print(tree[i] + " ");
      System.out.println();
   }

   /** test program */
   public static void main(String [] args)
   {

      // define a MyInputStream object to input from System.in
      MyInputStream keyboard = new MyInputStream();
   
      // get data from System.in
      System.out.println("Enter number of players");
      int n = keyboard.readInteger();
      if (n < 2)
         throw new MyInputException
                   ("must have >= 2 players");

      Player [] thePlayer = new Player [n + 1];

      System.out.println("Enter player values");
      for (int i = 1; i <= n; i++)
         thePlayer[i] = new Player(i, keyboard.readInteger());

      CompleteWinnerTree w = new CompleteWinnerTree();
      w.initialize(thePlayer);

      System.out.println("The winner tree is");
      w.output();

      // change player 2's value
      thePlayer[2].value = 0;
      w.rePlay(2);
      System.out.println("Changed player 2 to zero, new tree is");
      w.output();

      // change player 3's value
      thePlayer[3].value = -1;
      w.rePlay(3);
      System.out.println("Changed player 3 to -1, new tree is");
      w.output();
 
      // change player 7's value
      thePlayer[7].value = 2;
      w.rePlay(7);
      System.out.println("Changed player 7 to 2, new tree is");
      w.output();
   }
}

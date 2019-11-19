

/** extended formula based winner trees using complete binary trees */

package dataStructures;

public class ExtendedCWTree extends CompleteWinnerTree
{
   /** @return the winner recorded in tree[i]
     * @return 0 if i >= number of players */
   public int getWinner(int i)
      {return (i < tree.length) ? tree[i] : 0;}
}

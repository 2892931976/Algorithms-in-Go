

/** indexed binary search tree */

package dataStructures;

public interface IndexedBSTree extends BSTree
{
   public Object get(int index);
   public Object remove(int index);
}

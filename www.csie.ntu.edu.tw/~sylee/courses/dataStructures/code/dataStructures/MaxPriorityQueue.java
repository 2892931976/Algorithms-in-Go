
package dataStructures;

public interface MaxPriorityQueue
{
   public boolean isEmpty();
   public int size();
   public Comparable getMax();
   public void put(Comparable theObject);
   public Comparable removeMax();
}

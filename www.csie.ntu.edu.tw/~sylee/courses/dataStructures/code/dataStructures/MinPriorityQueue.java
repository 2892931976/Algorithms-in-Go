
package dataStructures;

public interface MinPriorityQueue
{
   public boolean isEmpty();
   public int size();
   public Comparable getMin();
   public void put(Comparable theObject);
   public Comparable removeMin();
}

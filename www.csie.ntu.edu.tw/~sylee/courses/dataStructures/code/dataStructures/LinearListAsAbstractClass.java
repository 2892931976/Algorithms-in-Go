
/** abstract class for linear lists */

package dataStructures;

public abstract class LinearListAsAbstractClass
{
   public abstract boolean isEmpty();
   public abstract int size();
   public abstract Object get(int index);
   public abstract int indexOf(Object theElement);
   public abstract Object remove(int index);
   public abstract void add(int index, Object theObject);
   public abstract String toString();
}

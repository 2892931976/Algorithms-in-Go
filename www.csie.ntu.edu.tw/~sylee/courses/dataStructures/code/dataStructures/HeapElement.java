/** node structure used by heaps */

package dataStructures;

public class HeapElement
{
   // data members
   Comparable priority;   // element's priority
   Object element;

   // constructor
   public HeapElement(Object thePriority, Object theElement)
   {
      priority = (Comparable) thePriority;
      element = theElement;
   }
}

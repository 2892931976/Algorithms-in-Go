
/** Chain extended to include a method to remove by matching vertex field */

/** this class is for use by the linked graph classes */

package dataStructures;

public class GraphChain extends Chain
{
   /** delete element with element.vertex = theVertex
     * @return deleted element */
   public Object removeElement(int theVertex)
   {
      ChainNode current = firstNode,
                trail = null; // one behind current
   
      // search for match
      while (current != null &&
             ((EdgeNode) current.element).vertex != theVertex)
      {
         trail = current;
         current = current.next;
      }

      if (current == null) // no match
         return null;
   
      // match found in node current
      Object theElement = current.element; // save matching element
   
      // remove current from chain
      if (trail != null)
         trail.next = current.next;
      else
         firstNode = current.next; // current is first node
   
      size--;
      return theElement;
   }
}

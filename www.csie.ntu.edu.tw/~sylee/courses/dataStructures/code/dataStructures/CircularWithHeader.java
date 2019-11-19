


/** singly linked circular list with header node */

package dataStructures;

public class CircularWithHeader
{
   // data member
   protected ChainNode headerNode;

   // constructor
   /** create a circular list that is empty */
   public CircularWithHeader()
   {
       headerNode = new ChainNode();
       headerNode.next = headerNode;
   }

   // methods
   /** @return index of first occurrence of theElement,
     * return -1 if theElement not in list */
   public int indexOf(Object theElement)
   {
      // put theElement in header node
      headerNode.element = theElement;

      // search the list for theElement
      ChainNode currentNode = headerNode.next;
      int index = 0;  // index of currentNode
      while (!currentNode.element.equals(theElement))
      {
         // move to next node
         currentNode = currentNode.next;
         index++;
      }

      // make sure we found matching element
      if (currentNode == headerNode)
         return -1;
      else
         return index;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // create a circluar list
      CircularWithHeader x = new CircularWithHeader();
      for (int i = 0; i <= 4; i++)
      {   // insert i at front
          ChainNode y = new ChainNode(new Integer(i),
                            x.headerNode.next);
          x.headerNode.next = y;
      }

      // search
      for (int i = 0; i <=5; i++)
          System.out.println(i + " is element " +
                 x.indexOf(new Integer(i)));
   }
}

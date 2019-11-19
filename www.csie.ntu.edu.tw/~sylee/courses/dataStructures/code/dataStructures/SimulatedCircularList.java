
/** linked implementation of a circular list deallocate
  * using simulated pointers */

package dataStructures;

import java.util.*;

public class SimulatedCircularList
{
   // data members
   protected int firstNode;
   protected int size;
   public static SimulatedSpace1 S = new SimulatedSpace1(10);

   // constructor
   /** create a list that is empty */
   public SimulatedCircularList(int initialCapacity)
   {
       firstNode = -1;
       // size has the default initial value 0
   }

   public void deallocateCircular(SimulatedCircularList theList)
   {// Deallocate all nodes in the circular list theList.
      if (theList.firstNode != -1)
      {// theList is not empty
         int secondNode = S.node[theList.firstNode].next;
         S.node[theList.firstNode].next = firstNode;
         firstNode = secondNode;
         theList.firstNode = -1;  // theList is now empty
      }
   }
}

/** Node class used by simulated pointer structures.
  * This class and its data members are
  * visible only within the package dataStructures. */

package dataStructures;


class SimulatedNode
{
   // package visible data members
   Object element;
   int next;

   // package visible constructors
   SimulatedNode() {}

   SimulatedNode(int next)
      {this.next = next;}
}


/** memory management for simulated pointer classes */

package dataStructures;

import utilities.*;

public class SimulatedSpace1
{
   // data members
   private int firstNode;
   SimulatedNode [] node;  // package visible

   // constructor
   public SimulatedSpace1(int numberOfNodes)
   {
      node = new SimulatedNode [numberOfNodes];

      // create nodes and link into a chain
      for (int i = 0; i < numberOfNodes - 1; i++)
         node[i] = new SimulatedNode(i + 1);

      // last node of array and chain
      node[numberOfNodes - 1] = new SimulatedNode(-1);
      // firstNode has the default initial value 0
   }
   
   public int allocateNode(Object element, int next)
   {// Allocate a free node and set its fields.
      if (firstNode == -1)
      {   // double number of nodes
          node = (SimulatedNode []) ChangeArrayLength.
                 changeLength1D(node, 2 * node.length);

          // create and link new nodes
          for (int i = node.length / 2;
               i < node.length - 1; i++)
             node[i] = new SimulatedNode(i + 1);
          node[node.length - 1] = new SimulatedNode(-1);

          firstNode = node.length / 2;
      }

      int i = firstNode;         // allocate first node
      firstNode = node[i].next;  // firstNode points to
                                 // next free node
      node[i].element = element; 
      node[i].next = next;
      return i;
   }
   
   public void deallocateNode(int i)
   {// Free node i.
      // make i first node on free space list
      node[i].next = firstNode;
      firstNode = i;

      // remove element reference so that space can be garbage collected
      node[i].element = null;
   }

   /** test program */
   public static void main(String [] args)
   {
      int x;
      // create a simulated space with 5 nodes
      SimulatedSpace1 L = new SimulatedSpace1(5);
      x = L.allocateNode(null, 0);
      System.out.println("Node allocated is " + x);
      int y = L.allocateNode(null, 0);
      System.out.println("Node allocated is " + y);
      L.deallocateNode(x);
      y = L.allocateNode(null, 0);
      System.out.println("Node allocated is " + y);
   }
}

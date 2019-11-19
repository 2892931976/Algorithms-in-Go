

/** Memory management for simulated
  * pointer classes. Dual available space lists. */

package dataStructures;

import utilities.*;

public class SimulatedSpace2
{
   // data members
   private int first1;
   private int first2;
   SimulatedNode [] node;  // package visible

   // constructor
   public SimulatedSpace2(int numberOfNodes)
   {
      node = new SimulatedNode [numberOfNodes];
      // first1 has the default initial value 0
      first2 = -1;
   }
   
   public int allocateNode(Object element, int next)
   {// Allocate a free node and set its fields.
      if (first2 == -1)
      {   // 2nd list is empty
          if (first1 == node.length)
             // double number of nodes
             node = (SimulatedNode []) ChangeArrayLength.
                    changeLength1D(node, 2 * node.length);
          // create and allocate node
          node[first1] = new SimulatedNode();
          node[first1].element = element;
          node[first1].next = next;
          return first1++;
      }

      // allocate first node of chain
      int i = first2;
      first2 = node[i].next;
      node[i].element = element;
      node[i].next = next;
      return i;
   }

   public void deallocateNode(int i)
   {// Free node i.
      // make i first node on chain
      node[i].next = first2;
      // remove element reference so that space can be garbage collected
      node[i].element = null;
      first2 = i;
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

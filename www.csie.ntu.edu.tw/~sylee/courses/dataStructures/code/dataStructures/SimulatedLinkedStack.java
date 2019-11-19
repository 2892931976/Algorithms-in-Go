
/** a linked stack class using simulated pointers */

package dataStructures;

public class SimulatedLinkedStack implements Stack
{
   // data members
   protected int topNode;
   public static SimulatedSpace1 S;

   // constructors
   /** create an empty stack */
   public SimulatedLinkedStack(int initialCapacity)
       {topNode = -1;}

   public SimulatedLinkedStack()
      {this(0);}

   // methods
   /** @return true iff stack is empty */
   public boolean empty()
      {return topNode == -1;}
  


   /** @return top element of stack */
   public Object peek()
   {
      if (empty())
         throw new IllegalArgumentException
            ("stack is empty");
      return S.node[topNode].element;
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {topNode = S.allocateNode(theElement, topNode);}

   /** remove top element of stack and return it */
   public Object pop()
   {
      if (empty())
         throw new IllegalArgumentException
            ("SimulatedLinkedStack.pop: stack is empty");
      Object topElement = S.node[topNode].element;
      int oldTop = topNode;
      topNode = S.node[topNode].next;
      S.deallocateNode(oldTop);
      return topElement;
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      // create simulated space with 100 nodes
      SimulatedLinkedStack.S = new SimulatedSpace2(100);

      int x;
      SimulatedLinkedStack s = new SimulatedLinkedStack();
      // add a few elements
      s.push(new Integer(1));
      s.push(new Integer(2));
      s.push(new Integer(3));
      s.push(new Integer(4));


      // delete all elements
      while (!s.empty())
      {
         System.out.println("Top element is " + s.peek());
         System.out.println("Removed the element " + s.pop());
      }
   }  
}

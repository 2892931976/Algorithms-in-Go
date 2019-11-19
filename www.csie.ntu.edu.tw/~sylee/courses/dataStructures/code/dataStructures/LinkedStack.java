
/** a linked stack class */

package dataStructures;

import java.util.EmptyStackException;

public class LinkedStack implements Stack
{
   // data members
   protected ChainNode topNode;

   // constructors
   /** create an empty stack */
   public LinkedStack(int initialCapacity)
   {
       // the default initial value of topNode is null
   }

   public LinkedStack()
      {this(0);}

   // methods
   /** @return true iff stack is empty */
   public boolean empty()
      {return topNode == null;}


   /** @return top element of stack 
     * @throws EmptyStackException when the stack is empty */
   public Object peek()
   {
      if (empty())
         throw new EmptyStackException();
      return topNode.element;
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {topNode = new ChainNode(theElement, topNode);}

   /** remove top element of stack and return it
     * @throws EmptyStackException when the stack is empty */
   public Object pop()
   {
      if (empty())
         throw new EmptyStackException();
      Object topElement = topNode.element;
      topNode = topNode.next;
      return topElement;
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      LinkedStack s = new LinkedStack();
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


/** a stack class derived from Chain */

package dataStructures;

import java.util.*;

public class DerivedLinkedStack extends Chain
                                implements Stack
{
   // constructors
   /** included only for compatibility with other stack classes */
   public DerivedLinkedStack(int initialCapacity)
      {super();}

   public DerivedLinkedStack()
      {this(0);}

   // methods
   /** @return true iff stack is empty */
   public boolean empty()
      {return isEmpty();}


   /** @return top element of stack
     * @throws EmptyStackException when the stack is empty */
   public Object peek()
   {
      if (empty())
         throw new EmptyStackException();
      return get(0);
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {add(0, theElement);}

   /** remove top element of stack and return it
     * @throws EmptyStackException when the stack is empty */
   public Object pop()
   {
      if (empty())
         throw new EmptyStackException();

      // remove and return top element
      return remove(0);
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      int x;
      DerivedLinkedStack s = new DerivedLinkedStack(3);
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

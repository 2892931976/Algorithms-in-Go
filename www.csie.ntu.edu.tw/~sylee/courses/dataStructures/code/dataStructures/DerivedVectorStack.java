
/** a stack class derived from Vector */

package dataStructures;

import java.util.*;

public class DerivedVectorStack extends Vector
                                implements Stack
{
   // constructors
   /** create a stack with the given initial capacity */
   public DerivedVectorStack(int initialCapacity)
      {super(initialCapacity);}

   /** create a stack with initial capacity 10 */
   public DerivedVectorStack()
   {// use default capacity of 10
      this(10);
   }

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
      return get(size() - 1);
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {add(size(), theElement);}

   /** remove top element of stack and return it
     * @throws EmptyStackException when the stack is empty */
   public Object pop()
   {
      if (empty())
         throw new EmptyStackException();
      return remove(size() - 1);
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      int x;
      DerivedVectorStack s = new DerivedVectorStack(3);
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

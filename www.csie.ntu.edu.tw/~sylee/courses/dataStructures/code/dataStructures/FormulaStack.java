
/** a stack class derived from FormulaBasedLinearList */

package dataStructures;

import java.util.*;
import wrappers.*;
import utilities.*;

public class FormulaStack extends FormulaBasedLinearList
                          implements Stack
{
   // constructors
   /** create a stack with the given initial capacity */
   public FormulaStack(int initialCapacity)
      {super(initialCapacity);}

   /** create a stack with initial capacity 10 */
   public FormulaStack()
      {this(10);}

   // methods
   /** @return true iff stack is empty */
   public boolean empty()
       {return isEmpty();}


   /** @return top element of stack */
   public Object peek()
   {
      if (empty())
         throw new IllegalArgumentException
            ("FormulaStack.peek: stack is empty");
      return elementAt(size() - 1);
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {insertElementAt(theElement, size());}

   /** remove top element of stack and return it */
   public Object pop()
   {
      if (empty())
         throw new IllegalArgumentException
            ("FormulaStack.pop: stack is empty");
      Object topElement = elementAt(size() - 1);
      removeElementAt(size() - 1);
      return topElement;
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      int x;
      FormulaStack s = new FormulaStack(3);
      // add a few elements
      s.push(new MyInteger(1));
      s.push(new MyInteger(2));
      s.push(new MyInteger(3));
      s.push(new MyInteger(4));


      // delete all elements
      while (!s.empty())
      {
         System.out.println("Top element is " + s.peek());
         System.out.println("Removed the element " + s.pop());
      }
   }  
}

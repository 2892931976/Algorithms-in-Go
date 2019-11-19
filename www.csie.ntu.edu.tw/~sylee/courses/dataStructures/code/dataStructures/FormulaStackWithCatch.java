
/** a stack class derived from FormulaBasedLinearList */
/** this implementation catches exceptions thrown by
  * FormulaBasedLinearList */

package dataStructures;

import java.util.*;
import wrappers.*;
import utilities.*;

public class FormulaStackWithCatch extends FormulaBasedLinearList
                          implements Stack
{
   // constructors
   /** create a stack with the given initial capacity */
   public FormulaStackWithCatch(int initialCapacity)
      {super(initialCapacity);}

   /** create a stack with initial capacity 10 */
   public FormulaStackWithCatch()
      {this(10);}

   // methods
   /** @return true iff stack is empty */
   public boolean empty()
       {return isEmpty();}


   /** @return top element of stack */
   public Object peek()
   {
      try {return elementAt(size() - 1);}
      catch (IllegalArgumentException e)
      {
         throw new IllegalArgumentException
            ("FormulaStackWithCatch.peek: stack is empty");
      }
   }

   /** add theElement to the top of the stack */
   public void push(Object theElement)
      {insertElementAt(theElement, size());}

   /** remove top element of stack and return it */
   public Object pop()
   {
      try
      {
         Object topElement = elementAt(size() - 1);
         removeElementAt(size() - 1);
         return topElement;
      }
      catch (IllegalArgumentException e)
      {
         throw new IllegalArgumentException
            ("FormulaStackWithCatch.pop: stack is empty");
      }
   }
   
   /** test program */
   public static void main(String [] args)
   {  
      int x;
      FormulaStackWithCatch s = new FormulaStackWithCatch(3);
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


/** using a Vector */

package misc;

import java.util.*;
import wrappers.*;

public class UsingAVector
{
   public static void main(String [] args)
   {
      // define a vector with initial capacity 1
      Vector v = new Vector(1);
      System.out.println("The initial capacity is "
                         + v.capacity());
      System.out.println("The initial size is "
                         + v.size());

      // add some elements
      v.addElement(new MyInteger(1));
      v.addElement(new MyInteger(2));
      v.insertElementAt(new MyInteger(3), 2);
      v.insertElementAt(new MyInteger(4), 3);
      v.insertElementAt(new MyInteger(5), 4);
      System.out.println("The new capacity is "
                         + v.capacity());
      System.out.println("The new size is "
                         + v.size());
      System.out.println("The vector is " + v);

      // check out the iterator
      Enumeration e = v.elements();
      while (e.hasMoreElements())
         System.out.print(e.nextElement() + " ");
      System.out.println();

     // make a clone
     Vector w = (Vector) v.clone();
     // empty v
     v.removeAllElements();
     // output w
     System.out.println("The vector is " + w);
   }
}


/** extension of ArrayList */

package dataStructures;

import java.util.*;
import utilities.*;

public class ExtendedArrayList extends ArrayList
{
   // constructors
   public ExtendedArrayList(int initialCapacity)
      {super(initialCapacity);}

   public ExtendedArrayList()
   {// use default capacity of 10
      this(10);
   }

   /** set list size to newSize */
   public void setSize(int newSize)
   {
      int currentSize = size();
      if (currentSize < newSize)
      {
            for (int i = currentSize; i < newSize; i++)
               add(null);
      }
      else
      {
            for (int i = currentSize; i > newSize; i--)
               remove(i - 1);
      }
   }
}

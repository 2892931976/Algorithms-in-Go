
/** weigted edge */

package dataStructures;

import utilities.*;

public class WeightedEdge extends Edge
{
   // data member
   Object weight;

   // constructor
   public WeightedEdge(int theVertex1, int theVertex2, Object theWeight)
   {
      super(theVertex1, theVertex2);
      weight = theWeight;
   }

   // convert to string suitable for output
   public String toString()
   {
      return "(" + String.valueOf(vertex1) 
               + ", " + String.valueOf(vertex2)
               + ", " + weight.toString() + ")";
   }
}

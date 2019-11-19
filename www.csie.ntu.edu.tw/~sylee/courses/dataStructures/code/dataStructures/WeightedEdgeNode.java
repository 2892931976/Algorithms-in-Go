
/** weigted edge node */

package dataStructures;

import utilities.*;

public class WeightedEdgeNode extends EdgeNode
{
   // data member
   Object weight;

   // constructor
   public WeightedEdgeNode(int theVertex, Object theWeight)
   {
      super(theVertex);
      weight = theWeight;
   }

   // convert to a String
   public String toString()
      {return String.valueOf(vertex) + " " + weight.toString();}
}

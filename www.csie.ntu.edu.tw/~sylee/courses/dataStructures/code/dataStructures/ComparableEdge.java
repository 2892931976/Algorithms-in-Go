
/** weigted edge for with a weight that can be compared */

package dataStructures;

import utilities.*;

public class ComparableEdge extends Edge
                            implements Comparable
{
   // data member
   Comparable weight;

   // constructor
   public ComparableEdge(int theVertex1, int theVertex2, Comparable theWeight)
   {
      super(theVertex1, theVertex2);
      weight = theWeight;
   }

   /** @return -1 if this < x,
     *          0 if this == x,
     *          1 if this > x */
   public int compareTo(Object x)
      {return weight.compareTo(((ComparableEdge) x).weight);}

   /** @return true iff this == x */
   public boolean equals(Object x)
      {return weight.equals(((ComparableEdge) x).weight);}
}

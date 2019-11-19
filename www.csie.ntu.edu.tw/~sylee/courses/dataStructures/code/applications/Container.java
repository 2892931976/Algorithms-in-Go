/** data type for a container */

package applications;

public class Container implements Comparable
{
   // data members
   int id;       // container id
   int weight;   // container weight
 
   // constructor
   public Container(int theID, int theWeight)
   {
      id = theID;
      weight = theWeight;
   }

   public int compareTo(Object x)
   {
      int xWeight = ((Container) x).weight;
      if (weight < xWeight)
         return -1;
      else if (weight == xWeight)
              return 0;
           else return 1;
   }
}

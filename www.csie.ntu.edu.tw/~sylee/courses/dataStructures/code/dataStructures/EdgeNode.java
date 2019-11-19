
/** unweigted edge node */

package dataStructures;

public class EdgeNode
{
   // data member
   int vertex;  // only one end point need be stored

   // constructor
   public EdgeNode(int theVertex)
      {vertex = theVertex;}

   // test for equality
   public boolean equals(Object x)
      {return vertex == ((EdgeNode) x).vertex;}

   public String toString()
      {return String.valueOf(vertex);}
}

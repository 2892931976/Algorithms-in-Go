
/** unweigted edge */

package dataStructures;

public class Edge
{
   // data member
   int vertex1;  // one end point of the edge
   int vertex2;  // other end point of the edge

   // constructor
   public Edge(int theVertex1, int theVertex2)
   {
      vertex1 = theVertex1;
      vertex2 = theVertex2;
   }

   // convert to string suitable for output
   public String toString()
   {
      return "(" + String.valueOf(vertex1) 
               + "," + String.valueOf(vertex2) + ")";
   }
}

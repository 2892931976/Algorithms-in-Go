/** node structure used by second solution to the union/find problem */

package applications;

class EquivNode
{
   int equivClass;   // element class identifier
   int size;         // size of class
   int next;         // pointer to next element in class

   /** constructor */
   EquivNode(int theClass, int theSize)
   {
      equivClass = theClass;
      size = theSize;
      // next has the default value 0
   }
}

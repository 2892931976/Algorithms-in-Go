/** union/find using chains and size info
  * simulated pointers used */


package applications;

public class UnionFindSecondSolution
{
   static EquivNode [] node; // array of nodes
   static int n;             // number of elements
   
   /** initialize numberOfElements classes with one element each */
   static void initialize(int numberOfElements)
   {
      n = numberOfElements;
      node = new EquivNode [n + 1];
      
      for (int e = 1; e <= n; e++)
         // node[e] is initialized so that its equivClass is e,
         // size is 1, and next is 0
         node[e] = new EquivNode(e,1);
   }
   
   /** unite the classes classA and classB */
   static void union(int classA, int classB)
   {// assume classA != classB
      // make classA smaller class
      if (node[classA].size > node[classB].size)
      {// swap classA and classB
         int t = classA;
         classA = classB;
         classB = t;
      }
   
      //  change equivClass values of smaller class
      int k;
      for (k = classA; node[k].next != 0; k = node[k].next)
         node[k].equivClass = classB;
      node[k].equivClass = classB; // last node in chain
   
      // insert chain classA after first node in chain classB
      // and update new chain size
      node[classB].size += node[classA].size;
      node[k].next = node[classB].next;
      node[classB].next = classA;
   }
   
   /** find the class that contains theElement */
   static int find(int theElement)
      {return node[theElement].equivClass;}

   /** test program */
   public static void main(String [] args)
   {
      initialize(10);
      union(1,2);
      union(3,4);
      union(2,4);
      for (int i = 1; i < 7; i++)
         System.out.println("Element " + i + " is in equivalence class " +
                             find(i));
   }
}

/** simple union/find using a 1D array */

package applications;

public class UnionFindFirstSolution
{
   static int [] equivClass;
   static int n;  // number of elements
   
   /** initialize numberOfElements classes with one element each */
   static void initialize(int numberOfElements)
   {
      n = numberOfElements;
      equivClass = new int [n + 1];
      for (int e = 1; e <= n; e++)
         equivClass[e] = e;
   }
   
   /** unite the classes classA and classB */
   static void union(int classA, int classB)
   {// assume classA != classB
      for (int k = 1; k <= n; k++)
         if (equivClass[k] == classB)
            equivClass[k] = classA;
   }
   
   /** find the class that contains theElement */
   static int find(int theElement)
      {return equivClass[theElement];}

   /** test program */
   public static void main(String [] args)
   {
      initialize(10);
      union(1,2);
      union(3,4);
      union(1,3);
      for (int i = 1; i < 7; i++)
         System.out.println("Element " + i + " is in equivalence class " +
                             find(i));
   }
}


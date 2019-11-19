/** union-find using trees, simple version */

package applications;

public class UnionFindWithTrees
{
   int [] parent;   // pointer to parent in tree

   /** initialize n trees, one element per tree/class/set */
   public UnionFindWithTrees(int n)
   {
      parent = new int [n + 1];
      for (int e = 1; e <= n; e++)
         parent[e] = 0;
   }
   
   /** @return root of the tree that contains theElement */
   public int find(int theElement)
   {
      while (parent[theElement] != 0)
         theElement = parent[theElement];  // move up one level
      return theElement;
   }
   
   /** combine trees with distinct roots rootA and rootB */
   public void union(int rootA, int rootB)
      {parent[rootB] = rootA;}
   
   /** test program */
   public static void main(String [] args)
   {
      UnionFindWithTrees x = new UnionFindWithTrees(10);
      x.union(1, 2);
      x.union(3, 4);
      x.union(1, 3);
      System.out.println("find(1) = " + x.find(1) +
                         " find(2) = " + x.find(2));
      System.out.println("find(3) = " + x.find(3) +
                         " find(4) = " + x.find(4));
      System.out.println("find(5) = " + x.find(5) +
                         " find(6) = " + x.find(6));
   }
}

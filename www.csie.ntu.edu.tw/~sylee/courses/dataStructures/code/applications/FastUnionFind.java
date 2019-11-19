/** union/find with weighting and path compaction */

package applications;

public class FastUnionFind
{
   // top-level nested class
   private static class Node
   {
      // data members
      int parent;     // pointer to parent in tree
      boolean root;   // true iff node is a root
   
      // constructor
      private Node()
      {
         parent = 1;    // one node in tree
         root = true;   // this node is a root
      }
   }

   // data member of FastUnionFind
   Node [] node;

   /** initialize n trees, one element per tree/class/set */
   public FastUnionFind(int n)
   {
      // allocate an array for the nodes
      node = new Node [n + 1];

      // now allocate the nodes
      for (int e = 1; e <= n; e++)
         node[e] = new Node();
   }
   
   /** @return root of the tree that contains theElement */
   public int find(int theElement)
   {
      // theRoot will eventually be the root
      int theRoot = theElement;
      while (!node[theRoot].root)
         theRoot = node[theRoot].parent;
      
      // compact path from theElement to theRoot
      int currentNode = theElement;           // start at theElement
      while (currentNode != theRoot)
      {
         int parentNode = node[currentNode].parent;
         node[currentNode].parent = theRoot;  // move to level 2
         currentNode = parentNode;            // move to old parent 
      }
   
      return theRoot;
   }
   
   /** combine trees with distinct roots rootA and rootB */
   public void union(int rootA, int rootB)
   {// use the weighting rule
      if (node[rootA].parent < node[rootB].parent)
      {
         // rootA becomes subtree of rootB
         node[rootB].parent += node[rootA].parent;
         node[rootA].root = false;
         node[rootA].parent = rootB;
      }
      else
      {// rootB becomes subtree of rootA
         node[rootA].parent += node[rootB].parent;
         node[rootB].root = false;
         node[rootB].parent = rootA;
      }
   }

   /** test program */
   public static void main(String [] args)
   {
      FastUnionFind x = new FastUnionFind(10);
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

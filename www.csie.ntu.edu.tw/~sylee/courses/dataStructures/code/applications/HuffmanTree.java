

/** compute a Huffman binary tree */

package applications;

import dataStructures.*;
import utilities.*;
import wrappers.*;

public class HuffmanTree
{
   // top-level nested class
   private static class HuffmanNode implements Comparable
   {
      LinkedBinaryTree tree;   // a Huffman tree
      Operable weight;         // weight of the Huffman tree

      // constructor
      private HuffmanNode(LinkedBinaryTree theTree,
                          Operable theWeight)
      {
         tree = theTree;
         weight = theWeight;
      } 

      public int compareTo(Object x)
         {return weight.compareTo(((HuffmanNode) x).weight);}
   }
   
   /** @return Huffman tree with weights w[0:a.length-1] */
   public static LinkedBinaryTree huffmanTree(Operable [] w)
   {
      // create an array of single-node trees
      HuffmanNode [] hNode = new HuffmanNode [w.length + 1];
      LinkedBinaryTree emptyTree = new LinkedBinaryTree();
      for (int i = 0; i < w.length; i++)
      {
         LinkedBinaryTree x = new LinkedBinaryTree();
         x.makeTree(new MyInteger(i), emptyTree, emptyTree);
         hNode[i + 1] = new HuffmanNode(x, w[i]);
      }
   
      // make node array into a min heap
      MinHeap h = new MinHeap();
      h.initialize(hNode, w.length);
   
      // repeatedly combine pairs of trees from min heap
      // until only one tree remains
      for (int i = 1; i < w.length; i++)
      {
         // remove two lightest trees from the min heap
         HuffmanNode x = (HuffmanNode) h.removeMin();
         HuffmanNode y = (HuffmanNode) h.removeMin();

         // combine them into a single tree t
         LinkedBinaryTree z = new LinkedBinaryTree();
         z.makeTree(null, x.tree, y.tree);
         HuffmanNode t = new HuffmanNode(z,
                             (Operable) x.weight.add(y.weight));

         // put new tree into the min heap
         h.put(t);
      }
   
      return ((HuffmanNode) h.removeMin()).tree;  // final tree
   }
   
   /** test program */
   public static void main(String [] args)
   {
      MyInteger [] a = new MyInteger [10];
      for (int i = 0; i < a.length; i++)
         a[i] = new MyInteger(2 * i + 2);
      huffmanTree(a).postOrderOutput();
   }
}

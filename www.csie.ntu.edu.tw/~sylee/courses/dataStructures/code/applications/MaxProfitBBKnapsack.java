
/** max profit branch-and-bound knapsack */

package applications;

import dataStructures.*;
import utilities.*;

public class MaxProfitBBKnapsack
{
   // top-level classes
   static class BBnode
   {
      // instance data members
      BBnode parent;
      boolean leftChild;    // true iff left child of parent

      // constructor
      BBnode(BBnode theParent, boolean theLeftChild)
      {
         parent = theParent;
         leftChild = theLeftChild;
      }

   }
   
   static class HeapNode implements Comparable
   {
      // class data members
      BBnode liveNode;
      double upperProfit;
      double profit;
      double weight;
      int level;

      // constructor
      HeapNode(BBnode theLiveNode, double theUpperProfit, double theProfit,
               double theWeight, int theLevel)
      {
         liveNode = theLiveNode;
         upperProfit = theUpperProfit;
         profit = theProfit;
         weight = theWeight;
         level = theLevel;
      }

      public int compareTo(Object x)
      {
         double xUpperProfit = ((HeapNode) x).upperProfit;
         if (upperProfit < xUpperProfit) return -1;
         if (upperProfit == xUpperProfit) return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return upperProfit == ((HeapNode) x).upperProfit;}
   }
   
   private static class Element implements Comparable
   {
      // data members
      int id;   // object identifier
      double profitDensity;

      // constructor
      private Element(int theID, double theProfitDensity)
      {
         id = theID;
         profitDensity = theProfitDensity;
      }

      public int compareTo(Object x)
      {
         double xProfitDensity = ((Element) x).profitDensity;
         if (profitDensity < xProfitDensity) return -1;
         if (profitDensity == xProfitDensity) return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return profitDensity == ((Element) x).profitDensity;}
   }

   // class data members
   static double capacity;
   static int numberOfObjects;
   static double [] weight;
   static double [] profit;
   static double weightOfCurrentPacking;
   static double profitFromCurrentPacking;
   static int [] bestPackingSoFar;
   static MaxHeap liveNodeMaxHeap;
   
   /** @param theProfit[1:theProfit.length] is array of object profits
     * @param theWeight[1:theProfit.length] is array of object weights
     * @param theCapacity is knapsack capacity
     * @param bestPacking set to best knapsack filling
     * @return profit of best filling */
   public static double knapsack(double [] theProfit, double [] theWeight,
                                 double theCapacity, int [] bestPacking)
   {
      capacity = theCapacity;
      numberOfObjects = theProfit.length - 1;

      // define an Element array for profit densities
      Element [] q  = new Element [numberOfObjects];
   
      // set up densities in q[0:numberOfObjects-1] and
      // sum the weights and profits
      double weightSum = 0.0;         // will be sum of weights
      double profitSum = 0.0;         // will be sum of profits
      for (int i = 1; i <= numberOfObjects; i++)
      {
         q[i - 1] = new Element(i, theProfit[i] / theWeight[i]);
         profitSum += theProfit[i];
         weightSum += theWeight[i];
      }
   
      if (weightSum <= capacity)   // all objects fit
      {
         for (int i = 1; i <= numberOfObjects; i++)
            bestPacking[i] = 1;
         return profitSum;
      }
   
      // sort into increasing density order
      MergeSort.mergeSort(q);
   
      // initialize class members
      profit = new double [numberOfObjects + 1];
      weight = new double [numberOfObjects + 1];
      for (int i = 1; i <= numberOfObjects; i++)
      {// profits and weights in decreasing density order
         profit[i] = theProfit[q[numberOfObjects - i].id];
         weight[i] = theWeight[q[numberOfObjects - i].id];
      }
      weightOfCurrentPacking = 0.0;
      profitFromCurrentPacking = 0.0;
      bestPackingSoFar = new int [numberOfObjects + 1];
      liveNodeMaxHeap = new MaxHeap();

      // find best profit and construct packing
      double maxProfit = maxProfitBBKnapsack();
      for (int i = 1 ; i <= numberOfObjects; i++)
         bestPacking[q[numberOfObjects - i].id] = bestPackingSoFar[i];
      return maxProfit;
   }
   
   /** max profit branch-and-bound search of solution space tree
     * set bestPackingSoFar[i] = 1 iff object i is in knapsack in best filling
     * @return profit of best knapsack filling */
   private static double maxProfitBBKnapsack()
   {
      // initialize for level 1 start
      BBnode eNode = null;
      int eNodeLevel = 1;
      double maxProfitSoFar = 0.0;
      double maxPossibleProfitInSubtree = profitBound(1);
   
      // search subset space tree
      while (eNodeLevel != numberOfObjects + 1)
      {// not at leaf
         // check left child
         double weightOfLeftChild = weightOfCurrentPacking
                                    + weight[eNodeLevel];
         if (weightOfLeftChild <= capacity)
         {// feasible left child
            if (profitFromCurrentPacking + profit[eNodeLevel]
                > maxProfitSoFar)
               maxProfitSoFar = profitFromCurrentPacking 
                                + profit[eNodeLevel];
            addLiveNode(maxPossibleProfitInSubtree,
                        profitFromCurrentPacking + profit[eNodeLevel],
                        weightOfCurrentPacking + weight[eNodeLevel],
                        eNodeLevel + 1, eNode, true);
         }
         maxPossibleProfitInSubtree = profitBound(eNodeLevel + 1);
   
         // check right child
         if (maxPossibleProfitInSubtree >= maxProfitSoFar)
            // right child has prospects
            addLiveNode(maxPossibleProfitInSubtree,
                        profitFromCurrentPacking,
                        weightOfCurrentPacking,
                        eNodeLevel + 1, eNode, false);
   
         // get next E-node, heap cannot be empty
         HeapNode nextENode = (HeapNode) liveNodeMaxHeap.removeMax();
         eNode = nextENode.liveNode;
         weightOfCurrentPacking = nextENode.weight;
         profitFromCurrentPacking = nextENode.profit;
         maxPossibleProfitInSubtree = nextENode.upperProfit;
         eNodeLevel = nextENode.level;
      }
   
      // construct bestPackingSoFar[] by following path
      // from eNode to the root
      for (int j = numberOfObjects; j > 0; j--)
      {
         bestPackingSoFar[j] = (eNode.leftChild) ? 1 : 0;
         eNode = eNode.parent;
      }
   
      return profitFromCurrentPacking;
   }
   
   /** add a new live node to the max heap
     * also add the new node to the solution space tree
     * @param upperProfit upper bound on profit for the live node
     * @param theProfit profit of new live node
     * @param theWeight weight of new live node
     * @param theLevel level of live node
     * @param theParent parent of new node
     * @param leftChild true iff new node is left child of theParent */
   private static void addLiveNode(double upperProfit, double theProfit,
                       double theWeight, int theLevel, BBnode theParent,
                       boolean leftChild)
   {
      // create the new node of the solution space tree
      BBnode b = new BBnode(theParent, leftChild);

      // create corresponding node for max heap
      HeapNode hNode = new HeapNode(b, upperProfit, theProfit,
                                    theWeight, theLevel);

      // put into max heap
      liveNodeMaxHeap.put(hNode);
   }

   /** bounding function
     * @return upper bound on value of best leaf in subtree */
   private static double profitBound(int currentLevel)
   {
      double remainingCapacity = capacity - weightOfCurrentPacking;
      double profitBound = profitFromCurrentPacking;

      // fill remaining capacity in order of profit density
      while (currentLevel <= numberOfObjects &&
             weight[currentLevel] <= remainingCapacity)
      {
         remainingCapacity -= weight[currentLevel];
         profitBound += profit[currentLevel];
         currentLevel++;
      }
   
      // take fraction of next object
      if (currentLevel <= numberOfObjects)
         profitBound += profit[currentLevel] / weight[currentLevel]
                        * remainingCapacity;
      return profitBound;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      double [] p = {0, 6, 3, 5, 4, 6};
      double [] w = {0, 2, 2, 6, 5, 4};
      int n = 5;
      int c = 10;
      int [] bestx = new int [n + 1];
      System.out.println("Optimal value is " + knapsack(p, w, c, bestx));
      System.out.print("Packing vector is ");
      for (int i=1; i <= n; i++)
         System.out.print(bestx[i] +  " ");
      System.out.println();
   }
}

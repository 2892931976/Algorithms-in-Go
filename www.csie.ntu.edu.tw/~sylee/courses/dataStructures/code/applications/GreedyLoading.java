
/** greedy container loading */

package applications;

public class GreedyLoading
{
   /** greedy algorithm for container loading
     * set x[i] = 1 iff container i, i >= 1 is loaded */
   public static void containerLoading
          (Container [] c, int capacity, int [] x)
   {
      // sort into increasing order of weight
      HeapSort.heapSort(c);
      
      int n = c.length - 1;  // number of containers
      // initialize x
      for (int i = 1; i <= n; i++)
         x[i] = 0;
      
      // select objects in order of weight
      for (int i = 1; i <= n && c[i].weight <= capacity; i++)
      {// enough capacity for container c[i].id
         x[c[i].id] = 1;
         capacity -= c[i].weight;  // remaining capacity
      }
   }
   
   /** test method */
   public static void main(String [] argrs)
   {
      // initialize he containers
      int n = 8;
      int [] w = {0, 60, 20, 40, 70, 30, 50, 10, 25};
      Container [] c = new Container [n + 1];
      for (int i = 1; i <= n; i++)
         c[i] = new Container(i, w[i]);
      int [] x = new int [n + 1];

      // load the ship
      containerLoading(c, 150, x);

      // output loading vector
      System.out.print("Loading vector is ");
      for (int i = 1; i <= n; i++)
         System.out.print(x[i] + " ");
      System.out.println();
   }
}

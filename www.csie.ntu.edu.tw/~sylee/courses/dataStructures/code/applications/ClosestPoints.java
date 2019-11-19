
/** closest pair of points in Euclidean space */

package applications;

import utilities.*;

public class ClosestPoints
{
   // top-level nested classes
   /** point in 2D */
   public static class Point
   {
      // data members
      double x, y;    // point coordinates

      // constructor
      public Point(double theX, double theY)
      {
         x = theX;
         y = theY;
      }
   }

   /** point with id, implements Comparable using x-coordinates */
   public static class Point1 extends Point
                       implements Comparable
   {
      // data member
      int id;         // point identifier
     
      // constructor
      public Point1(double theX, double theY, int theID)
      {
         super(theX, theY);
         id = theID;
      }

      public int compareTo(Object x)
      {
         double xx = ((Point1) x).x;
         if (this.x < xx)
            return -1;
         if (this.x == xx)
            return 0;
         return 1;
      }
   
      public boolean equals(Object x)
         {return this.x == ((Point1) x).x;}
   }

   /** point with an integer field, implements Comparable using y-coordinates */
   public static class Point2 extends Point
                       implements Comparable
   {
      int p;          // index to same point in array X

      // constructor
      public Point2(double theX, double theY, int theP)
      {
         super(theX, theY);
         p = theP;
      }

      public int compareTo(Object x)
      {
         double xy = ((Point2) x).y;
         if (this.y < xy)
            return -1;
         if (this.y == xy)
            return 0;
         return 1;
      }
   
      /** @return true iff this.y == x.y */
      public boolean equals(Object x)
         {return this.y == ((Point2) x).y;}
   }

   /** pairs of points and their distance */
   public static class PointPair
   {
      // data members
      Point1 a;       // one of the points
      Point1 b;       // the other point
      double dist;   // distance between a and b

      // constructor
      public PointPair(Point1 theA, Point1 theB, double theDist)
      {
         a = theA;
         b = theB;
         dist = theDist;
      }
   }
   
   /** @return distance between points u and v */
   public static double dist(Point u, Point v)
   {
      double dx = u.x - v.x;
      double dy = u.y - v.y;
      return Math.sqrt(dx * dx + dy * dy);
   }
   
   /** @param x[l:r] points sorted by x-coordinate, r > l
     * @param y[l:r] points sorted by y-coordinate
     * @param z[l:r] is used for work space
     * @return closest pair of points in x[l:r] */
   private static PointPair closestPair(Point1 [] x, Point2 [] y,
                                    Point2 [] z, int l, int r)
   {
      if (r - l == 1)  // only two points
         return new PointPair(x[l], x[r], dist(x[l], x[r]));
   
      if (r - l == 2)
      {// three points
         // compute distance between all pairs
         double d1 = dist(x[l], x[l + 1]);
         double d2 = dist(x[l + 1], x[r]);
         double d3 = dist(x[l], x[r]);
         // find closest pair
         if (d1 <= d2 && d1 <= d3)
            return new PointPair(x[l], x[l + 1], d1);
         if (d2 <= d3)
            return new PointPair(x[l + 1], x[r], d2);
         else
            return new PointPair(x[l], x[r], d3);
      }
   
      // more than three points, divide into two
      int m = (l + r) / 2;    // x[l:m] in A, rest in B
   
      // create sorted-by-y lists in z[l:m] & z[m+1:r]
      int f = l,      // cursor for z[l:m]
          g = m + 1;  // cursor for z[m+1:r]
      for (int i = l; i <= r; i++)
         if (y[i].p > m) z[g++] = y[i];
         else z[f++] = y[i];
   
      // solve the two parts
      PointPair best = closestPair(x, z, y, l, m);
      PointPair right = closestPair(x, z, y, m + 1, r);
   
      // make best closer pair of the two
      if (right.dist < best.dist)
         best = right;
   
      MergeSort.merge(z, y, l, m, r);   // reconstruct y
   
      // put points within best.d of midpoint in z
      int k = l;                        // cursor for z
      for (int i = l; i <= r; i++)
         if (Math.abs(x[m].x - y[i].x) < best.dist)
            z[k++] = y[i];
   
      // search for closer category 3 pair
      // by checking all pairs from z[l:k-1]
      for (int i = l; i < k; i++)
      {
         for (int j = i + 1; j < k && z[j].y - z[i].y < best.dist; j++)
         {
            double dp = dist(z[i], z[j]);
            if (dp < best.dist) // closer pair found
               best = new PointPair(x[z[i].p], x[z[j].p], dp);
         }
      }
      return best;
   }
   
   /** @return closest pair of points in array x 
     * @return null if fewer than two points in x */
   public static PointPair closestPair(Point1 [] x)
   {
      if (x.length < 2) return null;
   
      // sort on x-coordinate
      MergeSort.mergeSort(x);
   
      // create a point array sorted on y-coordinate
      Point2 [] y = new Point2 [x.length];
      for (int i = 0; i < x.length; i++)
         // copy point i from x to y and index it
         y[i] = new Point2(x[i].x, x[i].y, i);
      MergeSort.mergeSort(y);  // sort on y-coordinate
   
      // create a temporary array
      Point2 [] z = new Point2 [x.length];
   
      // find closest pair
      return closestPair(x, y, z, 0, x.length - 1);
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // define the input stream to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of points");
      int n = keyboard.readInteger();
      Point1 [] x = new Point1 [n];

      for (int i = 0; i < n; i++)
      {
         System.out.println("Enter point " + (i + 1));
         double xcoord = keyboard.readDouble();
         double ycoord = keyboard.readDouble();
         x[i] = new Point1(xcoord, ycoord, i + 1);
      }

      PointPair best = closestPair(x);
      System.out.println("Closest points are " + best.a.id +
                         " and " + best.b.id);
      System.out.println("Their distance is " + best.dist);
   }
}

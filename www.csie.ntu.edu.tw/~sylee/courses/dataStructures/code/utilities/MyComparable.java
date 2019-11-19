
package utilities;

/** Interface to be implemented by all classes
  * that permit comparison between their objects. */

public interface MyComparable extends Comparable
{
   /** @return -1 if this < x,
     *          0 if this == x,
     *          1 if this > x  */
   public int compareTo(Object x);

   /** @return true iff this < x */
   public boolean lessThan(Object x);

   /** @return true iff this <= x */
   public boolean lessThanOrEqual(Object x);

   /** @return true iff this > x */
   public boolean greaterThan(Object x);

   /** @return true iff this >= x */
   public boolean greaterThanOrEqual(Object x);

   /** @return true iff this == x */
   public boolean equals(Object x);
}

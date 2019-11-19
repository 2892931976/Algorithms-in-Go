package utilities;

/** Interface to be implemented by all classes
  * that permit the standard arithmetic operations. */
public interface Computable
{
   /** @return this + x */
   public Object add(Object x);

   /** @return this - x */
   public Object subtract(Object x);

   /** @return this * x */
   public Object multiply(Object x);

   /** @return quotient of this / x */
   public Object divide(Object x);

   /** @return remainder of this / x */
   public Object mod(Object x);

   /** @return this incremented by x */
   public Object increment(Object x);

   /** @return this decremented by x */
   public Object decrement(Object x);

   /** @return the additive zero element */
   public Object zero();

   /** @return the multiplicative identity element */
   public Object identity();
}


/** Dummy class that implements Operable. All methods
  * throw an exception of type UndefinedMethodException
  * The class is useful when you want to implement
  * only a subset of the methods of Operable. */
package utilities;

import exceptions.*;

public class Dummy implements Operable
{
   // Computable interface methods
   public Object add(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".add is undefined");
   }

   public Object subtract(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".subtract is undefined");
   }

   public Object multiply(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".multiply is undefined");
   }

   public Object divide(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".divide is undefined");
   }

   public Object mod(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".mod is undefined");
   }

   public Object increment(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".increment is undefined");
   }

   public Object decrement(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".decrement is undefined");
   }

   public Object zero()
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".zero is undefined");
   }

   public boolean equalsZero()
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".equalsZero is undefined");
   }

   public Object identity()
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".identity is undefined");
   }

   // Comparable interface method
   public int compareTo(Object x)
   {
      throw new UndefinedMethodException("method " +
            getClass().getName() + ".compareTo is undefined");
   }
}

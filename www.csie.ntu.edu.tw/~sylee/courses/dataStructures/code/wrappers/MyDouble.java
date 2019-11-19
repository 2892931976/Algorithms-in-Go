
/** wrapper class for double */

package wrappers;

import utilities.*;
import exceptions.*;

public class MyDouble
       implements Operable, Zero, CloneableObject
{
   // value of the double
   private double value;

   // constructor methods
   /** MyDouble initialized to theValue */
   public MyDouble(double theValue)
      {value = theValue;}

   /** MyDouble initialized to 0 */
   public MyDouble()
      {this(0.0);}

   /** MyDouble initialized to s */
   public MyDouble(String s)
          throws NumberFormatException
      {value = (Double.valueOf(s)).doubleValue();}

   // accessor methods
   /** @return value of the double */
   public double getValue() {return value;}
   public double doubleValue() {return value;}

   // mutator value
   /** set value to theValue */
   public void setValue(double theValue)
      {value = theValue;}

   // convert to String, useful for output
   /** @return integer in String format */
   public String toString()
      {return Double.toString(value);}

   /** input from the given input stream */
   public static MyDouble input(MyInputStream stream)
   {
      System.out.println("Enter a double value");
      return new MyDouble(stream.readDouble());
   }

   /** make a clone */
   public Object clone()
      {return new MyDouble(value);}

   // Computable interface methods
   /** @return this + x */
   public Object add(Object x)
   {
      return new MyDouble
                 (value + ((MyDouble) x).value);
   }

   /** @return this - x */
   public Object subtract(Object x)
   {
      return new MyDouble
                 (value - ((MyDouble) x).value);
   }

   /** @return this * x */
   public Object multiply(Object x)
   {
      return new MyDouble
                 (value * ((MyDouble) x).value);
   }

   /** @return this / x */
   public Object divide(Object x)
   {
      return new MyDouble
                 (value / ((MyDouble) x).value);
   }

   /** @return remainder of this / x */
   public Object mod(Object x)
   {
      return new MyDouble
                 (value % ((MyDouble) x).value);
   }

   /** @return this incremented by x */
   public Object increment(Object x)
   {
      value += ((MyDouble) x).value;
      return this;
   }

   /** @return this decremented by x */
   public Object decrement(Object x)
   {
      value -= ((MyDouble) x).value;
      return this;
   }

   /** @return a new copy of 0 */
   public Object zero()
      {return new MyDouble(0.0);}

   /** @return true iff value == 0 */
   public boolean equalsZero()
      {return value == 0.0;}

   /** @return a new copy of 1 */
   public Object identity()
      {return new MyDouble(1.0);}

   // Comparable interface method
   /** @return -1 if this < x,
     *          0 if this == x,
     *          1 if this > x */

   public int compareTo(Object x)
   {
      double y = ((MyDouble) x).value;
      if (value < y) return -1;
      if (value == y) return 0;
      return 1;
   }

   // override Object.equals
   /** @return true iff this == x */
   public boolean equals(Object x)
      {return value == ((MyDouble) x).value;}

   /** test program */
   public static void main(String [] args)
   {
      MyDouble[] z = new MyDouble[5];
      for (int i = 0; i < z.length; i++)
         z[i] = new MyDouble(i * i + 1.0);

      System.out.println("Testing the MyDouble wrapper class");
      // output the array
      System.out.print("The test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();

      // test Computable interface operations
      System.out.println(z[1] + " + " + z[2] + " = " + z[1].add(z[2]));
      System.out.println(z[2] + " - " + z[1] + " = " + z[2].subtract(z[1]));
      System.out.println(z[1] + " * " + z[2] + " = " + z[1].multiply(z[2]));
      System.out.println(z[2] + " / " + z[0] + " = " + z[2].divide(z[0]));
      System.out.println("The zero element is " + z[2].zero());
      System.out.println("The identity element is " + z[2].identity());
      System.out.println();

      // test Comparable interface operation
      System.out.println(z[1] + " compareTo(" + z[2] + ") = " +
                         z[1].compareTo(z[2]));
      System.out.println(z[2] + " compareTo(" + z[1] + ") = " +
                         z[2].compareTo(z[1]));
      System.out.println(z[2] + " compareTo(" + z[2] + ") = " +
                         z[2].compareTo(z[2]));
      System.out.println();

      // test equals
      System.out.println(z[1] + " == " + z[2] + " = " + z[1].equals(z[2]));
      System.out.println(z[1] + " == " + z[1] + " = " + z[1].equals(z[1]));
      System.out.println();

      // test generic sum method
      System.out.println("The sum of the numbers is " +
                         MyMath.sum(z, z.length));
      System.out.println();

      // test generic swap method
      MyMath.swap(z, 0, z.length - 1);
      // output the array
      System.out.print("After swapping the first and last "
                        + "elements, the test array is ");
      for (int i = 0; i < z.length; i++)
         System.out.print(z[i] + " ");
      System.out.println();
      System.out.println();

      // test generic max function
      try {
         System.out.println("The maximum element is " +
                            z[MyMath.max(z, z.length - 1)]);
         }
      catch (IllegalArgumentException e)
         {System.out.println(e.getMessage());}
   }
}


/** output the roots of a quadratic. */

package applications;

import exceptions.*;


public class QuadraticRoots
{
   /** The quadratic is ax^2 + bx + c.
     * a must be nonzero.
     * @exception IllegalArgumentException
     * thrown when coefficient of x^2 is zero */

   public static void outputRoots(double a, double b, double c)
   {
      if (a == 0)
         throw new IllegalArgumentException
                   ("Coefficient of x^2 must be nonzero");

      double d = b * b - 4 * a * c;
      if (d > 0) {// two real roots
                  double sqrtd = Math.sqrt(d);
                  System.out.println
                  ("There are two real roots " +
                   (-b + sqrtd) / (2 * a) + " and " +
                   (-b - sqrtd) / (2 * a));
                 }
      else if (d == 0)
              // both roots are the same
              System.out.println
              ("There is only one distinct root " + -b / (2 * a));
           else {//  complex conjugate roots
                 System.out.println("The roots are complex");
                 System.out.println("The real part is " + -b / (2 * a));
                 System.out.println("The imaginary part is " +
                   Math.sqrt(-d) / (2 * a));
                }
   }
      
   /** test program */
   public static void main(String [] args)
   {
       try {        
          // two real roots
          outputRoots(1, -5, 6);
 
          // one real root
          outputRoots(1, -8, 16);
 
          // complex roots
          outputRoots(1, 2, 5);
 
          // not a quadratic
          outputRoots(0, 4, 4);
          }

      catch (IllegalArgumentException e)
         {System.out.println(e.getMessage());}
   }
}


/** evaluate a polynomial */

package applications;

import utilities.*;
import wrappers.*;

public class EvaluatePolynomial
{
   /** @return coeff[0] * x^0 coeff a[1] * x^1
     * + coeff[2] * x^2 + ...
     * @throws IllegalArgumentException
     * when coeff.length < 1 */
   public static Computable valueOf(Computable [] coeff, Computable x)
   {
      if (coeff.length < 1)
         throw new IllegalArgumentException
               ("must have >= 1 coefficient");
 
      Computable y = (Computable) coeff[0].identity(),   // x^0
                 value = coeff[0];                       // coeff[0] * x^0

      // add remaining terms to value
      for (int i = 1; i < coeff.length; i++)
      {
         y = (Computable) y.multiply(x);  // y = x^i

         // add in next term
         value.increment(y.multiply(coeff[i]));
      }

      return value;
   }
      
   /** test program */
   public static void main(String [] args)
   {
      MyInteger [] coeff = {new MyInteger(1),
                            new MyInteger(2),
                            new MyInteger(3),
                            new MyInteger(4),
                            new MyInteger(5),
                            new MyInteger(6)};
      MyInteger x = new MyInteger(2);

      System.out.println("The polynomial value at 2 is "
                         + valueOf(coeff, x));
   }
}

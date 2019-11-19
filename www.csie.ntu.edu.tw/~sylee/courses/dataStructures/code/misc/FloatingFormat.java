
/** Formating floating point numbers to 2 places.
  * Rounding is automatic except when the rounded digit is 5.
  * 12.345 doesn't round to 12.35 but 12.355 rounds to 12.36
  * Add 0.0005 to round 12.235. */
package misc;

import java.text.*;   // has the NumberFormat and DecimalFormat classes

public class FloatingFormat
{
   public static void main(String [] args)
   {
      // standard number format in use
      NumberFormat nf1 = NumberFormat.getInstance();

      /* # denotes an optional digit in output
       * 0 denotes a cumpulsory digit in output
       * so use # when you want 0s to be supressed */
      NumberFormat nf2 = new DecimalFormat("#,##0.00");
        // nf2 outputs with exactly 2 decimal digits
        // at least one digit before the decimal point
        // is output. If the number is at least 1000, a comma
        // is ouput at the thousandths place.

      NumberFormat nf3 = new DecimalFormat("0.00");
        // similar to nf2 but no comma

      // change standard format so that exactly two decimal digits
      // are output
      nf1.setMinimumFractionDigits(2);
      nf1.setMaximumFractionDigits(2);

      // let's see what all this does
      double myNumber = 1234.5678;
      System.out.println(myNumber + "  " + nf1.format(myNumber)
                                  + "  " + nf2.format(myNumber)
                                  + "  " + nf3.format(myNumber));
      myNumber = 12.355;
      System.out.println(myNumber + "  " + nf1.format(myNumber)
                                  + "  " + nf2.format(myNumber)
                                  + "  " + nf3.format(myNumber));
      myNumber = 123456.5;
      System.out.println(myNumber + "  " + nf1.format(myNumber)
                                  + "  " + nf2.format(myNumber)
                                  + "  " + nf3.format(myNumber));
   }
}

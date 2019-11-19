
/** Currency class with private data member
  * amount of type long. */

package applications;

import java.text.*;   // has the NumberFormat and DecimalFormat classes
import utilities.*;

public class Currency2
{
   // class constants
   public static final boolean PLUS = true;
   public static final boolean MINUS = false;
   public static final NumberFormat TWODIGIT = new DecimalFormat("00");

   // instance data member
   private long amount;

   // constructors
   /** initialize instance to $0.00 */
   public Currency2()
      {this(PLUS, 0L, (byte) 0);}

   /** initialize instance to
     * theSign $ theDollars.theCents */

   public Currency2(boolean theSign, long theDollars,
                   byte theCents)
   {
      if (theDollars < 0)
         throw new IllegalArgumentException
               ("Currency2: Dollar value must be >= 0");

      if (theCents < 0 || theCents > 99)
         throw new IllegalArgumentException
               ("Currency2: Cents must be between 0 and 99");

      amount = theDollars * 100 + theCents;
      if (theSign == MINUS) amount = -amount;
   }

   /** initialize with double */
   public Currency2(double theValue)
      {setValue(theValue);}

   /** initialize with amount value */
   private Currency2(long theAmount)
      {amount = theAmount;}

   // accessor methods

   /** @return sign */
   public boolean getSign()
   {
      if (amount < 0) return MINUS;
      else return PLUS;
   }

   /** @return dollars */
   public long getDollars()
   {
      if (amount < 0) return - amount / 100;
      else return amount / 100;
   }

   /** @return cents */
   public byte getCents()
   {
      if (amount < 0)
         return (byte) (-amount - getDollars() * 100);
      else return (byte) (amount - getDollars() * 100);
   }

   // mutator methods
   /** Set the sign of amount to theSign.
     * For this to work properly amount must
     * be nonzero. */

   public void setSign(boolean theSign)
   {
      // change the sign as necessary
      if ((amount < 0 && theSign == PLUS) ||
          (amount > 0 && theSign == MINUS))
         amount = -amount;
   }
 
   /** set dollars = theDollars */
   public void setDollars(long theDollars)
   {
      if (theDollars < 0)
         throw new IllegalArgumentException
               ("Currency2: Dollar value must be >= 0");
      else {boolean sign = getSign();
            byte cents = getCents();
            amount = theDollars * 100 + cents;
            if (sign == MINUS)
               amount = -amount;
           }
   }

   /** set cents = theCents */
   public void setCents(byte theCents)
   {
      if (theCents < 0 || theCents > 99)
         throw new IllegalArgumentException
               ("Currency2: Cents must be between 0 and 99");
      else {boolean sign = getSign();
            long dollars = getDollars();
            amount = dollars * 100 + theCents;
            if (sign == MINUS)
               amount = -amount;
           }
   }

   /** set amount */
   public void setValue(double theValue)
   {
      if (theValue < 0)
         amount = (long) ((theValue - 0.005) * 100);
      else amount = (long) ((theValue + 0.005) * 100);
   }

   public void setValue(Currency2 x)
      {amount = x.amount;}
   
   /** convert to a string */
   public String toString()
   {
      if (amount >= 0)
         {return "$" + getDollars() + "." + TWODIGIT.format(getCents());}
      else {return "-$" + getDollars() + "." + TWODIGIT.format(getCents());}
   }

   // arithmetic methods

   /** return this + x */
   public Currency2 Add(Currency2 x)
      {return new Currency2(amount + x.amount);}
   
   /** return this incremented by x */
   public Currency2 Increment(Currency2 x)
   {
      amount += x.amount;
      return this;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // test constructors
      Currency2 g = new Currency2(),
               h = new Currency2(PLUS, 3L, (byte) 50),
               i = new Currency2(-2.50),
               j = new Currency2();

      // test toString
      System.out.println("The initial values are " + g +
                         " " + h + " " + i + " " + j);
      System.out.println();

      // test mutators
      // first make g nonzero
      g.setDollars(2);
      g.setSign(MINUS);
      g.setCents((byte) 25);
      i.setValue(-6.45);
      System.out.println("New values are " + g + " " + i);
      System.out.println();

      // do some arithmetic
      j = h.Add(g);
      System.out.println(h + " + " + g + " = " + j);

      System.out.print(i + " incremented by " + h + " is ");
      i.Increment(h);
      System.out.println(i);

      j = i.Add(g).Add(h);
      System.out.println(i + " + " + g + " + " + h +
                         " = " + j);
      System.out.println();

      j = i.Increment(g).Add(h);
      System.out.println(j);
      System.out.println(i);
   }
}

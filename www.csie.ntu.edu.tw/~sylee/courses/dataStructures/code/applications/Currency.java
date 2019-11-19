package applications;
import java.text.*;   // has the NumberFormat and DecimalFormat classes

public class Currency
{
   // class constants
   public static final boolean PLUS = true;
   public static final boolean MINUS = false;
   public static final NumberFormat TWODIGIT = new DecimalFormat("00");

   // instance data members
   private boolean sign;
   private long dollars;
   private byte cents;

   // constructors
   /** initialize instance to
     * theSign $ theDollars.theCents
     * @throws IllegalArgumentException when theDollars < 0
     * or theCents < 0 or theCents > 99 */
   public Currency(boolean theSign, long theDollars, byte theCents)
   {
      sign = theSign;

      if (theDollars < 0)
         throw new IllegalArgumentException
               ("Dollar value must be >= 0");
      else
         dollars = theDollars;

      if (theCents < 0 || theCents > 99)
         throw new IllegalArgumentException
               ("Cents must be between 0 and 99");
      else
         cents = theCents;
   }

   /** initialize instance to $0.00 */
   public Currency()
      {this(PLUS, 0L, (byte) 0);}

   /** initialize with double */
   public Currency(double theValue)
      {setValue(theValue);}

   // accessor methods
   /** @return sign */
   public boolean getSign()
      {return sign;}

   /** @return dollars */
   public long getDollars()
      {return dollars;}

   /** @return cents */
   public byte getCents()
      {return cents;}

   // mutator methods
   /** set sign = theSign */
   public void setSign(boolean theSign)
      {sign = theSign;}
 
   /** set dollars = theDollars
     * @throws IllegalArgumentException when theDollars < 0 */
   public void setDollars(long theDollars)
   {
      if (theDollars < 0)
         throw new IllegalArgumentException
               ("Dollar value must be >= 0");
      else
         dollars = theDollars;
   }

   /** set cents = theCents
     * @throws IllegalArgumentException when theCents < 0 or > 99 */
   public void setCents(byte theCents)
   {
      if (theCents < 0 || theCents > 99)
         throw new IllegalArgumentException
               ("Cents must be between 0 and 99");
      else
         cents = theCents;
   }

   /** set sign, dollars, and cents */
   public void setValue(double theValue)
   {
      if (theValue < 0)
      {
         sign = MINUS;
         theValue = -theValue;
      }
      else
         sign = PLUS;

      dollars = (long) (theValue + 0.005); // extract integral part

      // get two decimal digits
      cents = (byte) ((theValue + 0.005 - dollars) * 100);
   }

   public void setValue(Currency x)
   {
      sign = x.sign;
      dollars = x.dollars;
      cents = x.cents;
   }
   
   /** convert to a string */
   public String toString()
   {
      if (sign == PLUS)
         {return "$" + dollars + "." + TWODIGIT.format(cents);}
      else
         {return "-$" + dollars + "." + TWODIGIT.format(cents);}
   }

   // arithmetic methods
   /** @return this + x */
   public Currency add(Currency x)
   {
      // convert this to a long
      long a1 = dollars * 100 + cents;
      if (sign == MINUS)
         a1 = -a1;
      
      // convert x to a long
      long a2 = x.dollars * 100 + x.cents;
      if (x.sign == MINUS)
         a2 = -a2;
      
      long a3 = a1 + a2;
       
      // convert result to Currency object
      Currency answer = new Currency();
      if (a3 < 0)
      {
         answer.sign = MINUS;
         a3 = -a3;
      }
      else
         answer.sign = PLUS;
      answer.dollars = a3 / 100;
      answer.cents = (byte) (a3 - answer.dollars * 100);
      
      return answer;
   }
   
   /** @return this incremented by x */
   public Currency increment(Currency x)
   {
      setValue(add(x));
      return this;
   }
   
   /** test program */
   public static void main(String [] args)
   {
      // test constructors
      Currency g = new Currency(),
               h = new Currency(PLUS, 3L, (byte) 50),
               i = new Currency(-2.50),
               j = new Currency();

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
      j = h.add(g);
      System.out.println(h + " + " + g + " = " + j);

      System.out.print(i + " incremented by " + h + " is ");
      i.increment(h);
      System.out.println(i);

      j = i.add(g).add(h);
      System.out.println(i + " + " + g + " + " + h +
                         " = " + j);
      System.out.println();

      j = i.increment(g).add(h);
      System.out.println(j);
      System.out.println(i);
   }
}

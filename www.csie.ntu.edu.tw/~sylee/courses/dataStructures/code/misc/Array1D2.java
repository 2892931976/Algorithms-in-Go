
import utilities.*;
import wrappers.*;
import java.lang.reflect.*;

public class Array1D2
{
    Object[] a;

    public void inputArray(Method inputMethod, MyInputStream stream)
    {
        try
        {
            System.out.println("Enter number of elements");
            int n = stream.readInteger();
            a = new Object [n];

            Object [] inputMethodArgs = {stream};
            for (int i=0; i<n; i++)
            {
                System.out.println("Enter element " + (i+1));
                a[i] = inputMethod.invoke(null, inputMethodArgs);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            throw new IllegalArgumentException("Array1D.inputarray");
        }
    }

    public int length()
        {return a.length;}

    /** test program */
    public static void main(String [] args)
    {
        // test constructors
        Array1D2 b = new Array1D2();

        // test inputArray
        Class [] parameterTypes = {MyInputStream.class};
        Class c=MyInteger.class;
        Method m;
        try {
            m = c.getMethod("input", parameterTypes);
        }
        catch (Exception e){
            System.out.println(e);
            throw new IllegalArgumentException("Array1D.inputarray");
        }
        MyInputStream s = new MyInputStream();
        b.inputArray(m, s);
        for (int j=0; j<b.length(); j++)
            System.out.println( b.a[j].toString() );
   }
}


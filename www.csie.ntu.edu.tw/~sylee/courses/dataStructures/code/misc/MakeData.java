
package misc;

import wrappers.*;
public class MakeData
{
   public static void main(String [] args)
   {
       int n = 500;
       System.out.println(n + " " + n + " " + (4*n - 6));
       for (int i = 1; i <= n; i++)
          for (int j = 1; j <= n; j++)
            if (i >= j && i - j < 4)
               System.out.println(i + " " + j + " " + new MyInteger(3));

       System.out.println(n + " " + n + " " + (2*n - 1));
       for (int i = 1; i <= n; i++)
          for (int j = 1; j <= n; j++)
            if (i <= j && j - i < 2)
               System.out.println(i + " " + j + " " + new MyInteger(5));
  }
}

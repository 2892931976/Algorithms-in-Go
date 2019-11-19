package exceptions;

public class UndefinedMethodException
       extends RuntimeException
{
   public UndefinedMethodException()
      {super();}
   public UndefinedMethodException(String message)
      {super(message);}
}

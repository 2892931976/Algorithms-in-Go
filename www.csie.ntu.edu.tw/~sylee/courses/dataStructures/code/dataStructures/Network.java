

package dataStructures;

import java.util.*;

public interface Network
{
   // ADT methods
   public int vertices();
   public int edges();
   public boolean existsEdge(int i, int j);
   public void putEdge(Object theEdge);
   public void removeEdge(int i, int j);
   public int degree(int i);
   public int inDegree(int i);
   public int outDegree(int i);

   // create an iterator for vertex i
   public Iterator iterator(int i);
}

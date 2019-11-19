   
/** output LPT schedules */

package applications;

import dataStructures.*;
import wrappers.*;
import utilities.*;

public class LPTSchedule
{
   // top-level nested classes  
   private static class JobNode implements Comparable
   {
      int id;         // job identifier
      Operable time;  // processing time

      // constructor
      public JobNode(int theId, Operable theTime)
      {
         id = theId;
         time = theTime;
      }

      public int compareTo(Object x)
         {return time.compareTo(((JobNode) x).time);}

      public boolean equals(Object x)
         {return time.equals(((JobNode) x).time);}
   }
   
   private static class MachineNode implements Comparable
   {
      int id;          // machine identifier
      Operable avail;  // when it becomes free

      // constructor
      public MachineNode(int theId, Operable theAvail)
      {
         id = theId;
         avail = theAvail;
      }

      public int compareTo(Object x)
         {return avail.compareTo(((MachineNode) x).avail);}

      public boolean equals(Object x)
         {return avail.equals(((MachineNode) x).avail);}
   }
   
   /** Output an m machine LPT schedule for the a.length
     * jobs whose times are a[1:a.length-1].  */
   public static void makeSchedule(JobNode [] a, int m)
   {
      if (a.length <= m)
      {
         System.out.println("Schedule each job on a different machine.");
         return;
      }
   
      HeapSort.heapSort(a); // in ascending order

      // initialize m machines and the min heap
      MinHeap machineHeap = new MinHeap(m);
      for (int i = 1; i <= m; i++)
      {
         MachineNode x = new MachineNode(i, (Operable) a[1].time.zero());
         machineHeap.put(x);
      }
   
      // construct schedule
      for (int i = a.length - 1; i >= 1; i--)
      {// schedule job i on first free machine
         MachineNode x = (MachineNode) machineHeap.removeMin();
         System.out.println("Schedule job " + a[i].id 
              + " on machine " + x.id + " from " + x.avail
              + " to " + x.avail.add(a[i].time));
         x.avail.increment(a[i].time);  // new available time for this machine
         machineHeap.put(x);
      }
   }
   
   /** test program */
   public static void main(String [] args)
   {
      JobNode [] a = new JobNode [11];
      int n = 10;
      for (int i = 1; i <= n; i++)
         a[i] = new JobNode(i, new MyInteger(2 * i * i));
      makeSchedule(a, 3);
   }
}

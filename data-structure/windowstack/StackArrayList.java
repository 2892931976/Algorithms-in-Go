package com.chj.windowstack;

import java.util.ArrayList;

/**
 * This class implements a Stack using an ArrayList.
 * <p>
 * A stack is exactly what it sounds like. An element gets added to the top of
 * the stack and only the element on the top may be removed.
 * <p>
 * This is an ArrayList Implementation of a stack, where size is not
 * a problem we can extend the stack as much as we want.
 *
 * @author Unknown
 */
public class StackArrayList<V> {

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        
        StackArrayList<Integer> myStackArrayList = new StackArrayList<Integer>();
        
        myStackArrayList.push(5);
        myStackArrayList.push(8);
        myStackArrayList.push(2);
        myStackArrayList.push(9);

        System.out.println("*********************Stack List Implementation*********************");
        System.out.println(myStackArrayList.isEmpty()); // will print false
        System.out.println(myStackArrayList.peek()); // will print 9
        System.out.println(myStackArrayList.pop()); // will print 9
        System.out.println(myStackArrayList.peek()); // will print 2
        System.out.println(myStackArrayList.pop()); // will print 2
    }

    /**
     * ArrayList representation of the stack
     */
    private ArrayList<V> stackList;

    /**
     * Constructor
     */
    public StackArrayList() {
        stackList = new ArrayList<>();
    }

    /**
     * Adds value to the end of list which
     * is the top for stack
     *
     * @param value value to be added
     */
    public void push(V value) {
        stackList.add(value);
    }

    /**
     * Pops last element of list which is indeed
     * the top for Stack
     *
     * @return Element popped
     */
    public V pop() {

        if (!isEmpty()) { // checks for an empty Stack
            V popValue = stackList.get(stackList.size() - 1);
            stackList.remove(stackList.size() - 1);  // removes the poped element from the list
            return popValue;
        }

        System.out.print("The stack is already empty!");
        return null;
//    	return stackList.remove(stackList.size() - 1);
    }

    /**
     * Checks for empty Stack
     *
     * @return true if stack is empty
     */
    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    /**
     * Top element of stack
     *
     * @return top element of stack
     */
    public V peek() {
        return stackList.get(stackList.size() - 1);
    }
}

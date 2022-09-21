/*
 *       File: Stack.java
 *      Class: CSC205
 * Programmer: Jennifer Norby
 *    Purpose: To write a class that is a representation of a stack class.
 */
package program3;

import java.io.*;
import java.util.*;

public class Stack<E> implements Serializable
{
    //------DATA------
    private ArrayList<E> contents;
    
    //-----CONSTRUCTORS-----
    public Stack()
    {
        this.contents = new ArrayList<>();
    }
    
    //------METHODS-----
    public E peek()
    {
        if (empty())               //nothing in the ArrayList
            throw new EmptyStackException();    //throws exception
        return contents.get(0);     //return (and not remove) whatever is at position 0
    }
    
    public E push(E item)
    {
        contents.add(0, item);  //adds item to position zero
        return item;            //returns item
    }

    public E pop()
    {
        if (empty())                  //nothing in the ArrayList
            throw new EmptyStackException();   //throws exception
        return contents.remove(0);     //return and remove whatever is at position 0
    }

    public boolean empty()
    {
        return contents.isEmpty();
    }
    
    public int search(Object object)
    {
        int i = contents.indexOf(object);   //current index
        return (i == -1) ? -1:i + 1;        //return -1 if it cannot locate, else return index + 1
    }
    
    public String toString()
    {
        return contents.toString(); //returns contents as a string
    }   
}
        
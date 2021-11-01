package com.erotourtes;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<T> implements Iterable<T> {
    private Node first;
    private Node last;
    private int length;

    public void insertAt(int index, T value) {
        validateIndex(index, true);

        var newNode = new Node(value);
        if(isEmpty()) {
            first = newNode;
            last = first;
            length++;
            return;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
            length++;
            return;
        }

        var iterator = new ListIterator();

        //iterator: index = 1 => currentElement on index 0;
        while (iterator.index != index)
            iterator.next();

        newNode.next = iterator.currentElement.next;
        iterator.currentElement.next = newNode;

        if(iterator.currentElement == last)
            last = newNode;

        length++;
    }

    public void unShift(T value) {
        insertAt(0, value);
    }

    public void push(T value) {
        insertAt(length, value);
    }

    public T deleteAt(int index) {
        validateIndex(index, false);

        if (isEmpty())
            throw new NoSuchElementException();
        else if (index == 0) {
            var secondElement = first.next;
            var deletedElement = first;
            //not to pollute memory;
            deletedElement.next = null;
            first = secondElement;
            length--;
            return deletedElement.value;
        }

        var iterator = new ListIterator();
        while (iterator.index != index)
            iterator.next();

        var preDeletedElement = iterator.currentElement;
        var deletedElement = preDeletedElement.next;
        preDeletedElement.next = deletedElement.next;
        //not to pollute memory;
        deletedElement.next = null;
        if(deletedElement == last)
            last = preDeletedElement;

        length--;

        return deletedElement.value;
    }

    public T shift() {
        return deleteAt(0);
    }

    public T pop() {
        return deleteAt(length-1);
    }

    public int indexOf(T element) {
        var iterator = new ListIterator();
        while (iterator.hasNext())
            if (iterator.next().equals(element)) return iterator.index - 1;
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public T[] toArray() {
        T[] array = (T[])new Object[length];
        var iterator = new ListIterator();
        while (iterator.hasNext())
            array[iterator.index] = iterator.next();

        return array;
    }

    /*public Object[] toArray() {
        Object[] array = new Object[length];
        var iterator = new ListIterator();
        while (iterator.hasNext())
            array[iterator.index] = iterator.next();

        return array;
    }*/

    /*public T[] toArray(T[] type) {
        T[] array = (T[])Array.newInstance(type.getClass().componentType(), length);
        var iterator = new ListIterator();
        while (iterator.hasNext())
            array[iterator.index] = iterator.next();

        return array;
    }*/

    public void reverse() {
        if(isEmpty())
            return;
        // 1 -> 2 -> 3;
        // p    c    t
        //      p    c   t
        //           p   c
        var previous =first;
        var current = first.next;
        while (current != null) {
            var temp = current.next;
            current.next = previous;
            previous = current;
            current = temp;
        }

        last = first;
        first.next = null;
        first = previous;
    }

    public T getKthFromEnd(int k) {
        //without using length field;
        // in one pass
        /* [ 1 -> 2 -> 3 -> 4 -> 5]
             *              *
             k = 1 (d = 0)
             k = 2 (d = 1)
             k = 3 (d = 2)
             k = 4 (d = 3)
        */
        if(k <= 0 || isEmpty())
            throw new IllegalArgumentException();

        var target = first;
        var afterTrarget = first;
        for(int i = 0; i < k - 1; i++)
            if(afterTrarget.next == null)
                throw new IllegalArgumentException();
            else
                afterTrarget = afterTrarget.next;

        while (afterTrarget.next != null) {
            afterTrarget = afterTrarget.next;
            target = target.next;
        }
        return target.value;
    }

    public T[] getMiddle() {
        //without using length field
        // in one pass
        /* [ 1 -> 2 -> 3 -> 4 -> 5 -> 6]
        */
        if(isEmpty())
            return (T[]) new Object[0];

        var slower= first;
        var faster = first;

        while (faster.next != null) {
            if (faster.next.next == null) {
                return (T[]) new Object[] {slower.value, slower.next.value};
            } else {
                faster = faster.next.next;
                slower = slower.next;
            }
        }
        return (T[]) new Object[] {slower.value};
    }

    public void makeListWithLoop(int index) {
        validateIndex(index, true);

        var iterator = new ListIterator();
        while(iterator.hasNext())
            if(iterator.index == index)
                break;
            else
                iterator.next();

        last.next = iterator.currentElement;
    }

    public boolean hasLoop() {
        var slower = first;
        var faster = first;

        while (faster != null) {
            if(faster.next == null)
                return false;
            slower = slower.next;
            faster = faster.next.next;

            if(slower == faster)
                return true;
        }
        return false;
    }

    private void validateIndex(int index, boolean forInserting) {
        if (forInserting && (index > length || index < 0))
            throw new IllegalArgumentException();
        else if (!forInserting && (index >= length || index < 0))
            throw new IllegalArgumentException();
    }

    private boolean isEmpty() {
        return first == null;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        String listInString = "[";
        var iterator = iterator();
        while (iterator.hasNext())
            listInString += iterator.next() + ", ";

        //if last element pointing at other element;
        if(hasLoop())
            listInString += "[" + last.next.value + "]" + ", ";

        if(!isEmpty())
            listInString = listInString.substring(0, listInString.length() - 2);
        return listInString + "]";
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T>{
        private int index;
        private Node currentElement = first;

        private Node getNode() {
            return currentElement;
        }

        @Override
        public boolean hasNext() {
            return index < length;
        }

        @Override
        public T next() {
            if(index == 0) {
                index++;
                return currentElement.value;
            }
            currentElement = currentElement.next;
            index++;
            return currentElement.value;
        }
    }

    private class Node {
        T value;
        Node next;
        public Node(T value) {
            this.value = value;
        }
    }
}

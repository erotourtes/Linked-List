package com.erotourtes;


import java.util.Iterator;

public class List<T> implements Iterable<T> {
    private ListElement<T> startingElement;
    private ListElement<T> lastElement;

    private int length;

    public List() {
        startingElement = new ListElement();
        startingElement.index = -1;
        lastElement = startingElement;
        length = 0;
    }

    //add methods
    public boolean addByIndex(int index, T value) {
        if(index > length)
            return false;

        var newItem = new ListElement<T>(index, value);
        var iterator = listIterator();

        while (iterator.currentElement.index < index - 1)
            iterator.saveNext();

        if(iterator.currentElement.linkToNext != null)
            newItem.linkToNext = iterator.currentElement.linkToNext;
        iterator.currentElement.linkToNext = newItem;

        iterator.saveNext();
        length++;

        while(iterator.hasNext()) {
            iterator.saveNext();
            iterator.currentElement.index++;
        }

        return true;
    }

    public boolean unShift(T value) {
        return addByIndex(0, value);
    }

    public boolean push(T value) {
        return addByIndex(length, value);
    }

    //remove methods
    public boolean removeByIndex(int index) {
        if(index > length)
            return false;

        var iterator = listIterator();

        while (iterator.currentElement.index < index - 1)
            iterator.saveNext();

        iterator.currentElement.linkToNext = iterator.currentElement.linkToNext.linkToNext;
        length--;

        while(iterator.hasNext()) {
            iterator.saveNext();
            iterator.currentElement.index--;
        }

        return true;
    }

    public boolean shift() {
        return removeByIndex(0);
    }

    public boolean pop() {
        return removeByIndex(length);
    }

    public void info() {
        var iterator = listIterator();

        while(iterator.hasNext()) {
            iterator.next();
            var el = iterator.currentElement;
            System.out.println("index = " + el.index + " value = " + el.value);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        var iterator = iterator();

        String values = "{";

        while(iterator.hasNext()) {
            values += iterator.next();

            if(iterator.hasNext())
                values += ", ";
        }

        values += "}";
        return values;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator(this);
    }

    private ListIterator listIterator() {
        return new ListIterator(this);
    }

    private  class ListIterator implements Iterator<T> {
        private List<T> list;
        private int index;
        private ListElement<T> currentElement;

        public ListIterator(List<T> list) {
            this.list = list;
            index = 0;
            currentElement = startingElement;
        }

        @Override
        public boolean hasNext() {
            return index < list.length;
        }

        @Override
        public T next() {
            currentElement = currentElement.linkToNext;
            index++;
            return currentElement.value;
        }

        public void saveNext() {
            currentElement = currentElement.linkToNext;
            index++;
        }
    }

    private class ListElement<T> {
        private T value;
        private ListElement<T> linkToNext;
        private int index;

        public ListElement() {}
        public ListElement(int index, T value) {
            this.value = value;
            this.index = index;
        }
    }

    public int getLength() {
        return length;
    }
}

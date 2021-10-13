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

    /*public void add(T value) {
        var newItem = new ListElement<T>();
        newItem.value = value;


        if(startingElement.linkToNext == null)
            startingElement.linkToNext = newItem;
        else
            lastElement.linkToNext = newItem;

        lastElement = newItem;

        newItem.index = length++;
    }*/

    /*public boolean insertToStart(T value) {
        var newItem = new ListElement<T>();
        newItem.value = value;
        newItem.index = 0;

        var iterator = listIterator();

        while(iterator.hasNext()) {
            iterator.next();
            iterator.currentElement.index++;
        }

        if(startingElement.linkToNext != null)
            newItem.linkToNext = startingElement.linkToNext;

        startingElement.linkToNext = newItem;

        length++;
        return true;
    }*/

    public boolean addByIndex(int index, T value) {
        if(index > length)
            return false;

        var newItem = new ListElement<T>(index, value);
        var iterator = listIterator();

        while (iterator.currentElement.index < index - 1) {
            iterator.next();
        }

        if(iterator.currentElement.linkToNext != null)
            newItem.linkToNext = iterator.currentElement.linkToNext;
        iterator.currentElement.linkToNext = newItem;

        while(iterator.hasNext()) {
            iterator.next();
            iterator.currentElement.index++;
        }

        length++;

        return true;
    }

    /*public boolean addByIndex(int index, T value, boolean t) {
        if (index > length)
            return false;

        if(index == 0)
            return insertToStart(value);

        var newItem = new ListElement<T>();
        newItem.value = value;
        newItem.index = index;

        var iterator = listIterator();
        boolean isAdded = false;

        while (iterator.hasNext()) {
            var el = iterator.currentElement;
            if(isAdded) {
                el.index++;
            } else if(el.index == index - 1) {
                if(el.linkToNext != null)
                    newItem.linkToNext = el.linkToNext;
                el.linkToNext = newItem;
                isAdded = true;
            }
        }

        newItem.index = length++;
        return true;
    }*/

    public void info() {
        var iterator = listIterator();

        while(iterator.hasNext()) {
            iterator.next();
            var el = iterator.currentEl();
            System.out.println("index = " + el.index + " value = " + el.value);
        }
    }

    public T remove(int index) {
        var currentElement = startingElement;
        T deletedElementsValue = startingElement.value;
        boolean isFinded = false;

        for (var i = 0; i < length; i++) {
            if(isFinded) {
                currentElement.index--;
            } else if (currentElement.linkToNext.index == index) {
                deletedElementsValue = currentElement.linkToNext.value;

                var deletedElement = currentElement.linkToNext;
                if (deletedElement.linkToNext != null)
                    currentElement.linkToNext = deletedElement.linkToNext;
                else
                    currentElement.linkToNext = null;

                isFinded = true;
            }
            currentElement = currentElement.linkToNext;
        }
        if(isFinded)
            length--;

        return deletedElementsValue;
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

        public ListElement<T> currentEl() {
            return currentElement;
        }

        @Override
        public boolean hasNext() {
            return index < list.length;
        }

        @Override
        public T next() {
            index++;
            currentElement = currentElement.linkToNext;
            return currentElement.value;
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

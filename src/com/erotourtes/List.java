package com.erotourtes;


public class List<T> {
    private ListElement lastElement;
    private ListElement firstElement;

    public List() {
        firstElement = new ListElement();
        lastElement =  firstElement;
    }

    public void add(T value) {
        var newItem = new ListElement<T>();

        if(firstElement.linkToNext == null) {
            firstElement.value = value;
            firstElement.linkToNext = newItem;
            lastElement = newItem;
            return;
        }

        lastElement.value = value;
        lastElement.linkToNext = newItem;
        lastElement = newItem;
    }

    @Override
    public String toString() {
        var el = firstElement;

        String values = "{";

        while(el.linkToNext != null) {
            values += el.value + ", ";
            el = el.linkToNext;
        }

        values += "}";
        return values;
    }

    private class ListElement<T> {
        private T value;
        private ListElement linkToNext;

        public void setValue(T value) {
            this.value = value;
        }

    }
}
package com.erotourtes;


public class Main {

    public static void main(String[] args) {
        var list = new List<Integer>();

        list.addByIndex(0, 1);
        list.info();

        list.addByIndex(1, 2);
        list.info();

        list.addByIndex(1, 3);
        list.info();

        System.out.println(list);
    }
}


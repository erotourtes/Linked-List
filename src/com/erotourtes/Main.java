package com.erotourtes;


public class Main {

    public static void main(String[] args) {
        var greatList = new List<Integer>();

        for (int i = 0; i < 1; i++) {
            greatList.push(i);
        }
        System.out.println(greatList);
        System.out.println(greatList.hasLoop());
    }
}


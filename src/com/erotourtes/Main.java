package com.erotourtes;


public class Main {

    public static void main(String[] args) {
        var list = new List<Integer>();

        list.push(1);
        list.push(2);
        list.push(3);
        list.push(4);
        list.info();

        for(var el : list)
            System.out.println(el);

        list.removeByIndex(2);
        list.shift();
        list.pop();
        list.info();


        System.out.println(list);
    }
}


package com.evo;

public class TestClass {

    public static void main(String[] args) {
        Thread thread = new Thread(new Game());
        thread.start();
    }

}
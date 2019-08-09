package com.evo;

public class Launcher {

    public static void main(String[] args) {
        Thread thread = new Thread(new Game());
        thread.start();
    }

}
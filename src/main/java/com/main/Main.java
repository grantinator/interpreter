package main.java.com.main;

import main.java.com.repl.Repl;

public class Main {
    public static void main(String[] args) {
        System.console().printf("Hey~ \u1F42E. Welcome to KushLang...\nIts blaaaaazing fast!\n");
        System.console().printf("Go ahead, %s, type in some commands \n", System.getProperty("user.name"));
        Repl.start();
    }
}

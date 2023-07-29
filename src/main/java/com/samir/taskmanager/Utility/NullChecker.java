package com.samir.taskmanager.Utility;

public class NullChecker {
    public static String check(String name) {
        return name.matches("^\\s*$") ? null : name;
    }
}

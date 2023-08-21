package com.samir.taskmanager.Utility;

import java.util.LinkedHashMap;
import java.util.Map;

public class IntegerToRoman {
    public static String RomanNumerals(int Int) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("m", 1000);
        roman_numerals.put("cm", 900);
        roman_numerals.put("d", 500);
        roman_numerals.put("cd", 400);
        roman_numerals.put("c", 100);
        roman_numerals.put("xc", 90);
        roman_numerals.put("l", 50);
        roman_numerals.put("xl", 40);
        roman_numerals.put("x", 10);
        roman_numerals.put("ix", 9);
        roman_numerals.put("v", 5);
        roman_numerals.put("iv", 4);
        roman_numerals.put("i", 1);
        String res = "";
        for (Map.Entry<String, Integer> entry : roman_numerals.entrySet()) {
            int matches = Int / entry.getValue();
            res += repeat(entry.getKey(), matches);
            Int = Int % entry.getValue();
        }
        return res;
    }

    public static String repeat(String s, int n) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        String roman = sb.toString();
        return roman;
    }
}

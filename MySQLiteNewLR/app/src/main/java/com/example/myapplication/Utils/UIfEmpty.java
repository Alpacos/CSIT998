package com.example.myapplication.Utils;

public class UIfEmpty {
    public static boolean ifEmpty(String... params) {
        for (int i = 0; i < params.length; i++) {
            if(params[0].length() == 0) {
                return true;
            }
        }
        return false;
    }
}

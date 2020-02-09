package com.okchain.common;

import java.util.regex.Pattern;

public class StrUtils {
    public static boolean isInteger(String str) {
        if (str == null || str.equals("")) return false;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumeric(String str){
        if (str == null || str.equals("")) return false;
        Pattern pattern = Pattern.compile("^[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static boolean isDecimal(String str, int n) {
        if (str == null || str.equals("")) return false;
        Pattern pattern = Pattern.compile("^[0-9]*\\.[0-9]{"+n+"}");
        return pattern.matcher(str).matches();
    }

    public static boolean isProduct(String str) {
        if (str == null || str.equals("")) return false;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-]+_[a-zA-Z0-9-]+$");
        return pattern.matcher(str).matches();
    }

    public static boolean isProductSide(String str) {
        if (str == null || str.equals("")) return false;
        if (str.equals("BUY") || str.equals("SELL")) return true;
        return false;
    }

}

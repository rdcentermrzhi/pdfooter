package com.jinrong.pdf.utils;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return !(str != null && str.length() != 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trimAll(String str) {
        return str.replaceAll(" ", "");
    }

    public static String escape4Mysql(String str) {
        return str.replaceAll("'", "\\\\'");
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
                ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS ||
                ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
            return true;
        return false;
    }

    public static String capital(String str) {
        char[] chars = new char[str.length()];
        chars[0] = Character.toUpperCase(str.charAt(0));
        for (int i = 1; i < str.length(); i++)
            chars[i] = str.charAt(i);
        return new String(chars);
    }
}

package big.helper;

import java.util.*;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class Utils {

    public static int c2i(char c) {
        return c - '0';
    }

    public static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

    public static void crash(String s, Object... o) {
        throw new RuntimeException(String.format(Locale.CANADA, s, o));
    }

    public static long getEnding(List<String> numbers, int endingSize) {
        if (endingSize > numbers.get(0).length()) {
            print("Size %d too big for getEnding(), using full length", endingSize);
            endingSize = numbers.get(0).length() - 1;
        }
        long ending = 0;
        for (String s : numbers) {
            ending += Long.parseLong(s.substring(s.length() - endingSize, s.length()));
        }
        ending %= Math.pow(10, endingSize);
        return ending;
    }

    /**
     * Custom implementation of integer summation using Strings
     * @param set list of Strings holding the integers to be added
     * @return sum as a String
     */
    public static String sum(LinkedList<String> set) {
        set = (LinkedList<String>) set.clone();
        int carry = 0, digit;
        String result = "";
        while (!set.isEmpty()) {
            digit = carry % 10; //add carries
            carry /= 10; //remove old carry
            ListIterator<String> i = set.listIterator();
            while (i.hasNext()) {
                String s = i.next();
                digit += c2i(s.charAt(s.length() - 1));
                s = s.substring(0, s.length() - 1);
                if (s.isEmpty()) {
                    i.remove();
                } else {
                    i.set(s);
                }
            }
            carry += digit / 10;
            result = (digit % 10) + result;
        }
        if (carry > 0) result = carry + result;
        return result;
    }

}

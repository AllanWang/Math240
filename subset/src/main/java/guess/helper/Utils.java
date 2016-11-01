package guess.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

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

    public static int digitSize(long l) {
        return String.valueOf(l).length();
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
     *
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

    public static String diff(String s1, String s2) {
        while (s2.length() < s1.length()) {
            s2 = "0" + s2;
        }
        while (s1.length() < s2.length()) {
            s1 = "0" + s1;
        }
        if (s1.compareTo(s2) < 0) {
            String temp = s1;
            s1 = s2;
            s2 = temp;
        }
        String result = "";
        boolean take = false; //no carry to start off with
        for (int i = s1.length() - 1; i >= 0; i--) {
            int digitDiff = c2i(s1.charAt(i));
            digitDiff -= c2i(s2.charAt(i));
            if (take) digitDiff--;
            take = digitDiff < 0;
            if (take) digitDiff += 10;
            result = digitDiff + result;
        }
        while (result.charAt(0) == '0') {
            result = result.substring(1);
        }
        return result;
    }

}

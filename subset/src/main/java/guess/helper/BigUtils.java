package guess.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class BigUtils {

    public static BigInteger[] getArray(String[] array) {
        BigInteger[] newArray = new BigInteger[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = new BigInteger(array[i]);
        }
        return newArray;
    }

    public static BigInteger trim(List<BigInteger> list) {
        BigInteger smallest = null;
        for (BigInteger i : list) {
            if (smallest == null) smallest = i;
            if (smallest.compareTo(i) > 0) smallest = i;
        }
        for (ListIterator<BigInteger> iterator = list.listIterator(); iterator.hasNext(); ) {
            BigInteger bigInt = iterator.next();
            if (bigInt.equals(smallest)) {
                iterator.remove();
            } else {
                iterator.set(bigInt.subtract(smallest));
            }
        }
        return smallest;
    }

    public static String sumString(List<BigInteger> list) {
        return compress(sum(list));
    }

    public static BigInteger sum(List<BigInteger> list) {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger bigInt : list) {
            sum = sum.add(bigInt);
        }
        return sum;
    }

    public static String compress(BigInteger bigInt) {
        return bigInt.toString(Character.MAX_RADIX);
    }

    public static List<String> compress(List<BigInteger> list) {
        List<String> result = new ArrayList<>(list.size());
        for (BigInteger bigInt : list) {
            result.add(compress(bigInt));
        }
        return result;
    }

    public static BigInteger expand(String s) {
        return new BigInteger(s, Character.MAX_RADIX);
    }

    public static List<BigInteger> expand(List<String> list) {
        List<BigInteger> result = new ArrayList<>(list.size());
        for (String s : list) {
            result.add(expand(s));
        }
        return result;
    }

    public static LinkedList tdFactors(BigInteger n) {
        BigInteger two = BigInteger.valueOf(2);
        LinkedList<BigInteger> fs = new LinkedList<>();

        if (n.compareTo(two) < 0) {
            throw new IllegalArgumentException("must be greater than one");
        }

        while (n.mod(two).equals(BigInteger.ZERO)) {
            fs.add(two);
            n = n.divide(two);
        }

        if (n.compareTo(BigInteger.ONE) > 0) {
            BigInteger f = BigInteger.valueOf(3);
            while (f.multiply(f).compareTo(n) <= 0) {
                if (n.mod(f).equals(BigInteger.ZERO)) {
                    fs.add(f);
                    n = n.divide(f);
                } else {
                    f = f.add(two);
                }
            }
            fs.add(n);
        }

        return fs;
    }

}

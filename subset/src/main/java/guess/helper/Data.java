package guess.helper;

import com.google.common.collect.BiMap;
import guess.BigNumbers;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.lucene.util.OpenBitSet;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class Data {
    private BigInteger[] numbers = BigNumbers.binary;
    private HashMap<Byte, BiMap<String, OpenBitSet>> maps = new HashMap<>();
    private final BigInteger MINUS_ONE = BigInteger.valueOf(-1);

    public void getSparseSums() {
        BigInteger sum = BigInteger.ZERO;
        String key;
        String value = "";
        int range;
        for (int z = 0; z < 6; z++) { //skipping loop
            range = numbers.length + z;
            for (int i = z + 1; i < range - 1; i++) {
                Utils.print("i %d", i);
                for (int j = 0; j < Utils.lcm(range, i); j++) {
                    if (j % i == 0) { //number of digits reached, push sum
                        key = sum.toString(Character.MAX_RADIX);
                        if (sparse.containsKey(key)) {
                            Utils.print("Match sum %s, values %s %s", sum.toString(), value, sparse.get(key));
                            return;
                        }
                        sparse.put(key, value);
                        value = ""; //reset key
                    }
                    sum = sum.add(numbers[j % numbers.length]); //add number
                    OpenBitSet
                }
            }
        }
    }


    private String mappable(boolean[] array) {
        String s = "";
        for (boolean b : array) {
            if (b) {
                s += "0"
                        Character.MAX_VALUE
            }
        }
    }

    private BigInteger getSum(BigInteger prevSum, int border, BigInteger counter) {
        if (counter.equals(MINUS_ONE)) return MINUS_ONE;
        if (!prevSum.equals(BigInteger.ZERO)) {
            String key = prevSum.toString(Character.MAX_RADIX);
            if (map.containsKey(key)) {
                callback.onMatch(prevSum, null, null);
                return MINUS_ONE;
            }
            map.put(key, counter);
        }

//        Utils.print("Counter %s list %s", counter.toString(), Arrays.toString(getList(counter)));
        for (int i = border + 1; i < numbers.length; i++) {
            counter = getSum(prevSum.add(numbers[i]), i, counter);
        }
        return counter;
    }

    private BigInteger[] getList(BigInteger counter) {
        BigInteger combinationLength = BigInteger.valueOf(numbers.length);
        int index = 0;
        BigInteger[] temp = new BigInteger[numbers.length];
        while (counter.compareTo(combinationLength) > 0) {
            counter = counter.subtract(combinationLength);
            combinationLength = combinationLength.subtract(BigInteger.ONE);
            temp[index] = numbers[index];
            index++;
        }
        temp[index] = numbers[index + counter.intValue() - 1];
        return Arrays.copyOf(temp, index + 1);
    }

//    public void sweep(long guess, Callback callback) {
//        long modulus;
//        int digitSize = Utils.digitSize(guess);
//        if (digitSize > numberArrays.croppedDigitSize) {
//            modulus = (long) Math.pow(10, numberArrays.croppedDigitSize);
//            guess %= modulus;
//        } else {
//            modulus = (long) Math.pow(10, digitSize);
//        }
//        for (Iterator<int[]> iter = new Combinations(numberArrays.size, numberArrays.digitSize).iterator(); iter.hasNext(); ) {
//            System.out.println(Arrays.toString(iter.next()));
//        }
//
//    }
//
//    public void iterateTail() {
//        for (int k = 1; k <= numberArrays.size; k++) {
//
//        }
//    }

}

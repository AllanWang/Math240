package guess;

import guess.helper.ProgressReport;
import guess.helper.Utils;
import org.apache.lucene.util.OpenBitSet;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Allan Wang on 2016-11-11.
 */
public class Luck {

    private static BigInteger[] numbers = BigNumbers.numbersShuffled;
    private static HashMap<BigInteger, OpenBitSet> map = new HashMap<>();
    private static ProgressReport mReport = new ProgressReport(15, () -> {
        print("Progress: %d entries", map.keySet().size());
    });

    public static void main(String[] args) {
        checkLuck(21);
    }

    private static void checkLuck(int luckyNumber) {
        BigInteger sum = BigInteger.ZERO;
        Random rnd = new Random();
        OpenBitSet bitSet = new OpenBitSet(numbers.length);
        mReport.start();
        boolean isLucky = false;
        for (int i = 0; !isLucky; i = (i + 1) % numbers.length) {
            if (bitSet.get(i)) continue;
            if (rnd.nextBoolean()) {
                bitSet.fastSet(i);
                sum = sum.add(numbers[i]);
            }
            if (bitSet.cardinality() == luckyNumber) {
                isLucky = check(sum, bitSet);
//                if (bitSet.cardinality() >= luckyNumber + 1) {
                sum = BigInteger.ZERO;
                bitSet = new OpenBitSet(numbers.length);
//                }
            }
        }
    }

    private static void checkLuck2(int luckyNumber) {
        BigInteger sum = BigInteger.ZERO;
        Random rnd = new Random();
        OpenBitSet bitSet = new OpenBitSet(numbers.length);
        mReport.start();
        boolean isLucky = false;
        while (!isLucky) {
            while (bitSet.cardinality() < luckyNumber) {
                int index = rnd.nextInt(numbers.length);
                if (bitSet.get(index)) continue;
                bitSet.fastSet(index);
                sum = sum.add(numbers[index]);
            }
            isLucky = check(sum, bitSet);
            int remove = rnd.nextInt(luckyNumber);
            int index = 0;
            for (int i = 0; i < remove; i++) {
                index = bitSet.nextSetBit(index);
            }
            bitSet.fastClear(index);
            sum = sum.subtract(numbers[index]);
        }
    }

    private static boolean check(final BigInteger sum, final OpenBitSet bitSet) {
        if (map.containsKey(sum)) {
            if (!map.get(sum).equals(bitSet)) {
                print("Match found for sum %s\n\t%s\n\t%s", sum.toString(), Utils.bitString(bitSet, numbers), Utils.bitString(map.get(sum), numbers));
                mReport.stop();
                return true;
            }
        } else {
            map.put(sum, bitSet);
        }
        return false;
    }

    private static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

    private static void crash(String s, Object... o) {
        throw new RuntimeException(String.format(Locale.CANADA, s, o));
    }

}

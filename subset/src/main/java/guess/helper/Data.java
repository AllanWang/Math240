package guess.helper;

import com.google.common.collect.HashBiMap;
import guess.data.NumberArrays;
import org.apache.commons.math3.util.Combinations;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class Data implements Serializable {
    private NumberArrays numberArrays;
    private HashBiMap<String, String> map = HashBiMap.create();
    private BigInteger counter;
    private Callback callback;

    public Data(String[] numbers) {
        numberArrays = new NumberArrays(numbers);
    }

    public interface Callback {
        void onMatch(BigInteger sum, BigInteger[] list1, BigInteger[] list2);

        void noMatch();
    }

    public void sweep(long guess, Callback callback) {
        long modulus;
        int digitSize = Utils.digitSize(guess);
        if (digitSize > numberArrays.croppedDigitSize) {
            modulus = (long) Math.pow(10, numberArrays.croppedDigitSize);
            guess %= modulus;
        } else {
            modulus = (long) Math.pow(10, digitSize);
        }
        for (Iterator<int[]> iter = new Combinations(numberArrays.size, numberArrays.digitSize).iterator(); iter.hasNext();) {
            System.out.println(Arrays.toString(iter.next()));
        }

    }

    public void iterateTail() {
        for(int k = 1; k <= numberArrays.size; k++) {

        }
    }

}

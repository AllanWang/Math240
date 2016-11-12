package guess.helper;

import org.apache.lucene.util.OpenBitSet;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Allan Wang on 2016-11-11.
 */
public class Utils {

    public static String bitString(OpenBitSet bitSet, BigInteger[] fullList) {
        BigInteger[] array = new BigInteger[(int) bitSet.cardinality()];
        int ii = -1;
        for (int i = 0; i < array.length; i++) {
            ii = bitSet.nextSetBit(ii + 1);
            array[i] = fullList[ii];
        }
        return Arrays.toString(array);
    }

}

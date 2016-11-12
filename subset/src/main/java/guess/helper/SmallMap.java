package guess.helper;

import org.apache.lucene.util.OpenBitSet;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Allan Wang on 2016-11-03.
 */
public class SmallMap extends HashMap<Integer, OpenBitSet> {
    private Integer mLargest = 0;

    /**
     * Attempts to put value in map
     *
     * @param key
     * @param value
     * @return true for success, false if there is already a value
     */
    @Override
    public OpenBitSet put(Integer key, OpenBitSet value) {
        if (super.containsKey(key)) return null;
        super.put(key, value);
        if (key > mLargest) mLargest = key; //new largest value in set
        return value;
    }

    @Override
    public boolean containsKey(Object o) {
        if (!(o instanceof Integer)) return false;
        Integer i = (Integer) o;
        return i <= mLargest && super.containsKey(o);
    }

    /**
     * Gets the value of a key, but as a readable string
     * Must be a valid bitset for the list given
     *
     * @param key
     * @return
     */
    public String getString(Integer key, Integer[] fullList) {
        OpenBitSet bitSet = get(key);
        if (bitSet == null) return null;
        return bitString(bitSet, fullList);
    }

    public static String bitString(OpenBitSet bitSet, Integer[] fullList) {
        Integer[] array = new Integer[(int) bitSet.cardinality()];
        int ii = -1;
        for (int i = 0; i < array.length; i++) {
            ii = bitSet.nextSetBit(ii + 1);
            array[i] = fullList[ii];
        }
        return Arrays.toString(array);
    }
}

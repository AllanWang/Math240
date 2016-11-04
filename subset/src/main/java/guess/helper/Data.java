package guess.helper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.sun.istack.internal.NotNull;
import guess.BigNumbers;
import org.apache.lucene.util.OpenBitSet;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Allan Wang on 2016-10-31.
 * <p>
 * Class that will quickly compute all valid subset sums starting from size 1 to size numbers.length
 * Calculating by set size will hopefully result in a faster match
 * Most functions that return a boolean return true for a match and false for otherwise
 */
public class Data {
    private final BigInteger[] numbers;
    private final List<BiMap<BigInteger, OpenBitSet>> mapList;
    private Timer mTimer;

    private Callback callback;

    public static void main(String[] args) {
        new Data(BigNumbers.numbersSorted).getAllSums(new Callback() {
            @Override
            public void onMatch(BigInteger sum, OpenBitSet set1, OpenBitSet set2) {

            }

            @Override
            public void onSubSetFinish(int size, BiMap<BigInteger, OpenBitSet> map) {
                print("Getting subsets of size %d...", size);
            }

            @Override
            public void onNoMatch(List<BiMap<BigInteger, OpenBitSet>> mapList) {
                print("No match");
                for (BiMap<BigInteger, OpenBitSet> subsets : mapList) {
                    for (BigInteger sum : subsets.keySet()) {
                        print("Sum %s, bitset %s", sum.toString(), s(subsets.get(sum)));
                    }
                }
            }
        });
    }

    public interface Callback {
        void onMatch(BigInteger sum, OpenBitSet set1, OpenBitSet set2);
        void onSubSetFinish(int size, BiMap<BigInteger, OpenBitSet> map);
        void onNoMatch(List<BiMap<BigInteger, OpenBitSet>> mapList);
    }

    public boolean getAllSums(@NotNull Callback callback) {
        this.callback = callback;
        for (int i = 1; i < numbers.length; i++) { //get subsets by size i
            print("Getting subsets of size %d...", i);
            if (mapOfSize(i)) return true;
            callback.onSubSetFinish(i, mapList.get(i-1));
        }
        callback.onNoMatch(mapList);
        return false;
    }

    public Data(BigInteger[] numbers) {
        this.numbers = numbers;
        mapList = new ArrayList<>(numbers.length);
        for (BigInteger number : numbers) { //initialize each list
            mapList.add(HashBiMap.create());
        }
    }

    private boolean mapOfSize(int size) {
        if (size < 1) {
            crash("Size too small: %d", size);
        }
        OpenBitSet bitSet;
        if (size == 1) {
            for (int i = 0; i < numbers.length; i++) {
                bitSet = new OpenBitSet(numbers.length);
                bitSet.set(i);
                if (push(numbers[i], bitSet)) return true;
            }
        } else {
            if (mapList.get(size - 2) != null) {
                if (getSubsetSums(size - 1, mapList.get(size - 2))) return true;
            }
        }
        return false;
    }

    private boolean getSubsetSums(int oldCardinality, BiMap<BigInteger, OpenBitSet> oldMap) {
        for (BigInteger prevSum : oldMap.keySet()) {
            OpenBitSet prevBitSet = (OpenBitSet) oldMap.get(prevSum).clone();
            int lastSet = -1;
            for (int i = 0; i < oldCardinality; i++) {
                lastSet = prevBitSet.nextSetBit(lastSet + 1);
            }
            for (int i = lastSet + 1; i < numbers.length; i++) {
                if (getSum(prevSum, prevBitSet, i)) return true;
            }
        }
        return false;
    }

    private boolean getSum(BigInteger prevSum, OpenBitSet prevBitSet, int toggle) {
        OpenBitSet newBitSet = (OpenBitSet) prevBitSet.clone();
        newBitSet.set(toggle);
        return push(prevSum.add(numbers[toggle]), newBitSet);
    }

    private boolean push(BigInteger sum, OpenBitSet key) {
        int index = (int) key.cardinality();
        for (BiMap<BigInteger, OpenBitSet> bimap : mapList) {
            if (bimap.containsKey(sum)) {
                print("Match found for sum %s\n\t%s\n\t%s", sum.toString(), Arrays.toString(getNumbers(key)), Arrays.toString(getNumbers(bimap.get(sum))));
                callback.onMatch(sum, (OpenBitSet) bimap.get(sum), key);
                return true;
            }
        }
        mapList.get(index - 1).put(sum, key);
        return false;
    }

    private static String s(OpenBitSet bitSet) {
        return Arrays.toString(bitSet.getBits());
    }

    private BigInteger[] getNumbers(OpenBitSet bitSet) {
        BigInteger[] array = new BigInteger[(int) bitSet.cardinality()];
        int ii = -1;
        for (int i = 0; i < array.length; i++) {
            ii = bitSet.nextSetBit(ii + 1);
            array[i] = numbers[ii];
        }
        return array;
    }

    public static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

    public static void crash(String s, Object... o) {
        throw new RuntimeException(String.format(Locale.CANADA, s, o));
    }


}

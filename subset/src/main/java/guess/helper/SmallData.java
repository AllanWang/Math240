package guess.helper;

import com.sun.istack.internal.NotNull;
import guess.BigNumbers;
import org.apache.lucene.util.OpenBitSet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Allan Wang on 2016-10-31.
 * <p>
 * Class that will quickly compute all valid subset sums starting from size 1 to size numbers.length
 * Calculating by set size will hopefully result in a faster match
 * Most functions that return a boolean return true for a match and false for otherwise
 */
public class SmallData {
    private final Integer[] numbers;
    private final List<SmallMap> mapList = new ArrayList<>();
    private SmallMap mRandMap = new SmallMap();
    private int currentSubsetSize;
    private ProgressReport mReport = new ProgressReport(15, () -> {
        if (currentSubsetSize > 0) {
            print("Progress: subset %d, %d entries", currentSubsetSize, mapList.get(currentSubsetSize - 1).keySet().size());
        } else {
            print("Progress: %d entries", mRandMap.keySet().size());
        }
    });

    private Callback callback;

    public static void main(String[] args) {
        new SmallData(BigNumbers.list_10000).getAllSums(new Callback() {
            @Override
            public void onMatch(Integer sum, OpenBitSet set1, OpenBitSet set2) {
            }

            @Override
            public void onSubSetFinish(int size, SmallMap map) {
//                print("Getting subsets of size %d...", size);
            }

            @Override
            public void onNoMatch(List<SmallMap> mapList) {
                print("No match");
                for (SmallMap subsets : mapList) {
                    for (Integer sum : subsets.keySet()) {
                        print("Sum %s, bitset %s", sum.toString(), s(subsets.get(sum)));
                    }
                }
            }
        });
    }

    public SmallData(Integer[] numbers) {
        this.numbers = numbers;
    }

    public interface Callback {
        void onMatch(Integer sum, OpenBitSet set1, OpenBitSet set2);

        void onSubSetFinish(int size, SmallMap map);

        void onNoMatch(List<SmallMap> mapList);
    }

    public boolean getAllSums(@NotNull Callback callback) {
        for (Integer number : numbers) { //initialize each list
            mapList.add(new SmallMap());
        }
        this.callback = callback;
        mReport.start();
        for (currentSubsetSize = 1; currentSubsetSize < numbers.length; currentSubsetSize++) { //get subsets by size i
            print("Getting subsets of size %d...", currentSubsetSize);
            if (mapOfSize(currentSubsetSize)) return true;
            callback.onSubSetFinish(currentSubsetSize, mapList.get(currentSubsetSize - 1));
        }
        mReport.stop();
        callback.onNoMatch(mapList);
        return false;
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

    private void randomMapOfSize(int size) {
        currentSubsetSize = -size;
        mReport.start();
        Integer sum = 0;
        OpenBitSet bitSet = new OpenBitSet(numbers.length);
        boolean skip = false;
        for (int i = 0; ; i = (i + 1) % numbers.length) {
            if (bitSet.get(i)) continue;
            if (skip) {
                switch ((int) bitSet.cardinality()) {
                    case 7:
                    case 11:
                    case 13:
                        skip = false;
                        continue;
                }
            } else {
                skip = true;
            }
            bitSet.fastSet(i);
            sum += numbers[i];
            if (bitSet.cardinality() == size) { //subset size reached
                if (mRandMap.containsKey(sum)) {
                    if (mRandMap.get(sum).equals(bitSet)) {
                        print("Repeat");
                    } else {
                        print("Match found for sum %s\n\t%s\n\t%s", sum.toString(), SmallMap.bitString(bitSet, numbers), mRandMap.getString(sum, numbers));
                        mReport.stop();
                        return;
                    }
                } else {
                    mRandMap.put(sum, bitSet);
                }
                sum = 0;
                bitSet = new OpenBitSet(numbers.length);
            }
        }

    }

    private boolean getSubsetSums(int oldCardinality, SmallMap oldMap) {
        for (Integer prevSum : oldMap.keySet()) {
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

    private boolean getSum(Integer prevSum, OpenBitSet prevBitSet, int toggle) {
        OpenBitSet newBitSet = (OpenBitSet) prevBitSet.clone();
        newBitSet.set(toggle);
        return push(prevSum + numbers[toggle], newBitSet);
    }

    private boolean push(Integer sum, OpenBitSet key) {
        int index = (int) key.cardinality();
        for (SmallMap map : mapList) {
            if (map.containsKey(sum)) {
                print("Match found for sum %s\n\t%s\n\t%s", sum.toString(), SmallMap.bitString(key, numbers), map.getString(sum, numbers));
                callback.onMatch(sum, map.get(sum), key);
                return false;
            }
        }
        mapList.get(index - 1).put(sum, key);
        return false;
    }

    private static String s(OpenBitSet bitSet) {
        return Arrays.toString(bitSet.getBits());
    }

    private static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

    private static void crash(String s, Object... o) {
        throw new RuntimeException(String.format(Locale.CANADA, s, o));
    }


}

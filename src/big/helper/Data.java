package big.helper;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

import static big.helper.Utils.print;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class Data implements Serializable {
    private BigInteger[] numbers;
    private HashMap<String, String> map = new HashMap<>();
    private BigInteger counter;
    private Callback callback;

    public Data(BigInteger[] numbers) {
        this.numbers = numbers;
    }

    public interface Callback {
        void onMatch(BigInteger sum, BigInteger[] list1, BigInteger[] list2);

        void noMatch();
    }

    public void findMatch(Callback callback) {
        findMatch(1, numbers.length, callback);
    }

    public void findMatch(int minSetSize, int maxSetSize, Callback callback) {
        this.callback = callback;
        maxSetSize = Math.max(minSetSize, maxSetSize);
        minSetSize = Math.min(minSetSize, maxSetSize);
        if (maxSetSize > numbers.length) {
            print("Max size too big, defaulting to list length");
            maxSetSize = numbers.length;
        }
        if (minSetSize <= 0) {
            minSetSize = 1;
        }
        for (int i = minSetSize; i <= maxSetSize; i++) {
            getSum(repositionCounter(minSetSize), -1, i);
        }
        if (callback != null) callback.noMatch();
    }

    private BigInteger repositionCounter(int minSetSize) {
        counter = BigInteger.ZERO;
        if (minSetSize == 1) return BigInteger.ZERO;
        for (int i = 1; i < minSetSize; i++) {
            counter = counter.add(combination(i));
        }
        BigInteger preSum = BigInteger.ZERO;
        for (int i = 1; i < minSetSize; i++) {
            preSum = preSum.add(numbers[i - 1]);
        }
        print("MinSetSize is %d, starting at counter %d with sum %s", minSetSize, counter, preSum.toString());
        return preSum;
    }

    private BigInteger combination(int choose) {
        int size = numbers.length;
        if (choose > size / 2) choose = size - choose; //they are equivalent
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < choose; i++) {
            result = result.multiply(BigInteger.valueOf(size - i));
        }
        while (choose > 1) {
            result = result.divide(BigInteger.valueOf(choose));
            choose--;
        }
        return result;
    }

    private synchronized void incrementCounter() {
        counter = counter.add(BigInteger.ONE);
    }

    private BigInteger addKey(BigInteger sum, BigInteger status) {
        String key = sum.toString(Character.MAX_RADIX);
        if (map.containsKey(key))
            return new BigInteger(map.get(key), Character.MAX_RADIX); //hopefully conversion will save space
        map.put(key, status.toString(Character.MAX_RADIX));
        return null;
    }

//    private long statusCode(boolean[] status) {
//        long key = 0;
//        for (boolean b : status) {
//            key *= 2;
//            if (b) key++;
//        }
//        return key;
//    }
//
//    private boolean[] statusDecode(long key) {
//        boolean[] status = new boolean[numbers.length];
//        for (int i = status.length - 1; i >= 0; i--) {
//            status[i] = (key % 2 == 1);
//            key /= 2;
//        }
//        return status;
//    }

    private BigInteger[] getNumberList(BigInteger counter) {
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

    private synchronized void getSum(BigInteger prevSum, int border, int toGo) {
        if (callback == null) return;
        if (toGo == 0) {
            print("Counter %s sum %s", counter, prevSum);
            BigInteger match = addKey(prevSum, counter);
            if (match != null) {
                callback.onMatch(prevSum, getNumberList(match), getNumberList(counter));
                callback = null; //stop recursion
            }
            return;
        }
        toGo--;
        for (int i = border + 1; i < numbers.length - toGo; i++) {
            incrementCounter(); //synchronized counter to match position
            getSum(prevSum.add(numbers[i]), i, toGo);
        }
    }
}

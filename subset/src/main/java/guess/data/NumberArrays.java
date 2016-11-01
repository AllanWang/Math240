package guess.data;

import guess.helper.Utils;

import java.math.BigInteger;

/**
 * Created by Allan Wang on 2016-11-01.
 */
public class NumberArrays {
    public final Number[] numbers;
    //    public final long maxModulus;
    public final int size, digitSize, croppedDigitSize;

    public NumberArrays(String[] stringArray) {
        size = stringArray.length;
        digitSize = stringArray[0].length();
        croppedDigitSize = (String.valueOf(Long.MAX_VALUE).length() - 1) / (String.valueOf(size).length() + 1 + digitSize); //new shortened length to make sure max sum fits long
//        maxModulus = (long) Math.pow(10, croppedDigitSize);
        numbers = new Number[size];
        String s;
        for (int i = 0; i < size; i++) {
            s = stringArray[i];
            if (s.length() != digitSize)
                Utils.crash("Inconsistent digit size, please make all numbers the same length");
            numbers[i] = new Number(new BigInteger(s), Long.parseLong(s.substring(digitSize - croppedDigitSize)));
        }
    }

    public class Number {
        public final BigInteger bigInt;
        public final long tail;

        public Number(BigInteger bigInt, long tail) {
            this.bigInt = bigInt;
            this.tail = tail;
        }

        @Override
        public String toString() {
            return bigInt.toString();
        }
    }
}

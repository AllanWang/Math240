package guess.helper;

import org.apache.lucene.util.OpenBitSet;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Allan Wang on 2016-11-03.
 */
public class BigMap extends HashMap<BigInteger, OpenBitSet> {
    private BigInteger mLargest = BigInteger.ZERO;

    /**
     * Attempts to put value in map
     *
     * @param key
     * @param value
     * @return true for success, false if there is already a value
     */
    @Override
    public OpenBitSet put(BigInteger key, OpenBitSet value) {
        if (super.containsKey(key)) return null;
        super.put(key, value);
        if (key.compareTo(mLargest) > 0) mLargest = key; //new largest value in set
        return value;
    }

    @Override
    public boolean containsKey(Object o) {
        if (!(o instanceof BigInteger)) return false;
        BigInteger bigInt = (BigInteger) o;
        if (bigInt.compareTo(mLargest) > 0) return false;
        return super.containsKey(o);
    }

    /**
     * Gets the value of a key, but as a readable string
     * Must be a valid bitset for the list given
     *
     * @param key
     * @return
     */
    public String getString(BigInteger key, BigInteger[] fullList) {
        OpenBitSet bitSet = get(key);
        if (bitSet == null) return null;
        return bitString(bitSet, fullList);
    }

    public static String bitString(OpenBitSet bitSet, BigInteger[] fullList) {
        BigInteger[] array = new BigInteger[(int) bitSet.cardinality()];
        int ii = -1;
        for (int i = 0; i < array.length; i++) {
            ii = bitSet.nextSetBit(ii + 1);
            array[i] = fullList[ii];
        }
        return Arrays.toString(array);
    }

    public void saveToFile(String fileName) {
        new Thread(() -> {
            try {

                File f = new File(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
                oos.writeObject(this);
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static BigMap readFromFile(String fileName) {
        try {
            File f = new File(fileName);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            BigMap returnedMap = (BigMap) ois.readObject();
            ois.close();
            return returnedMap;
        } catch (IOException | ClassNotFoundException e) {
            //file not found; continue with new map
        }
        return new BigMap();
    }

}

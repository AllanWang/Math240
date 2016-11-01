package big.helper;

import java.util.HashMap;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class MapUtils<K, V> {
    private HashMap<K, V> mMap;

    public MapUtils() {
        mMap = new HashMap<K, V>();
    }

    public MapUtils(HashMap<K, V> map) {
        mMap = map;
    }

    public V findDup(K key, V value) {
        if (mMap.containsKey(key)) return mMap.get(key);
        mMap.put(key, value);
        return null;
    }
}

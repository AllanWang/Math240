package big.helper;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static big.helper.Utils.print;

/**
 * Created by Allan Wang on 2016-10-31.
 * <p>
 * Loops through all possible subsets (including original set itself) of a given T[]
 */
public class Combinations<T> {

    private final List<T> fullList;

    public Combinations(T[] list) {
        fullList = new LinkedList<T>(Arrays.asList(list));
    }

    public Combinations(List<T> list) {
        fullList = list;
    }

    public interface Callback<T> {
        void onResult(LinkedList<T> list);
    }

    public void getAllCombinations(@NotNull final Callback<T> callback) {
        getAllCombinations(1, fullList.size(), callback);
    }

    public void printAll() {
        getAllCombinations(list -> {
            print(list.toString());
        });
    }

    public void printAll(int minSetSize, int maxSetSize) {
        getAllCombinations(minSetSize, maxSetSize, list -> {
            print(list.toString());
        });
    }

    public void getAllCombinations(int minSetSize, int maxSetSize, @NotNull final Callback<T> callback) {
        maxSetSize = Math.max(minSetSize, maxSetSize);
        minSetSize = Math.min(minSetSize, maxSetSize);
        if (maxSetSize > fullList.size()) {
            print("Max size too big, defaulting to list length");
            maxSetSize = fullList.size();
        }
        if (minSetSize <= 0) {
            minSetSize = 1;
        }
        for (int i = minSetSize; i <= maxSetSize; i++) {
            if (!getSubsets(new LinkedList<>(), -1, i, callback)) break;
        }
    }

    private boolean getSubsets(LinkedList<T> preList, int lastInPre, int toGo, final @NotNull Callback<T> callback) {
        preList = (LinkedList<T>) preList.clone();
        if (toGo == 0) {
            callback.onResult(preList);
            return false;
        }
        preList.addLast(null); //add blank element
        toGo--;
        for (int i = lastInPre + 1; i < fullList.size() - toGo; i++) {
            preList.set(preList.size() - 1, fullList.get(i)); //only switch out last element
            if (!getSubsets(preList, i, toGo, callback)) return false;
        }
        return true;
    }
}

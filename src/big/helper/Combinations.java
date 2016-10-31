package big.helper;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;
import java.util.List;

import static big.helper.Utils.print;

/**
 * Created by Allan Wang on 2016-10-31.
 * <p>
 * Loops through all possible subsets (including original set itself) of a given String[]
 */
public class Combinations {

    private final String[] fullList;

    public Combinations(String[] list) {
        fullList = list;
    }

    public interface Callback {
        void onResult(LinkedList<String> list);
    }

    public void getAllCombinations(@NotNull final Callback callback) {
        getAllCombinations(1, fullList.length, callback);
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

    public void getAllCombinations(int minSetSize, int maxSetSize, @NotNull final Callback callback) {
        maxSetSize = Math.max(minSetSize, maxSetSize);
        minSetSize = Math.min(minSetSize, maxSetSize);
        if (maxSetSize > fullList.length) {
            print("Max size too big, defaulting to list length");
            maxSetSize = fullList.length;
        }
        if (minSetSize <= 0) {
            minSetSize = 1;
        }
        for (int i = minSetSize; i <= maxSetSize; i++) {
            getSubsets(new LinkedList<>(), -1, i, callback);
        }
    }

    public void getSubsets(LinkedList<String> preList, int lastInPre, int toGo, final @NotNull Callback callback) {
        preList = (LinkedList<String>) preList.clone();
        if (toGo == 0) {
            callback.onResult(preList);
            return;
        }
        preList.addLast(" "); //add blank element
        toGo--;
        for (int i = lastInPre + 1; i < fullList.length - toGo; i++) {
            preList.set(preList.size() - 1, fullList[i]); //only switch out last element
            getSubsets(preList, i, toGo, callback);
        }
    }
}

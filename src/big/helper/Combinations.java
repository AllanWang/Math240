package big.helper;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Allan Wang on 2016-10-31.
 *
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
        for (int i = 1; i <= fullList.length; i++) {
            getSubsets(new LinkedList<>(), -1, i, callback);
        }
    }

    private void getSubsets(LinkedList<String> preList, int lastInPre, int toGo, final @NotNull Callback callback) {
        if (toGo == 0) {
            callback.onResult(preList);
            return;
        }
        preList = (LinkedList<String>) preList.clone();
        preList.addLast(" "); //add blank element
        toGo--;
        for (int i = lastInPre + 1; i < fullList.length - toGo; i++) {
            preList.set(preList.size() - 1, fullList[i]); //only switch out last element
            getSubsets(preList, i, toGo, callback);
        }
    }
}

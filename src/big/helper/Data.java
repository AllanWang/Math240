package big.helper;

import sun.awt.image.ImageWatched;

import java.util.*;

import static big.helper.Utils.*;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class Data {

    public static void sort(String[] list) {
        Arrays.sort(list);
        print("private static String[] numbers = {");
        for (String s : list) {
            print("\t\"" + s + "\",");
        }
        print("};");
    }

    public static void diffCheck(String[] full) {
        HashMap<String, ArrayList<LinkedList<String>>> diffMap = new HashMap<>();
        new Combinations(full).getSubsets(new LinkedList<>(), -1, 2, list -> {
            String diff = diff(list.get(0), list.get(1));
            if (diffMap.containsKey(diff)) {
                diffMap.get(diff).add(list);
            } else {
                diffMap.put(diff, new ArrayList<LinkedList<String>>() {{
                    add(list);
                }});
            }
        });
        print("Done loading");
        for(String s : diffMap.keySet()) {
            ArrayList<LinkedList<String>> lists = diffMap.get(s);
//            if (lists.size() > 1) {
                print("\n\nPairs of diff %s\n", s);
                for (LinkedList<String> list : lists) {
                    print("\t%s\t%s\n", list.get(0),list.get(1));
                }
//            }
        }
    }
}

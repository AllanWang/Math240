/**
 * Created by Allan Wang on 2016-11-05.
 */
public class simple {

    public static void main(String[] args) {
        final int coefficient = 32,
                congruent = 8,
                mod = 13;
        int x = 0;
        int i = 0;
        int sum = 0;
        while (x < 646) {
            x++;
            sum += coefficient;
            if (sum % mod == congruent) {
                System.out.println(x);
                i++;
            }
        }
        System.out.println("I " + i);
    }
}

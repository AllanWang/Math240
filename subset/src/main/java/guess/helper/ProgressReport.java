package guess.helper;

import com.sun.istack.internal.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class ProgressReport extends TimerTask {

    private static final int interval = 5;

    public static Timer start(@NotNull Data data) {
        final Timer timer = new Timer();
        final ProgressReport report = new ProgressReport(data);
        timer.schedule(report, 3000, interval * 1000);
        return timer;
    }

    public static void stop(Timer timer) {
        timer.cancel();
        timer.purge();
    }

    public interface Data {
        String report();
    }

    private Data mData;
    private int count = 0;

    private ProgressReport(@NotNull Data data) {
        mData = data;
    }

    @Override
    public void run() {
        count++;
        Utils.print("Progress report: %d seconds\t%s", interval * count, mData.report());
    }
}

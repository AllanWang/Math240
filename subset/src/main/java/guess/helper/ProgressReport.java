package guess.helper;

import com.sun.istack.internal.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Allan Wang on 2016-10-31.
 */
public class ProgressReport extends TimerTask {

    private final int interval;
    private final Callback callback;
    private final Timer timer = new Timer();
    private boolean running = false;


    public ProgressReport(int interval, @NotNull Callback callback) {
        this.interval = interval;
        this.callback = callback;
    }

    public interface Callback {
        void onReport();
    }

    public void start() {
        if (running) return;
        running = true;
        timer.schedule(this, 500, interval * 1000); //slight delay before start
    }

    public void stop() {
        if (!running) return;
        running = false;
        timer.cancel();
        timer.purge();
    }

    @Override
    public void run() {
        callback.onReport();
    }
}

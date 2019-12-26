package pl.se.fitnessapp.util;

import android.util.Log;

public class RunFail implements Runnable {
    private Runnable original;
    private String msg;


    public RunFail(Runnable original, String msg) {
        this.original = original;
        this.msg = msg;
    }

    @Override
    public void run() {
        original.run();
        Log.e("Runnable", msg);
    }
}

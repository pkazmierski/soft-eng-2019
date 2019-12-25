package pl.se.fitnessapp.util;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Application instance;

    public static Application getApplication() {
        return instance;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
package pl.se.fitnessapp.logic;

import pl.se.fitnessapp.data.AppSyncDb;
import pl.se.fitnessapp.data.IDBDishes;
import pl.se.fitnessapp.data.IDBExercises;
import pl.se.fitnessapp.data.IDBPersonal;
import pl.se.fitnessapp.data.IDBPreferences;

public class DBProvider {
    private static DBProvider instance;

    private IDBDishes idbDishes;
    private IDBExercises idbExercises;
    private IDBPersonal idbPersonal;
    private IDBPreferences idbPreferences;

    private DBProvider() {
        idbDishes = AppSyncDb.getInstance();
        idbExercises = AppSyncDb.getInstance();
        idbPersonal = AppSyncDb.getInstance();
        idbPreferences = AppSyncDb.getInstance();
    }

    public static DBProvider getInstance() {
        if(instance == null)
            instance = new DBProvider();
        return instance;
    }

    public IDBDishes getIDBDishes() {
        return idbDishes;
    }

    public IDBExercises getIDBExercises() {
        return idbExercises;
    }

    public IDBPersonal getIDBPersonal() {
        return idbPersonal;
    }

    public IDBPreferences getIDBPreferences() {
        return idbPreferences;
    }
}

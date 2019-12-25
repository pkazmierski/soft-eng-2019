package pl.se.fitnessapp.data;

import java.util.List;

import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.Dish;
import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.Preferences;

public class AppSyncDb implements IDBPreferences, IDBDishes, IDBExercises, IDBPersonal {

	private static AppSyncDb instance;

	private AppSyncDb() { }

	public static AppSyncDb getInstance() {
		if(instance == null)
			instance = new AppSyncDb();
		return instance;
	}

	@Override
	public void getDishes(Runnable onSuccess, Runnable onFailure, List<Dish> dishesStorage) {

	}

	@Override
	public void getIngredientsDefinitions(Runnable onSuccess, Runnable onFailure, List<DatabaseIngredient> dbIngredientsStorage) {

	}

	@Override
	public void getExercises(Runnable onSuccess, Runnable onFailure, List<Exercise> exercisesStorage) {

	}

	@Override
	public void getPersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage) {

	}

	@Override
	public void updatePersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage) {

	}

	@Override
	public void createPersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage) {

	}

	@Override
	public void getPreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage) {

	}

	@Override
	public void updatePreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage) {

	}

	@Override
	public void createPreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage) {

	}
}
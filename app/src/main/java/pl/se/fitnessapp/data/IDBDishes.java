package pl.se.fitnessapp.data;

import java.util.List;

import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.Dish;

public interface IDBDishes {

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param dishesStorage
	 */
	void getDishes(Runnable onSuccess, Runnable onFailure, List<Dish> dishesStorage);

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param dbIngredientsStorage
	 */
	void getIngredientsDefinitions(Runnable onSuccess, Runnable onFailure, List<DatabaseIngredient> dbIngredientsStorage);

}
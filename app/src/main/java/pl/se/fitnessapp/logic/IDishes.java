package pl.se.fitnessapp.logic;

import java.util.List;

import pl.se.fitnessapp.model.Dish;

public interface IDishes {

	/**
	 * 
	 * @param onGenerated
	 * @param dishStorage
	 */
	void generateRecommendations(Runnable onGenerated, List<Dish> dishStorage);

}
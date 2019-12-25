package pl.se.fitnessapp.logic;

import java.util.List;

import pl.se.fitnessapp.model.Exercise;

public interface IExercises {

	/**
	 * 
	 * @param onGenerated
	 * @param dishStorage
	 */
	void generateRecommendations(Runnable onGenerated, List<Exercise> dishStorage);

}
package pl.se.fitnessapp.data;

import java.util.List;

import pl.se.fitnessapp.model.Exercise;

public interface IDBExercises {

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param exercisesStorage
	 */
	void getExercises(Runnable onSuccess, Runnable onFailure, List<Exercise> exercisesStorage);

}
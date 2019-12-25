package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalTime;

public class Preferences {

	private LocalTime exerciseTime;
	private DietType dietType;
	private Duration exerciseDuration;
	private MealSchedule mealSchedule;

	/**
	 *
	 * @param exerciseTime
	 * @param dietType
	 * @param exerciseDuration
	 * @param mealSchedule
	 */
	public Preferences(LocalTime exerciseTime, DietType dietType, Duration exerciseDuration, MealSchedule mealSchedule) {
		// TODO - implement Preferences.Preferences
		throw new UnsupportedOperationException();
	}

	public LocalTime getExerciseTime() {
		return this.exerciseTime;
	}

	/**
	 *
	 * @param exerciseTime
	 */
	public void setExerciseTime(LocalTime exerciseTime) {
		this.exerciseTime = exerciseTime;
	}

	public Duration getExerciseDuration() {
		return this.exerciseDuration;
	}

	/**
	 *
	 * @param exerciseDuration
	 */
	public void setExerciseDuration(Duration exerciseDuration) {
		this.exerciseDuration = exerciseDuration;
	}

	public MealSchedule getMealSchedule() {
		return this.mealSchedule;
	}

	/**
	 *
	 * @param mealSchedule
	 */
	public void setMealSchedule(MealSchedule mealSchedule) {
		this.mealSchedule = mealSchedule;
	}

	public Preferences() {
		// TODO - implement Preferences.Preferences
		throw new UnsupportedOperationException();
	}

	public DietType getDietType() { return this.dietType; }

	/**
	 *
	 * @param dietType
	 */
	public void setDietType(DietType dietType) { this.dietType = dietType; }

}
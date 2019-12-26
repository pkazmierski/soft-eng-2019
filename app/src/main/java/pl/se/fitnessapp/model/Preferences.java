package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalTime;

public class Preferences {

	private LocalTime exerciseTime;
	private DietType dietType;
	private Duration exerciseDuration;
	private MealSchedule mealSchedule;

	public Preferences() {
		this.exerciseTime = LocalTime.of(18,0);
		this.dietType = DietType.STANDARD;
		this.exerciseDuration = Duration.ofMinutes(30);
		this.mealSchedule = new MealSchedule();
	}

	public LocalTime getExerciseTime() {
		return this.exerciseTime;
	}

	public void setExerciseTime(LocalTime exerciseTime) {
		this.exerciseTime = exerciseTime;
	}

	public Duration getExerciseDuration() {
		return this.exerciseDuration;
	}

	public void setExerciseDuration(Duration exerciseDuration) {
		this.exerciseDuration = exerciseDuration;
	}

	public MealSchedule getMealSchedule() {
		return this.mealSchedule;
	}

	public void setMealSchedule(MealSchedule mealSchedule) {
		this.mealSchedule = mealSchedule;
	}

	public DietType getDietType() { return this.dietType; }

	public void setDietType(DietType dietType) { this.dietType = dietType; }


	@Override
	public String toString() {
		return "Preferences{" +
				"exerciseTime=" + exerciseTime +
				", dietType=" + dietType +
				", exerciseDuration=" + exerciseDuration +
				", mealSchedule=" + mealSchedule +
				'}';
	}
}
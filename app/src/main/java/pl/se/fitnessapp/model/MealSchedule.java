package pl.se.fitnessapp.model;

import java.time.LocalTime;

public class MealSchedule {

	public LocalTime breakfast;
	public LocalTime secondBreakfast;
	public LocalTime dinner;
	public LocalTime linner;
	public LocalTime supper;

	public MealSchedule() {
		this.breakfast = LocalTime.of(7,0);
		this.secondBreakfast = LocalTime.of(10,0);
		this.dinner = LocalTime.of(13,0);
		this.linner = LocalTime.of(16,0);
		this.supper = LocalTime.of(19,0);
	}

	@Override
	public String toString() {
		return "MealSchedule{" +
				"breakfast=" + breakfast +
				", secondBreakfast=" + secondBreakfast +
				", dinner=" + dinner +
				", linner=" + linner +
				", supper=" + supper +
				'}';
	}
}
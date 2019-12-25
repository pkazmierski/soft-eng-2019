package pl.se.fitnessapp.model;

import android.location.Location;

import java.util.List;

public class Personal {

	private float weight;
	private int height;
	private float bmi;
	private Goal goal;
	private Sex sex;
	private int age;
	private int physicalActivity;
	private Location home;
	private List<DatabaseIngredient> allergies;
	private List<Dish> recommendedDishes;
	private List<Exercise> recommendedExercises;

	/**
	 * 
	 * @param weight
	 * @param height
	 * @param bmi
	 * @param goal
	 * @param sex
	 * @param age
	 * @param physicalActivity
	 * @param home
	 */
	public Personal(int weight, int height, int bmi, Goal goal, Sex sex, int age, int physicalActivity, Location home) {
		// TODO - implement Personal.Personal
		throw new UnsupportedOperationException();
	}

	public float getWeight() { return this.weight; }

	/**
	 * 
	 * @param weight
	 */
	public void setWeight(float weight) { this.weight = weight; }

	public int getHeight() {
		return this.height;
	}

	/**
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public float getBmi() { return this.bmi; }

	/**
	 * 
	 * @param bmi
	 */
	public void setBmi(float bmi) {	this.bmi = bmi; }

	public Goal getGoal() {
		return this.goal;
	}

	/**
	 * 
	 * @param goal
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Sex getSex() {
		return this.sex;
	}

	/**
	 * 
	 * @param sex
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getAge() {
		return this.age;
	}

	/**
	 * 
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public int getPhysicalActivity() {
		return this.physicalActivity;
	}

	/**
	 * 
	 * @param physicalActivity
	 */
	public void setPhysicalActivity(int physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public Location getHome() {
		return this.home;
	}

	/**
	 * 
	 * @param home
	 */
	public void setHome(Location home) {
		this.home = home;
	}

	public List<DatabaseIngredient> getAllergies() { return this.allergies; }

	/**
	 * 
	 * @param allergies
	 */
	public void setAllergies(List<DatabaseIngredient> allergies) { this.allergies = allergies; }

	public List<Dish> getRecommendedDishes() {
		return this.recommendedDishes;
	}

	/**
	 * 
	 * @param recommendedDishes
	 */
	public void setRecommendedDishes(List<Dish> recommendedDishes) {
		this.recommendedDishes = recommendedDishes;
	}

	public List<Exercise> getRecommendedExercises() {
		return this.recommendedExercises;
	}

	/**
	 * 
	 * @param recommendedExercises
	 */
	public void setRecommendedExercises(List<Exercise> recommendedExercises) {
		this.recommendedExercises = recommendedExercises;
	}

	/**
	 * 
	 * @param weight
	 * @param height
	 * @param bmi
	 * @param goal
	 * @param sex
	 * @param age
	 * @param physicalActivity
	 * @param home
	 * @param allergies
	 * @param recommendedDishes
	 * @param recommendedExercises
	 */
	public Personal(int weight, int height, int bmi, Goal goal, Sex sex, int age, int physicalActivity, Location home, List<DatabaseIngredient> allergies, List<Dish> recommendedDishes, List<Exercise> recommendedExercises) {
		// TODO - implement Personal.Personal
		throw new UnsupportedOperationException();
	}

}
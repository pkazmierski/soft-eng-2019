package pl.se.fitnessapp.model;

import android.location.Location;

import java.util.List;

public class Personal {

	private double weight;
	private int height;
	private double bmi;
	private Goal goal;
	private Sex sex;
	private int age;
	private int physicalActivity;
	private Location home;
	private List<DatabaseIngredient> allergies;
	private List<Dish> recommendedDishes;
	private List<Exercise> recommendedExercises;

	public Personal() {	}

	public double getWeight() { return this.weight; }

	public void setWeight(double weight) { this.weight = weight; }

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getBmi() { return this.bmi; }

	public void setBmi(double bmi) { this.bmi = bmi; }

	public Goal getGoal() {
		return this.goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPhysicalActivity() {
		return this.physicalActivity;
	}

	public void setPhysicalActivity(int physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public Location getHome() {
		return this.home;
	}

	public void setHome(Location home) {
		this.home = home;
	}

	public List<DatabaseIngredient> getAllergies() { return this.allergies; }

	public void setAllergies(List<DatabaseIngredient> allergies) { this.allergies = allergies; }

	public List<Dish> getRecommendedDishes() {
		return this.recommendedDishes;
	}

	public void setRecommendedDishes(List<Dish> recommendedDishes) {
		this.recommendedDishes = recommendedDishes;
	}

	public List<Exercise> getRecommendedExercises() {
		return this.recommendedExercises;
	}

	public void setRecommendedExercises(List<Exercise> recommendedExercises) {
		this.recommendedExercises = recommendedExercises;
	}


    @Override
    public String toString() {
        return "Personal{" +
                "weight=" + weight +
                ", height=" + height +
                ", bmi=" + bmi +
                ", goal=" + goal +
                ", sex=" + sex +
                ", age=" + age +
                ", physicalActivity=" + physicalActivity +
                ", home=" + home +
                ", allergies=" + allergies +
                ", recommendedDishes=" + recommendedDishes +
                ", recommendedExercises=" + recommendedExercises +
                '}';
    }
}
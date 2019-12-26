package pl.se.fitnessapp.model;

import android.location.Location;

import java.util.List;

public class Personal {

	private double weight;
	private int height;
	private double bmr;
	private double bmi;
	private Goal goal;
	private Sex sex;
	private int age;
	private PhysicalActivity physicalActivity;
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

	public double getBmr() { return this.bmr; }

	public double getBmi() { return this.bmi; }

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

	public PhysicalActivity getPhysicalActivity() {
		return this.physicalActivity;
	}

	public void setPhysicalActivity(PhysicalActivity physicalActivity) {
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

	public void calculateAndSetBmr() {
	    if(sex == Sex.MALE)
	        bmr = 66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age);
	    else
            bmr = 655.1 + (9.563 * weight) + (1.85 * height) - (4.676 * age);

	    switch(physicalActivity) {
			case SEDENTARY:
                bmr *= 1.2;
                break;
			case LIGHT:
                bmr *= 1.375;
                break;
			case MODERATE:
                bmr *= 1.55;
                break;
			case ACTIVE:
                bmr *= 1.725;
                break;
			case VERY_ACTIVE:
                bmr *= 1.9;
                break;
        }
    }

	public void calculateAndSetBmi() {
		bmi = weight / height / height * 10000;
	}


    @Override
    public String toString() {
        return "Personal{" +
                "weight=" + weight +
                ", height=" + height +
                ", bmr=" + bmr +
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
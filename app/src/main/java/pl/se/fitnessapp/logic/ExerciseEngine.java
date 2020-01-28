package pl.se.fitnessapp.logic;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.IEvent;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.Preferences;

import static pl.se.fitnessapp.model.Difficulty.EASY;
import static pl.se.fitnessapp.model.Difficulty.HARD;
import static pl.se.fitnessapp.model.Difficulty.MEDIUM;

public class ExerciseEngine implements IExercises {

	//Atributes
	private String BMIStatus; //Normal, Under_Normal_Weight, Over_Normal_Weight
	public int numberOfExercises;
	public List<Exercise> recommendedExercises;
	public List<Exercise> possibleExerciseRecommendations;
	public boolean generateRecommendationsIsRunning;
	Personal mPersonal;
	Preferences mPreferences;

	//Receive Personal
	public void setmPersonal(Personal p) {
		mPersonal = p;
	}
	//Receive Preferences
	public void setmPreferences(Preferences mPreferences) {
		this.mPreferences = mPreferences;
	}


	//Getters
	public List<Exercise> getRecommendedExercises() {
		return recommendedExercises;
	}
	public List<Exercise> getPossibleExerciseRecommendations() {
		return possibleExerciseRecommendations;
	}
	public boolean isGenerateRecommendationsIsRunning() {
		return generateRecommendationsIsRunning;
	}
	public IEvent getExerciseEvent() {
		return exerciseEvent;
	}
	public IEvent exerciseEvent;


	public ExerciseEngine() {
		// TODO - implement ExerciseEngine.ExerciseEngine


		throw new UnsupportedOperationException();
	}



	@Override
	public void generateRecommendations(Runnable onGenerated, List<Exercise> exerciseStorage) {

		generateRecommendationsIsRunning = true;
		possibleExerciseRecommendations.clear();
		recommendedExercises.clear();

		setNumberOfExercises();
		calculateBMIStatus();
		Goal goal = mPersonal.getGoal();


		//Get all possible exercises to recommend.
		Iterator<Exercise> it = exerciseStorage.iterator();

		while (it.hasNext()){
			Exercise current = it.next();
			switch (BMIStatus) {
				case "Under_Normal_Weight":
					if (current.getDifficulty().equals(EASY) && current.getGoal().equals(goal)) {
						possibleExerciseRecommendations.add(current);
					}
					break;

				case "Normal":
					if (current.getDifficulty().equals(MEDIUM) && current.getGoal().equals(goal)) {
						possibleExerciseRecommendations.add(current);
					}
					break;

				case "Over_Normal_Weight":
					if (current.getDifficulty().equals(HARD) && current.getGoal().equals(goal)) {
						possibleExerciseRecommendations.add(current);
					}
					break;
			}
		}


		//Get exercises to recommend.
		List<Exercise> l = possibleExerciseRecommendations;
		Random rand = new Random();
		Duration recommendationDuration = null;
		boolean selectingRecomendations = true;
		Duration d = mPreferences.getExerciseDuration();
		Exercise longestRecommended = null; //Exercise recommended with the highest duration.

		while (selectingRecomendations){
			int random = rand.nextInt(l.size());
			Exercise randomExercise = l.get(random);
			l.remove(random);
			recommendedExercises.add(randomExercise);
			recommendationDuration = recommendationDuration.plus(randomExercise.getDuration());
			//Save if is the longest recommended so far:
			if (recommendedExercises.size()==1){
				longestRecommended = randomExercise;
			} else if (randomExercise.getDuration().compareTo(longestRecommended.getDuration()) == 1){
				longestRecommended = randomExercise;
			}

			//Conditions where recommended exercises list is acceptable:
			if (recommendationDuration.compareTo(d) == 0){
				selectingRecomendations = false;
			}
			else if (recommendationDuration.compareTo(d) == -1){
				if((recommendedExercises.size()<numberOfExercises) && checkPreferenceOfDuration(recommendationDuration, 10)){
					selectingRecomendations = false;
				}
				else if ((recommendedExercises.size()>numberOfExercises) && checkPreferenceOfDuration(recommendationDuration, 20)){
					selectingRecomendations = false;
				}
			}
			else if (recommendationDuration.compareTo(d) == 1){
				if(checkPreferenceOfDuration(recommendationDuration, 20)){
					selectingRecomendations = false;
				} else {
					//remove longest exercise.
					recommendedExercises.remove(longestRecommended);
				}
			}

		}



		//Generate event once recommendation is created
		IEvent Event = new IEvent() {
			@Override
			public LocalDateTime getDate() {
				LocalDateTime date = LocalDate.now().atTime(18, 0, 0);
				return date;
			}

			@Override
			public String getName() {
				String name;
				String other;
				if (numberOfExercises > 1) {
					String num = String.valueOf(numberOfExercises - 1);
					other = "and " + num + " more";
				} else {
					other = null;
				}

				name = recommendedExercises.get(0).getName().toString() + other;

				return name;
			}
		};
		exerciseEvent = Event;



		generateRecommendationsIsRunning = false;
	}


		//Set number of exercises according to personal physical activity
		public void setNumberOfExercises () {

			switch (mPersonal.getPhysicalActivity()) {

				case SEDENTARY:
					numberOfExercises = 1;
					break;

				case LIGHT:
					numberOfExercises = 2;
					break;

				case MODERATE:
					numberOfExercises = 3;
					break;

				case ACTIVE:
					numberOfExercises = 4;
					break;

				case VERY_ACTIVE:
					numberOfExercises = 5;
					break;

			}

		}

		//Define BMI status according to age and gender
		public void calculateBMIStatus () {

			mPersonal.calculateAndSetBmi();
			double BMI = mPersonal.getBmi();
			int Age = mPersonal.getAge();

			switch (mPersonal.getSex()) {
				case MALE:
					if (Age == 16) {

						if ((19 <= BMI) && (BMI <= 24)) {
							BMIStatus = "Normal";

						} else if (BMI < 19) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 24) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if (Age == 17 || Age == 18) {

						if ((20 <= BMI) && (BMI <= 25)) {
							BMIStatus = "Normal";

						} else if (BMI < 20) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 25) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((18 < Age) && (Age < 25)) {

						if ((21 <= BMI) && (BMI <= 26)) {
							BMIStatus = "Normal";

						} else if (BMI < 21) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 26) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((24 < Age) && (Age < 35)) {

						if ((22 <= BMI) && (BMI <= 27)) {
							BMIStatus = "Normal";

						} else if (BMI < 22) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 27) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((34 < Age) && (Age < 55)) {

						if ((23 <= BMI) && (BMI <= 28)) {
							BMIStatus = "Normal";

						} else if (BMI < 23) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 28) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((54 < Age) && (Age < 65)) {

						if ((24 <= BMI) && (BMI <= 29)) {
							BMIStatus = "Normal";

						} else if (BMI < 24) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 29) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if (Age > 64) {

						if ((25 <= BMI) && (BMI <= 30)) {
							BMIStatus = "Normal";

						} else if (BMI < 25) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 30) {
							BMIStatus = "Over_Normal_Weight";

						}

					}
					break;
				case FEMALE:

					if ((15 < Age) && (Age < 25)) {

						if ((19 <= BMI) && (BMI <= 24)) {
							BMIStatus = "Normal";

						} else if (BMI < 19) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 24) {
							BMIStatus = "Over_Normal_Weight";
						}

					} else if ((24 < Age) && (Age < 35)) {

						if ((20 <= BMI) && (BMI <= 25)) {
							BMIStatus = "Normal";

						} else if (BMI < 20) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 25) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((34 < Age) && (Age < 45)) {

						if ((21 <= BMI) && (BMI <= 26)) {
							BMIStatus = "Normal";

						} else if (BMI < 21) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 26) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((44 < Age) && (Age < 55)) {

						if ((22 <= BMI) && (BMI <= 27)) {
							BMIStatus = "Normal";

						} else if (BMI < 22) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 27) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if ((54 < Age) && (Age < 65)) {

						if ((23 <= BMI) && (BMI <= 28)) {
							BMIStatus = "Normal";

						} else if (BMI < 23) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 28) {
							BMIStatus = "Over_Normal_Weight";

						}

					} else if (Age > 64) {

						if ((25 <= BMI) && (BMI <= 30)) {
							BMIStatus = "Normal";

						} else if (BMI < 25) {
							BMIStatus = "Under_Normal_Weight";

						} else if (BMI > 30) {
							BMIStatus = "Over_Normal_Weight";

						}

					}

					break;
				//default: System.out.println("PLEASE DEFINE YOUR GENDER");


			}
		}

		//Check a duration to be the duration established in preferences with different i% margins (of exerciseDuration)
		private boolean checkPreferenceOfDuration(Duration duration, int i){
			Duration d = mPreferences.getExerciseDuration();
			int dE = Integer.parseInt(duration.toString());
			int d1 = Integer.parseInt(d.toString()) * (100-i) / 100;
			int d2 = Integer.parseInt(d.toString()) * (100+i) / 100;
			if ((d1 < dE) && (dE < d2)){
				return true;
			} else {
				return false;
			}
		}

		//

	}
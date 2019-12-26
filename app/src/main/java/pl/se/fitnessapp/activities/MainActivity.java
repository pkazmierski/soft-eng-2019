package pl.se.fitnessapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import pl.se.fitnessapp.R;
import pl.se.fitnessapp.data.AppSyncDb;
import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.Difficulty;
import pl.se.fitnessapp.model.Dish;
import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.LocalIngredient;
import pl.se.fitnessapp.model.DishType;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.Sex;
import pl.se.fitnessapp.model.Unit;

public class MainActivity extends NavigationDrawerActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressWarnings("ConstantConditions") @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(contentView, 0);

        TextView txtMainUserWelcomeMsg = findViewById(R.id.txtMainUserWelcomeMsg);
        txtMainUserWelcomeMsg.setText("Welcome, " + AWSMobileClient.getInstance().getUsername() + ".");

        //add custom test code below
        testReceivingPersonalData();
    }





    //random test/debug functions
    void testReceivingDishes() {
        final List<Dish> dishList = new ArrayList<>();

        Runnable receivedDishes = new Runnable() {
            @Override
            public void run() {
                Log.d("receivedDishes", dishList.toString());
            }
        };

        Runnable receivedDishesFailure = new Runnable() {
            @Override
            public void run() {
                Log.e("receivedDishes", "FAILED TO PROCESS THE DISHES");
            }
        };

        AppSyncDb.getInstance().getDishes(receivedDishes, receivedDishesFailure, dishList);
    }

    void addSomeDishes() {
        List<LocalIngredient> dish1Ingredients = new ArrayList<>();
        dish1Ingredients.add(new LocalIngredient("a34c9513-1e20-40a2-8eed-d3ce67ea2651", "Bread", 1, Unit.SLICE));
        dish1Ingredients.add(new LocalIngredient("19755cae-e103-4799-95ef-4c97e90d842b", "Butter", 10, Unit.GRAMS));
        dish1Ingredients.add(new LocalIngredient("daeff1fd-f161-40eb-86f5-03cf42120e5b", "Ham", 1, Unit.SLICE));
        dish1Ingredients.add(new LocalIngredient("bfabdaa6-5efa-4d52-a016-93cfa8c73829", "Cucumber", 4, Unit.SLICE));

        List<LocalIngredient> dish2Ingredients = new ArrayList<>();
        dish2Ingredients.add(new LocalIngredient("a34c9513-1e20-40a2-8eed-d3ce67ea2651", "Bread", 1, Unit.SLICE));
        dish2Ingredients.add(new LocalIngredient("19755cae-e103-4799-95ef-4c97e90d842b", "Butter", 10, Unit.GRAMS));
        dish2Ingredients.add(new LocalIngredient("5ea51008-98e8-480c-8cd6-33ae56c373dc", "Cheese", 1, Unit.SLICE));

        Dish dish1 = new Dish("", "Ham and cucumber sandwich", "Test ham and cucumber sandwich", 300, dish1Ingredients, DishType.BREAKFAST);
        Dish dish2 = new Dish("", "Cheese sandwich", "Test cheese sandwich", 200, dish2Ingredients, DishType.SUPPER);

        AppSyncDb.getInstance().createDish(dish1);
        AppSyncDb.getInstance().createDish(dish2);
    }

    void addSomeIngredients() {
        AppSyncDb.getInstance().createDatabaseIngredient("Butter");
        AppSyncDb.getInstance().createDatabaseIngredient("Bread");
    }

    void addSomeExercises() {
        Exercise exercise1 = new Exercise("", "Squats (test)", "Test squats content", Duration.ofMinutes(5), Difficulty.EASY, Goal.MUSCLES);
        Exercise exercise2 = new Exercise("", "Running (test)", "Test running content", Duration.ofMinutes(60), Difficulty.MEDIUM, Goal.STAMINA);
        AppSyncDb.getInstance().createExercise(exercise1);
        AppSyncDb.getInstance().createExercise(exercise2);
    }

    void testReceivingExercises() {
        final List<Exercise> exercises = new ArrayList<>();

        Runnable exercisesrec = new Runnable() {
            @Override
            public void run() {
                Log.d("exercises", exercises.toString());
            }
        };

        Runnable exercisesfail = new Runnable() {
            @Override
            public void run() {
                Log.e("exercises", "FAILED TO PROCESS THE exercises");
            }
        };

        AppSyncDb.getInstance().getExercises(exercisesrec, exercisesfail, exercises);
    }

    void createPersonalData() {
        Personal personal = new Personal();
        personal.setWeight(78);
        personal.setHeight(177);
        personal.setBmi(1.855);
        personal.setGoal(Goal.MUSCLES);
        personal.setSex(Sex.MALE);
        personal.setAge(25);
        personal.setPhysicalActivity(1);
        personal.setHome(new Location("default"));

        List<DatabaseIngredient> allergies = new ArrayList<>();
        allergies.add(new DatabaseIngredient("bfabdaa6-5efa-4d52-a016-93cfa8c73829", "Cucumber"));
        allergies.add(new DatabaseIngredient("daeff1fd-f161-40eb-86f5-03cf42120e5b", "Ham"));
        personal.setAllergies(allergies);

        List<Dish> recommendedDishes = new ArrayList<>();
        List<LocalIngredient> dishIngredients = new ArrayList<>();
        dishIngredients.add(new LocalIngredient("a34c9513-1e20-40a2-8eed-d3ce67ea2651", "Bread", 1, Unit.SLICE));
        dishIngredients.add(new LocalIngredient("19755cae-e103-4799-95ef-4c97e90d842b", "Butter", 10, Unit.GRAMS));
        dishIngredients.add(new LocalIngredient("5ea51008-98e8-480c-8cd6-33ae56c373dc", "Cheese", 1, Unit.SLICE));
        Dish dish = new Dish("9ed7fe54-898f-4380-bd34-9790f1d484c5", "Cheese sandwich", "Test cheese sandwich", 200, dishIngredients, DishType.SUPPER);
        recommendedDishes.add(dish);
        personal.setRecommendedDishes(recommendedDishes);

        List<Exercise> recommendedExercises = new ArrayList<>();
        Exercise exercise = new Exercise("4182a226-72cf-4e3c-89c5-fd9531ff5495", "Squats (test)", "Test squats content", Duration.ofMinutes(5), Difficulty.EASY, Goal.MUSCLES);
        recommendedExercises.add(exercise);
        personal.setRecommendedExercises(recommendedExercises);

        Runnable logCreatePersonal = () -> Log.d("createPersonalData", "created personal: " + personal.toString());
        Runnable logFailedPersonal = () -> Log.d("createPersonalData", "failed to create personal: " + personal.toString());
        AppSyncDb.getInstance().createPersonal(logCreatePersonal, logFailedPersonal, personal);
    }

    void testReceivingPersonalData() {
        Personal personal = new Personal();
        Runnable logGotPersonal = () -> Log.d("gotPersonalData", "created personal: " + personal.toString());
        Runnable logFailedPersonal = () -> Log.d("gotPersonalData", "failed to create personal: " + personal.toString());
        AppSyncDb.getInstance().getPersonal(logGotPersonal, logFailedPersonal, personal);
    }
}

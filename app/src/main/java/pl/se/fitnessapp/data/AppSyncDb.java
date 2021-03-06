package pl.se.fitnessapp.data;

import android.location.Location;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateDatabaseIngredientMutation;
import com.amazonaws.amplify.generated.graphql.CreateDishMutation;
import com.amazonaws.amplify.generated.graphql.CreateExerciseMutation;
import com.amazonaws.amplify.generated.graphql.CreatePersonalDataMutation;
import com.amazonaws.amplify.generated.graphql.CreatePreferencesMutation;
import com.amazonaws.amplify.generated.graphql.DeleteDatabaseIngredientMutation;
import com.amazonaws.amplify.generated.graphql.DeleteDishMutation;
import com.amazonaws.amplify.generated.graphql.DeleteExerciseMutation;
import com.amazonaws.amplify.generated.graphql.DeletePersonalDataMutation;
import com.amazonaws.amplify.generated.graphql.DeletePreferencesMutation;
import com.amazonaws.amplify.generated.graphql.GetPersonalDataQuery;
import com.amazonaws.amplify.generated.graphql.GetPreferencesQuery;
import com.amazonaws.amplify.generated.graphql.ListDatabaseIngredientsQuery;
import com.amazonaws.amplify.generated.graphql.ListDishsQuery;
import com.amazonaws.amplify.generated.graphql.ListExercisesQuery;
import com.amazonaws.amplify.generated.graphql.UpdateDatabaseIngredientMutation;
import com.amazonaws.amplify.generated.graphql.UpdateDishMutation;
import com.amazonaws.amplify.generated.graphql.UpdateExerciseMutation;
import com.amazonaws.amplify.generated.graphql.UpdatePersonalDataMutation;
import com.amazonaws.amplify.generated.graphql.UpdatePreferencesMutation;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ResponseFetcher;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.DietType;
import pl.se.fitnessapp.model.Difficulty;
import pl.se.fitnessapp.model.Dish;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.LocalIngredient;
import pl.se.fitnessapp.model.DishType;
import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.MealSchedule;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.PhysicalActivity;
import pl.se.fitnessapp.model.Preferences;
import pl.se.fitnessapp.model.Sex;
import pl.se.fitnessapp.model.Unit;
import pl.se.fitnessapp.util.RunFail;
import type.CreateDatabaseIngredientInput;
import type.CreateDishInput;
import type.CreateExerciseInput;
import type.CreatePersonalDataInput;
import type.CreatePreferencesInput;
import type.DeleteDatabaseIngredientInput;
import type.DeleteDishInput;
import type.DeleteExerciseInput;
import type.DeletePersonalDataInput;
import type.DeletePreferencesInput;
import type.UpdateDatabaseIngredientInput;
import type.UpdateDishInput;
import type.UpdateExerciseInput;
import type.UpdatePersonalDataInput;
import type.UpdatePreferencesInput;

@SuppressWarnings("ConstantConditions")
public class AppSyncDb implements IDBPreferences, IDBDishes, IDBExercises, IDBPersonal {

    private static AppSyncDb instance;

    private AWSAppSyncClient appSyncClient;

    private AppSyncDb() {
        appSyncClient = AppSyncClientSingleton.getInstance();
    }

    public static AppSyncDb getInstance() {
        if (instance == null)
            instance = new AppSyncDb();
        return instance;
    }

    private List<LocalIngredient> parseLocalIngredients(final List<String> dbIngredientsStrings, final List<DatabaseIngredient> dbIngredients) {
        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        List<LocalIngredient> localIngredientsToReturn = new ArrayList<>();

        if (dbIngredients.size() > 0) {
            for (String dbIngredientString : dbIngredientsStrings) {
                String[] tokens = dbIngredientString.split(","); //id, amount, unit

                //get ingredient name from DatabaseIngredients list for current string
                DatabaseIngredient dbIngredient = null;

                for (DatabaseIngredient _dbIngredient : dbIngredients) {
                    if (_dbIngredient.getId().equals(tokens[0])) {
                        dbIngredient = _dbIngredient;
                        break;
                    }
                }

                if (dbIngredient == null)
                    dbIngredient = new DatabaseIngredient("NULL_ID", "NULL_NAME");

                //make new LocalIngredient with data from string and name from DatabaseIngredient
                double amount = 0.0;
                Unit unit = Unit.UNIT;
                try {
                    amount = Double.parseDouble(tokens[1]);
                    unit = Unit.valueOf(tokens[2]);
                } catch (Exception e) {
                    amount = 0.0;
                    unit = Unit.UNIT;
                    Log.e(methodName, e.getLocalizedMessage());
                }

                LocalIngredient currentIngredient = new LocalIngredient(dbIngredient.getId(), dbIngredient.getName(), amount, unit);

                localIngredientsToReturn.add(currentIngredient);
            }

            return localIngredientsToReturn;
        } else {
            DatabaseIngredient dbIngredient = new DatabaseIngredient("NULL_ID", "NULL_NAME");
            LocalIngredient currentIngredient = new LocalIngredient(dbIngredient.getId(), dbIngredient.getName(), 0.0, Unit.UNIT);

            return localIngredientsToReturn;
        }
    }

    private void getDishes(final Runnable onSuccess, final Runnable onFailure, @Nonnull final List<Dish> dishesStorage, ResponseFetcher fetcher) {
        if (dishesStorage == null)
            throw new IllegalArgumentException("dishesStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<ListDishsQuery.Data> listFoodDefinitionsQueryCallback = new GraphQLCall.Callback<ListDishsQuery.Data>() {
            @Override
            public void onResponse(final @Nonnull Response<ListDishsQuery.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, response.errors().toString());
                } else { //got dishes
                    dishesStorage.clear();

                    //no nulls anywhere in response
                    if (response.data() != null && response.data().listDishs() != null && response.data().listDishs().items() != null) {
                        Log.d(methodName, "onResponse (cache: " + response.fromCache() + "): " + response.data().listDishs().items().toString());

                        if (response.data().listDishs().items().size() == 0)
                            return;

                        final List<DatabaseIngredient> databaseIngredients = new ArrayList<>();

                        Runnable onIngredientsDefinitonsSuccess = () -> {
                            for (final ListDishsQuery.Item dbDish : response.data().listDishs().items()) { //go through every dish from the response
                                List<LocalIngredient> currentLocalIngredients = parseLocalIngredients(dbDish.ingredients(), databaseIngredients);
                                dishesStorage.add(new Dish(dbDish.id(), dbDish.name(), dbDish.content(), dbDish.calories(), currentLocalIngredients, DishType.valueOf(dbDish.type())));
                            }

                            if (onSuccess != null) {
                                onSuccess.run();
                            }
                        };

                        if (response.fromCache())
                            getIngredientsDefinitions(onIngredientsDefinitonsSuccess, onFailure, databaseIngredients, AppSyncResponseFetchers.CACHE_ONLY);
                        else
                            getIngredientsDefinitions(onIngredientsDefinitonsSuccess, onFailure, databaseIngredients, AppSyncResponseFetchers.NETWORK_ONLY);
                    } else {
                        Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    }
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        appSyncClient.query(ListDishsQuery.builder()
                .build())
                .responseFetcher(fetcher)
                .enqueue(listFoodDefinitionsQueryCallback);
    }

    @Override
    public void getDishes(final Runnable onSuccess, final Runnable onFailure, @Nonnull final List<Dish> dishesStorage) {
        getDishes(onSuccess, onFailure, dishesStorage, AppSyncResponseFetchers.CACHE_AND_NETWORK);
    }

    private void getIngredientsDefinitions(final Runnable onSuccess, final Runnable onFailure, @Nonnull final List<DatabaseIngredient> dbIngredientsStorage, ResponseFetcher fetcher) {
        if (dbIngredientsStorage == null)
            throw new IllegalArgumentException("dbIngredientsStorage cannot be null");
        if (fetcher == null)
            throw new IllegalArgumentException("fetcher cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<ListDatabaseIngredientsQuery.Data> listFoodDefinitionsQueryCallback = new GraphQLCall.Callback<ListDatabaseIngredientsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<ListDatabaseIngredientsQuery.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, response.errors().toString());
                } else {
                    dbIngredientsStorage.clear();
                    if (response.data() != null && response.data().listDatabaseIngredients() != null && response.data().listDatabaseIngredients().items() != null) {
                        Log.d(methodName, "onResponse (cache: " + response.fromCache() + "): " + response.data().listDatabaseIngredients().items().toString());

                        for (ListDatabaseIngredientsQuery.Item dbIngredient : response.data().listDatabaseIngredients().items()) {
                            dbIngredientsStorage.add(new DatabaseIngredient(dbIngredient.id(), dbIngredient.name()));
                        }
                    }

                    if (onSuccess != null)
                        onSuccess.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        appSyncClient.query(ListDatabaseIngredientsQuery.builder()
                .build())
                .responseFetcher(fetcher)
                .enqueue(listFoodDefinitionsQueryCallback);
    }

    @Override
    public void getIngredientsDefinitions(final Runnable onSuccess, final Runnable onFailure, @Nonnull final List<DatabaseIngredient> dbIngredientsStorage) {
        getIngredientsDefinitions(onSuccess, onFailure, dbIngredientsStorage, AppSyncResponseFetchers.CACHE_AND_NETWORK);
    }

    private void getExercises(final Runnable onSuccess, final Runnable onFailure, final List<Exercise> exercisesStorage, ResponseFetcher fetcher) {
        if (exercisesStorage == null)
            throw new IllegalArgumentException("exercisesStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<ListExercisesQuery.Data> getExercisesQueryCallback = new GraphQLCall.Callback<ListExercisesQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<ListExercisesQuery.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                } else if (!response.hasErrors() && response.data() != null && response.data().listExercises() != null && response.data().listExercises().items() != null) {
                    exercisesStorage.clear();
                    List<ListExercisesQuery.Item> dbExercises = response.data().listExercises().items();
                    for (ListExercisesQuery.Item dbEx : dbExercises) {
                        exercisesStorage.add(new Exercise(dbEx.id(), dbEx.name(), dbEx.content(), Duration.ofSeconds(dbEx.duration()), Difficulty.values()[dbEx.difficulty()], Goal.values()[dbEx.goal()]));
                    }
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                }

                if (onSuccess != null)
                    onSuccess.run();
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        appSyncClient.query(ListExercisesQuery.builder()
                .build())
                .responseFetcher(fetcher)
                .enqueue(getExercisesQueryCallback);
    }

    @Override
    public void getExercises(final Runnable onSuccess, final Runnable onFailure, final List<Exercise> exercisesStorage) {
        getExercises(onSuccess, onFailure, exercisesStorage, AppSyncResponseFetchers.CACHE_AND_NETWORK);
    }

    @Override
    public void getPersonal(final Runnable onSuccess, final Runnable onFailure, final Personal personalStorage) {
        if (personalStorage == null)
            throw new IllegalArgumentException("personalStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<GetPersonalDataQuery.Data> getPersonalDataCallback = new GraphQLCall.Callback<GetPersonalDataQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetPersonalDataQuery.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().getPersonalData() != null) {
                    GetPersonalDataQuery.GetPersonalData pd = response.data().getPersonalData();

                    personalStorage.setWeight(pd.weight());
                    personalStorage.setAge(pd.age());
                    personalStorage.setGoal(Goal.values()[pd.goal()]);
                    personalStorage.setHeight(pd.height());
                    Location location = new Location("database");
                    if(pd.home() != null) {
                        String[] tokens = pd.home().split(",");
                        location.setLatitude(Double.valueOf(tokens[0]));
                        location.setLongitude(Double.valueOf(tokens[1]));
                    }
                    personalStorage.setHome(location);
                    personalStorage.setPhysicalActivity(PhysicalActivity.values()[pd.physicalActivity()]);
                    personalStorage.setSex(Sex.values()[pd.sex() ? 1 : 0]);
                    personalStorage.calculateAndSetBmr();
                    personalStorage.calculateAndSetBmi();

                    final List<DatabaseIngredient> dbIngredients = new ArrayList<>();
                    final List<Dish> dbDishes = new ArrayList<>();
                    final List<Exercise> dbExercises = new ArrayList<>();

                    Runnable onExercisesSuccess = () -> {
                        List<Exercise> recommendedExercises = new ArrayList<>();
                        if (pd.recommendedExercises() != null) {
                            for (String exerciseId : pd.recommendedExercises()) {
                                Exercise currentExercise = null;
                                for (Exercise dbExercise : dbExercises) { //find Exercise object which matches the id
                                    if (dbExercise.getId().equals(exerciseId)) {
                                        currentExercise = dbExercise;
                                        break;
                                    }
                                }
                                if (currentExercise == null)
                                    currentExercise = new Exercise();
                                recommendedExercises.add(currentExercise);
                            }
                        }
                        personalStorage.setRecommendedExercises(recommendedExercises);

                        if (onSuccess != null)
                            onSuccess.run();
                    };

                    Runnable onDishesSuccess = () -> {
                        List<Dish> recommendedDishes = new ArrayList<>();
                        if (pd.recommendedDishes() != null) {
                            for (String dishId : pd.recommendedDishes()) {
                                Dish currentDish = null;
                                for (Dish dbDish : dbDishes) { //find Dish object which matches the id
                                    if (dbDish.getId().equals(dishId)) {
                                        currentDish = dbDish;
                                        break;
                                    }
                                }
                                if (currentDish == null)
                                    currentDish = new Dish();
                                recommendedDishes.add(currentDish);
                            }
                        }
                        personalStorage.setRecommendedDishes(recommendedDishes);

                        //get exercises
                        if (response.fromCache())
                            getExercises(onExercisesSuccess, new RunFail(onFailure, "failed to get exercises from cache"),
                                    dbExercises, AppSyncResponseFetchers.CACHE_ONLY);
                        else
                            getExercises(onExercisesSuccess, new RunFail(onFailure, "failed to get exercises from network"),
                                    dbExercises, AppSyncResponseFetchers.NETWORK_ONLY);
                    };

                    Runnable onIngredientsDefinitonsSuccess = () -> {
                        List<DatabaseIngredient> allergicIngredients = new ArrayList<>();
                        if (pd.allergies() != null) {
                            for (String ingredientId : pd.allergies()) {
                                DatabaseIngredient currentIngredient = null;
                                for (DatabaseIngredient dbIngredient : dbIngredients) { //find DatabseIngredient object which matches the id
                                    if (dbIngredient.getId().equals(ingredientId)) {
                                        currentIngredient = dbIngredient;
                                        break;
                                    }
                                }
                                if (currentIngredient == null)
                                    currentIngredient = new DatabaseIngredient("NULL_ID", "NULL_NAME");
                                allergicIngredients.add(currentIngredient);
                            }
                        }
                        personalStorage.setAllergies(allergicIngredients);

                        //get dishes
                        if (response.fromCache())
                            getDishes(onDishesSuccess, new RunFail(onFailure, "failed to get dishes from cache"),
                                    dbDishes, AppSyncResponseFetchers.CACHE_ONLY);
                        else
                            getDishes(onDishesSuccess, new RunFail(onFailure, "failed to get dishes from network"),
                                    dbDishes, AppSyncResponseFetchers.NETWORK_ONLY);
                    };

                    //get ingredients
                    if (response.fromCache())
                        getIngredientsDefinitions(onIngredientsDefinitonsSuccess, new RunFail(onFailure, "failed to get ingredients from cache"),
                                dbIngredients, AppSyncResponseFetchers.CACHE_ONLY);
                    else
                        getIngredientsDefinitions(onIngredientsDefinitonsSuccess, new RunFail(onFailure, "failed to get ingredients from network"),
                                dbIngredients, AppSyncResponseFetchers.NETWORK_ONLY);
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        appSyncClient.query(GetPersonalDataQuery.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(getPersonalDataCallback);
    }

    @Override
    public void updatePersonal(final Runnable onSuccess, final Runnable onFailure, Personal personal) {
        if (personal == null)
            throw new IllegalArgumentException("personalStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<UpdatePersonalDataMutation.Data> updatePersonalDataCallback = new GraphQLCall.Callback<UpdatePersonalDataMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdatePersonalDataMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().updatePersonalData() != null) {
                    if (onSuccess != null)
                        onSuccess.run();

                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        List<String> allergicIngredientsIds = new ArrayList<>();
        for (DatabaseIngredient ing : personal.getAllergies()) {
            allergicIngredientsIds.add(ing.getId());
        }

        List<String> dishesIds = new ArrayList<>();
        for (Dish di : personal.getRecommendedDishes()) {
            dishesIds.add(di.getId());
        }

        List<String> exercisesIds = new ArrayList<>();
        for (Exercise exercise : personal.getRecommendedExercises()) {
            exercisesIds.add(exercise.getId());
        }

        String home = personal.getHome().getLongitude() + "," + personal.getHome().getLatitude();
        UpdatePersonalDataInput updatePersonalDataInput = UpdatePersonalDataInput.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .weight(personal.getWeight())
                .height(personal.getHeight())
                .goal(personal.getGoal().ordinal())
                .sex(personal.getSex().ordinal() != 0)
                .age(personal.getAge())
                .physicalActivity(personal.getPhysicalActivity().ordinal())
                .home(home)
                .allergies(allergicIngredientsIds)
                .recommendedDishes(dishesIds)
                .recommendedExercises(exercisesIds)
                .build();

        appSyncClient.mutate(UpdatePersonalDataMutation.builder()
                .input(updatePersonalDataInput)
                .build())
                .enqueue(updatePersonalDataCallback);
    }

    @Override
    public void createPersonal(final Runnable onSuccess, final Runnable onFailure, Personal personal) {
        if (personal == null)
            throw new IllegalArgumentException("personalStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<CreatePersonalDataMutation.Data> createPersonalDataCallback = new GraphQLCall.Callback<CreatePersonalDataMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreatePersonalDataMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().createPersonalData() != null) {
                    if (onSuccess != null)
                        onSuccess.run();

                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        List<String> allergicIngredientsIds = new ArrayList<>();
        for (DatabaseIngredient ing : personal.getAllergies()) {
            allergicIngredientsIds.add(ing.getId());
        }

        List<String> dishesIds = new ArrayList<>();
        for (Dish dish : personal.getRecommendedDishes()) {
            dishesIds.add(dish.getId());
        }

        List<String> exercisesIds = new ArrayList<>();
        for (Exercise exercise : personal.getRecommendedExercises()) {
            exercisesIds.add(exercise.getId());
        }

        String home = personal.getHome().getLatitude() + "," + personal.getHome().getLongitude();
        CreatePersonalDataInput createPersonalDataInput = CreatePersonalDataInput.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .weight(personal.getWeight())
                .height(personal.getHeight())
                .goal(personal.getGoal().ordinal())
                .sex(personal.getSex().ordinal() != 0)
                .age(personal.getAge())
                .physicalActivity(personal.getPhysicalActivity().ordinal())
                .home(home)
                .allergies(allergicIngredientsIds)
                .recommendedDishes(dishesIds)
                .recommendedExercises(exercisesIds)
                .build();

        appSyncClient.mutate(CreatePersonalDataMutation.builder()
                .input(createPersonalDataInput)
                .build())
                .enqueue(createPersonalDataCallback);
    }

    @Override
    public void deletePersonal(Runnable onSuccess, Runnable onFailure) {
        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<DeletePersonalDataMutation.Data> deletePersonalDataCallback = new GraphQLCall.Callback<DeletePersonalDataMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeletePersonalDataMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().deletePersonalData() != null) {
                    if (onSuccess != null)
                        onSuccess.run();

                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        DeletePersonalDataInput deletePersonalDataInput = DeletePersonalDataInput.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .build();

        appSyncClient.mutate(DeletePersonalDataMutation.builder()
                .input(deletePersonalDataInput)
                .build())
                .enqueue(deletePersonalDataCallback);
    }

    @Override
    public void getPreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {
        if (preferencesStorage == null)
            throw new IllegalArgumentException("preferencesStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<GetPreferencesQuery.Data> getPreferencesCallback = new GraphQLCall.Callback<GetPreferencesQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetPreferencesQuery.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().getPreferences() != null) {
                    GetPreferencesQuery.GetPreferences pf = response.data().getPreferences();

                    if (pf.dietType() != null && !pf.dietType().isEmpty())
                        preferencesStorage.setDietType(DietType.valueOf(pf.dietType()));

                    if (pf.exerciseDuration() != null)
                        preferencesStorage.setExerciseDuration(Duration.ofSeconds(pf.exerciseDuration()));

                    if (pf.exerciseTime() != null)
                        preferencesStorage.setExerciseTime(LocalTime.ofSecondOfDay(pf.exerciseTime()));

                    if (pf.mealSchedule() != null) {
                        MealSchedule mealSchedule = new MealSchedule();
                        String[] tokens = pf.mealSchedule().split(",");

                        if (tokens.length == 5) {
                            mealSchedule.breakfast = LocalTime.ofSecondOfDay(Integer.valueOf(tokens[0]));
                            mealSchedule.secondBreakfast = LocalTime.ofSecondOfDay(Integer.valueOf(tokens[1]));
                            mealSchedule.dinner = LocalTime.ofSecondOfDay(Integer.valueOf(tokens[2]));
                            mealSchedule.linner = LocalTime.ofSecondOfDay(Integer.valueOf(tokens[3]));
                            mealSchedule.supper = LocalTime.ofSecondOfDay(Integer.valueOf(tokens[4]));

                            preferencesStorage.setMealSchedule(mealSchedule);
                        }
                    }

                    if (onSuccess != null)
                        onSuccess.run();
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        appSyncClient.query(GetPreferencesQuery.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(getPreferencesCallback);
    }

    @Override
    public void updatePreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {
        if (preferencesStorage == null)
            throw new IllegalArgumentException("preferencesStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<UpdatePreferencesMutation.Data> updatePreferencesCallback = new GraphQLCall.Callback<UpdatePreferencesMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdatePreferencesMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().updatePreferences() != null) {
                    if (onSuccess != null)
                        onSuccess.run();
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        MealSchedule ms = preferencesStorage.getMealSchedule();

        String mealScheduleSerialized = ms.breakfast.toSecondOfDay() + "," +
                ms.secondBreakfast.toSecondOfDay() + "," +
                ms.dinner.toSecondOfDay() + "," +
                ms.linner.toSecondOfDay() + "," +
                ms.supper.toSecondOfDay();
        UpdatePreferencesInput updatePreferencesInput = UpdatePreferencesInput.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .dietType(preferencesStorage.getDietType().toString())
                .exerciseDuration(((int) preferencesStorage.getExerciseDuration().getSeconds()))
                .exerciseTime(preferencesStorage.getExerciseTime().toSecondOfDay())
                .mealSchedule(mealScheduleSerialized).build();

        appSyncClient.mutate(UpdatePreferencesMutation.builder()
                .input(updatePreferencesInput)
                .build())
                .enqueue(updatePreferencesCallback);
    }

    @Override
    public void createPreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {
        if (preferencesStorage == null)
            throw new IllegalArgumentException("preferencesStorage cannot be null");

        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<CreatePreferencesMutation.Data> createPreferencesCallback = new GraphQLCall.Callback<CreatePreferencesMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreatePreferencesMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().createPreferences() != null) {
                    if (onSuccess != null)
                        onSuccess.run();
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        MealSchedule ms = preferencesStorage.getMealSchedule();

        String mealScheduleSerialized = ms.breakfast.toSecondOfDay() + "," +
                ms.secondBreakfast.toSecondOfDay() + "," +
                ms.dinner.toSecondOfDay() + "," +
                ms.linner.toSecondOfDay() + "," +
                ms.supper.toSecondOfDay();
        CreatePreferencesInput createPreferencesInput = CreatePreferencesInput.builder()
                .dietType(preferencesStorage.getDietType().toString())
                .exerciseDuration(((int) preferencesStorage.getExerciseDuration().getSeconds()))
                .exerciseTime(preferencesStorage.getExerciseTime().toSecondOfDay())
                .mealSchedule(mealScheduleSerialized).build();

        appSyncClient.mutate(CreatePreferencesMutation.builder()
                .input(createPreferencesInput)
                .build())
                .enqueue(createPreferencesCallback);
    }

    @Override
    public void deletePreferences(Runnable onSuccess, Runnable onFailure) {
        final int methodNameLength = new Object() {
        }.getClass().getEnclosingMethod().getName().length();
        final String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName().substring(0, methodNameLength < 23 ? methodNameLength : 22);

        GraphQLCall.Callback<DeletePreferencesMutation.Data> deletePreferencesCallback = new GraphQLCall.Callback<DeletePreferencesMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeletePreferencesMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e(methodName, "onResponse: " + response.errors().toString());
                    if (onFailure != null)
                        onFailure.run();
                } else if (!response.hasErrors() && response.data() != null && response.data().deletePreferences() != null) {
                    if (onSuccess != null)
                        onSuccess.run();
                } else {
                    Log.e(methodName, "Response contained nulls: " + response.data().toString());
                    if (onFailure != null)
                        onFailure.run();
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e(methodName, "onFailure: " + e.toString());
                if (onFailure != null)
                    onFailure.run();
            }
        };

        DeletePreferencesInput deletePreferencesInput = DeletePreferencesInput.builder()
                .id(AWSMobileClient.getInstance().getUsername())
                .build();

        appSyncClient.mutate(DeletePreferencesMutation.builder()
                .input(deletePreferencesInput)
                .build())
                .enqueue(deletePreferencesCallback);
    }

    //admin functions - outside of documentation
    public void createDatabaseIngredient(String ingredientName) {
        GraphQLCall.Callback<CreateDatabaseIngredientMutation.Data> createDbIngredientCallback = new GraphQLCall.Callback<CreateDatabaseIngredientMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateDatabaseIngredientMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("createDbIngredient", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("createDbIngredient", "onResponse: " + response.data().createDatabaseIngredient().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("createDbIngredient", e.toString());
            }
        };

        CreateDatabaseIngredientInput createDbIngredientInput = CreateDatabaseIngredientInput.builder()
                .name(ingredientName)
                .build();

        appSyncClient.mutate(CreateDatabaseIngredientMutation.builder()
                .input(createDbIngredientInput)
                .build())
                .enqueue(createDbIngredientCallback);
    }

    public void updateDatabaseIngredient(DatabaseIngredient ing) {
        GraphQLCall.Callback<UpdateDatabaseIngredientMutation.Data> updateDbIngredientCallback = new GraphQLCall.Callback<UpdateDatabaseIngredientMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateDatabaseIngredientMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("updateDbIngredient", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("updateDbIngredient", "onResponse: " + response.data().updateDatabaseIngredient().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("updateDbIngredient", e.toString());
            }
        };

        UpdateDatabaseIngredientInput updateDbIngredientInput = UpdateDatabaseIngredientInput.builder()
                .id(ing.getId())
                .name(ing.getName())
                .build();

        appSyncClient.mutate(UpdateDatabaseIngredientMutation.builder()
                .input(updateDbIngredientInput)
                .build())
                .enqueue(updateDbIngredientCallback);
    }

    public void deleteDatabaseIngredient(DatabaseIngredient ing) {
        GraphQLCall.Callback<DeleteDatabaseIngredientMutation.Data> deleteDbIngredientCallback = new GraphQLCall.Callback<DeleteDatabaseIngredientMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeleteDatabaseIngredientMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("deleteDbIngredient", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("deleteDbIngredient", "onResponse: " + response.data().deleteDatabaseIngredient().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("deleteDbIngredient", e.toString());
            }
        };

        DeleteDatabaseIngredientInput deleteDbIngredientInput = DeleteDatabaseIngredientInput.builder()
                .id(ing.getId())
                .build();

        appSyncClient.mutate(DeleteDatabaseIngredientMutation.builder()
                .input(deleteDbIngredientInput)
                .build())
                .enqueue(deleteDbIngredientCallback);
    }

    public void createDish(Dish dish) {
        GraphQLCall.Callback<CreateDishMutation.Data> createDishMutationCallback = new GraphQLCall.Callback<CreateDishMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateDishMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("createDish", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("createDish", "onResponse: " + response.data().createDish().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("createDish", e.toString());
            }
        };

        List<String> ingredientsSerialized = new ArrayList<>();
        for (LocalIngredient ing : dish.getIngredients()) {
            ingredientsSerialized.add(ing.getId() + "," + ing.getAmount() + "," + ing.getUnit().toString());
        }

        CreateDishInput createDishInput = CreateDishInput.builder()
                .calories(dish.getCalories())
                .content(dish.getContent())
                .name(dish.getName())
                .type(dish.getType().toString())
                .ingredients(ingredientsSerialized)
                .build();

        appSyncClient.mutate(CreateDishMutation.builder()
                .input(createDishInput)
                .build())
                .enqueue(createDishMutationCallback);
    }

    public void updateDish(Dish dish) {
        GraphQLCall.Callback<UpdateDishMutation.Data> updateDishCallback = new GraphQLCall.Callback<UpdateDishMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateDishMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("updateDish", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("updateDish", "onResponse: " + response.data().updateDish().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("updateDish", e.toString());
            }
        };

        List<String> ingredientsSerialized = new ArrayList<>();
        for (LocalIngredient ing : dish.getIngredients()) {
            ingredientsSerialized.add(ing.getId() + "," + ing.getAmount() + "," + ing.getUnit().toString());
        }

        UpdateDishInput updateDishInput = UpdateDishInput.builder()
                .id(dish.getId())
                .calories(dish.getCalories())
                .content(dish.getContent())
                .name(dish.getName())
                .type(dish.getType().toString())
                .ingredients(ingredientsSerialized)
                .build();

        appSyncClient.mutate(UpdateDishMutation.builder()
                .input(updateDishInput)
                .build())
                .enqueue(updateDishCallback);
    }

    public void deleteDish(Dish dish) {
        GraphQLCall.Callback<DeleteDishMutation.Data> deleteDishCallback = new GraphQLCall.Callback<DeleteDishMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeleteDishMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("deleteDish", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("deleteDish", "onResponse: " + response.data().deleteDish().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("deleteDish", e.toString());
            }
        };

        DeleteDishInput deleteDishInput = DeleteDishInput.builder()
                .id(dish.getId())
                .build();

        appSyncClient.mutate(DeleteDishMutation.builder()
                .input(deleteDishInput)
                .build())
                .enqueue(deleteDishCallback);
    }

    public void createExercise(Exercise exercise) {
        GraphQLCall.Callback<CreateExerciseMutation.Data> createExerciseCallback = new GraphQLCall.Callback<CreateExerciseMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateExerciseMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("createExercise", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("createExercise", "onResponse: " + response.data().createExercise().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("createExercise", e.toString());
            }
        };

        CreateExerciseInput createExerciseInput = CreateExerciseInput.builder()
                .name(exercise.getName())
                .content(exercise.getContent())
                .difficulty(exercise.getDifficulty().ordinal())
                .goal(exercise.getGoal().ordinal())
                .duration(((int) exercise.getDuration().getSeconds()))
                .build();

        appSyncClient.mutate(CreateExerciseMutation.builder()
                .input(createExerciseInput)
                .build())
                .enqueue(createExerciseCallback);
    }

    public void updateExercise(Exercise exercise) {
        GraphQLCall.Callback<UpdateExerciseMutation.Data> updateExerciseCallback = new GraphQLCall.Callback<UpdateExerciseMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdateExerciseMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("updateExercise", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("updateExercise", "onResponse: " + response.data().updateExercise().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("updateExercise", e.toString());
            }
        };

        UpdateExerciseInput updateExerciseInput = UpdateExerciseInput.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .content(exercise.getContent())
                .difficulty(exercise.getDifficulty().ordinal())
                .goal(exercise.getGoal().ordinal())
                .duration(((int) exercise.getDuration().getSeconds()))
                .build();

        appSyncClient.mutate(UpdateExerciseMutation.builder()
                .input(updateExerciseInput)
                .build())
                .enqueue(updateExerciseCallback);
    }

    public void deleteExercise(Exercise exercise) {
        GraphQLCall.Callback<DeleteExerciseMutation.Data> deleteExerciseCallback = new GraphQLCall.Callback<DeleteExerciseMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<DeleteExerciseMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("deleteExercise", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("deleteExercise", "onResponse: " + response.data().deleteExercise().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("deleteExercise", e.toString());
            }
        };

        DeleteExerciseInput deleteExerciseInput = DeleteExerciseInput.builder()
                .id(exercise.getId())
                .build();

        appSyncClient.mutate(DeleteExerciseMutation.builder()
                .input(deleteExerciseInput)
                .build())
                .enqueue(deleteExerciseCallback);
    }
}
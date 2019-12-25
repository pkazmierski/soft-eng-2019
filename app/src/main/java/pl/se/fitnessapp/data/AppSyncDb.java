package pl.se.fitnessapp.data;

import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateDatabaseIngredientMutation;
import com.amazonaws.amplify.generated.graphql.CreateDishMutation;
import com.amazonaws.amplify.generated.graphql.CreateExerciseMutation;
import com.amazonaws.amplify.generated.graphql.GetExerciseQuery;
import com.amazonaws.amplify.generated.graphql.ListDatabaseIngredientsQuery;
import com.amazonaws.amplify.generated.graphql.ListDishsQuery;
import com.amazonaws.amplify.generated.graphql.ListExercisesQuery;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ResponseFetcher;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.Difficulty;
import pl.se.fitnessapp.model.Dish;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.LocalIngredient;
import pl.se.fitnessapp.model.DishType;
import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.Preferences;
import pl.se.fitnessapp.model.Unit;
import type.CreateDatabaseIngredientInput;
import type.CreateDishInput;
import type.CreateExerciseInput;

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

    private List<LocalIngredient> getLocalIngredients(final List<String> dbIngredientsStrings, final List<DatabaseIngredient> dbIngredients) {
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

    @Override
    public void getDishes(final Runnable onSuccess, final Runnable onFailure, @Nonnull final List<Dish> dishesStorage) {
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

                        Runnable onIngredientsDefinitonsSuccess = new Runnable() {
                            @Override
                            public void run() {
                                for (final ListDishsQuery.Item dbDish : response.data().listDishs().items()) { //go through every dish from the response
                                    List<LocalIngredient> currentLocalIngredients = getLocalIngredients(dbDish.ingredients(), databaseIngredients);
                                    dishesStorage.add(new Dish(dbDish.id(), dbDish.name(), dbDish.content(), dbDish.calories(), currentLocalIngredients, DishType.valueOf(dbDish.type())));
                                }

                                if (onSuccess != null) {
                                    onSuccess.run();
                                }
                            }
                        };

                        if (response.fromCache()) //network response
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
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(listFoodDefinitionsQueryCallback);
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

    @Override
    public void getExercises(final Runnable onSuccess, final Runnable onFailure, final List<Exercise> exercisesStorage) {
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
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(getExercisesQueryCallback);
    }

    @Override
    public void getPersonal(final Runnable onSuccess, final Runnable onFailure, Personal personalStorage) {

    }

    @Override
    public void updatePersonal(final Runnable onSuccess, final Runnable onFailure, Personal personalStorage) {

    }

    @Override
    public void createPersonal(final Runnable onSuccess, final Runnable onFailure, Personal personalStorage) {

    }

    @Override
    public void getPreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {

    }

    @Override
    public void updatePreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {

    }

    @Override
    public void createPreferences(final Runnable onSuccess, final Runnable onFailure, Preferences preferencesStorage) {

    }


    //admin functions
    public void createDatabaseIngredient(String ingredientName) {
        GraphQLCall.Callback<CreateDatabaseIngredientMutation.Data> createDatabaseIngredientMutationCallback = new GraphQLCall.Callback<CreateDatabaseIngredientMutation.Data>() {
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

        CreateDatabaseIngredientInput createUserDataInput = CreateDatabaseIngredientInput.builder()
                .name(ingredientName)
                .build();

        appSyncClient.mutate(CreateDatabaseIngredientMutation.builder()
                .input(createUserDataInput)
                .build())
                .enqueue(createDatabaseIngredientMutationCallback);
    }

    public void createDish(Dish dish) {
        GraphQLCall.Callback<CreateDishMutation.Data> createDishMutationCallback = new GraphQLCall.Callback<CreateDishMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<CreateDishMutation.Data> response) {
                if (response.hasErrors()) {
                    Log.e("createDbIngredient", "onResponse: " + response.errors().toString());
                } else {
                    Log.d("createDbIngredient", "onResponse: " + response.data().createDish().toString());
                }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.e("createDbIngredient", e.toString());
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
}
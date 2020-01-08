package pl.se.fitnessapp.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.se.fitnessapp.R;
import pl.se.fitnessapp.adapters.ExercisesAdapter;
import pl.se.fitnessapp.logic.IExercises;
import pl.se.fitnessapp.model.Exercise;


public class ExerciseActivity extends NavigationDrawerActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private IExercises exercisesGenerator = new MockExercisesGenerator();
    private ArrayList<ExerciseItem> exerciseItemsList = new ArrayList<>();
    private ArrayList<Exercise> exerciseArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressWarnings("ConstantConditions") @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_exercise, null, false);
        drawer.addView(contentView, 0);

        mRecyclerView = findViewById(R.id.recyclerViewExercises);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExercisesAdapter(exerciseItemsList);
        exercisesGenerator.generateRecommendations(getRec , exerciseArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private Runnable getRec = () -> runOnUiThread(() -> {
        exerciseItemsList.clear();
        for (int i = 0; i < exerciseArrayList.size(); i++) {
            exerciseItemsList.add(new ExerciseItem(R.drawable.ic_preferences_black_24dp, exerciseArrayList.get(i).getName(), exerciseArrayList.get(i).getContent(), exerciseArrayList.get(i).getDuration().toString(), exerciseArrayList.get(i).getDifficulty().toString()));
        }
        mAdapter.notifyDataSetChanged();
    });

    private class MockExercisesGenerator implements IExercises {

        @Override
        public void generateRecommendations(Runnable onGenerated, List<Exercise> exerciseStorage) {
            exerciseStorage.clear();

            exerciseStorage.add(new Exercise());
            exerciseStorage.add(new Exercise());
            exerciseStorage.add(new Exercise());
            exerciseStorage.add(new Exercise());
            exerciseStorage.add(new Exercise());

            onGenerated.run();
        }
    }

}

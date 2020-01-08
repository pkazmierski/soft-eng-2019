package pl.se.fitnessapp.activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import pl.se.fitnessapp.R;
import pl.se.fitnessapp.adapters.DishesAdapter;
import pl.se.fitnessapp.model.Dish;

public class DishesActivity extends NavigationDrawerActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressWarnings("ConstantConditions") @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_dishes, null, false);
        drawer.addView(contentView, 0);

        ArrayList<DishItem> dishItemsList = new ArrayList<>();
        ArrayList<Dish> dishArrayList = new ArrayList<>();

        for (int i = 0 ; i < dishArrayList.size(); i++) {

            dishItemsList.add(new DishItem(R.drawable.ic_preferences_black_24dp ,dishArrayList.get(i).getName(), dishArrayList.get(i).getIngredients(), dishArrayList.get(i).getContent()));
        }



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new DishesAdapter(dishItemsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

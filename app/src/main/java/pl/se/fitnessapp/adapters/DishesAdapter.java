package pl.se.fitnessapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.se.fitnessapp.R;
import pl.se.fitnessapp.activities.DishItem;

public class DishesAdapter extends RecyclerView.Adapter<DishesAdapter.MyViewHolder> {
    private ArrayList<DishItem> mDishesList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mDishTitle;
        public TextView mDishIngredients;
        public TextView mDishListIngredients;
        public TextView mRecipe;
        public TextView mRecipeContent;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.dishImageView);
            mDishTitle = itemView.findViewById(R.id.dishNameTextView);
            mDishIngredients = itemView.findViewById(R.id.ingredientsTextView);
            mDishListIngredients = itemView.findViewById(R.id.ingredientsListTextView);
            mRecipe = itemView.findViewById(R.id.recipeTextView);
            mRecipeContent = itemView.findViewById(R.id.recipeContentTextView);
            relativeLayout = itemView.findViewById(R.id.relative_layout_dishes);
        }
    }

    public DishesAdapter(ArrayList<DishItem> exampleList) {
        mDishesList = exampleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_item, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DishItem currentItem = mDishesList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mDishTitle.setText(currentItem.getmDishTitle());
        holder.mDishIngredients.setText(currentItem.getmDishIngredients());
        holder.mDishListIngredients.setText(currentItem.getmDishListIngredients());
        holder.mRecipe.setText(currentItem.getmRecipe());
        holder.mRecipeContent.setText(currentItem.getmRecipeContent());

    }

    @Override
    public int getItemCount() {
        return mDishesList.size();
    }

}

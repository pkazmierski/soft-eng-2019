package pl.se.fitnessapp.model;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pl.se.fitnessapp.model.LocalIngredient;

public class DishItem extends AppCompatActivity {
    private int mImageResource;
    private String mDishTitle;
    private List<LocalIngredient> mDishListIngredients;
    private String mRecipeContent;
    private String mDishIngredients;
    private String mRecipe;

    public DishItem(int imageResource, String dishTitle, List<LocalIngredient> dishIngerdients, String dishRecipe) {
        mImageResource = imageResource;
        mDishTitle = dishTitle;
        mDishListIngredients = dishIngerdients;
        mRecipeContent = dishRecipe;
        mDishIngredients = "Ingredients:";
        mRecipe = "Recipe:";
    }

    @Override
    public String toString() {
        return "mDishListIngredients=" + mDishListIngredients;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getmDishTitle() {
        return mDishTitle;
    }

    public String getmDishListIngredients() {
        return mDishListIngredients.toString();
    }

    public String getmRecipeContent() {
        return mRecipeContent;
    }

    public String getmDishIngredients() {
        return mDishIngredients;
    }

    public String getmRecipe() {
        return mRecipe;
    }

}

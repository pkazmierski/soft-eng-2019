package pl.se.fitnessapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pl.se.fitnessapp.R;
import pl.se.fitnessapp.model.LocalIngredient;

public class DishItem extends AppCompatActivity {
    private int mImageResource;
    private ImageButton mImageButton;
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
        mImageButton = (ImageButton)findViewById(R.id.button_add_to_calendar_food);

    }

    public void addToCalendar(View view) {

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

    public ImageButton getmImageButton() {
        return mImageButton;
    }

}

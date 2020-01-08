package pl.se.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseItem extends AppCompatActivity {
    private int mImageResource;
    private String mExerciseTitle;
    private String mTrainingPlan;
    private String mContent;
    private String mDurationTextView;
    private String mDuration;
    private String mDifficultyTextView;
    private String mDifficulty;

    public ExerciseItem(int imageResource, String exerciseTitle, String content, String duration, String difficulty) {
        mImageResource = imageResource;
        mExerciseTitle = exerciseTitle;
        mContent = content;
        mDuration = duration;
        mDifficulty = difficulty;
        mTrainingPlan = "Training plan: ";
        mDurationTextView = "Duration: ";
        mDifficultyTextView = "Difficulty: ";
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getmExerciseTitle() {
        return mExerciseTitle;
    }

    public String getmTrainingPlan() {
        return mTrainingPlan;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmDurationTextView() {
        return mDurationTextView;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmDifficultyTextView() {
        return mDifficultyTextView;
    }

    public String getmDifficulty() {
        return mDifficulty;
    }

}

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
import pl.se.fitnessapp.model.ExerciseItem;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.MyViewHolder>{
    private ArrayList<ExerciseItem> mExerciseList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mExerciseTitle;
        public TextView mTrainingPlan;
        public TextView mContent;
        public TextView mDurationTextView;
        public TextView mDuration;
        public TextView mDifficultyTextView;
        public TextView mDifficulty;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ExerciseImageView);
            mExerciseTitle = itemView.findViewById(R.id.exerciseNameTextView);
            mTrainingPlan = itemView.findViewById(R.id.trainingPlanTextView);
            mContent = itemView.findViewById(R.id.contentView);
            mDurationTextView = itemView.findViewById(R.id.durationTextView);
            mDuration = itemView.findViewById(R.id.durationView);
            mDifficultyTextView = itemView.findViewById(R.id.difficultyTextView);
            mDifficulty = itemView.findViewById(R.id.difficultyView);
            relativeLayout = itemView.findViewById(R.id.relative_layout_exercises);
        }
    }

    public ExercisesAdapter(ArrayList<ExerciseItem> exampleList) {
        mExerciseList = exampleList;
    }

    @Override
    public ExercisesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        ExercisesAdapter.MyViewHolder evh = new ExercisesAdapter.MyViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExercisesAdapter.MyViewHolder holder, int position) {
        ExerciseItem currentItem = mExerciseList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mExerciseTitle.setText(currentItem.getmExerciseTitle());
        holder.mTrainingPlan.setText(currentItem.getmTrainingPlan());
        holder.mContent.setText(currentItem.getmContent());
        holder.mDurationTextView.setText(currentItem.getmDurationTextView());
        holder.mDuration.setText(currentItem.getmDuration());
        holder.mDifficultyTextView.setText(currentItem.getmDifficultyTextView());
        holder.mDifficulty.setText(currentItem.getmDifficulty());
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

}

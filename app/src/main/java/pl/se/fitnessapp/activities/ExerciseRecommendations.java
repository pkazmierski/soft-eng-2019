package pl.se.fitnessapp.activities;

import android.widget.Switch;

import java.util.LinkedList;
import java.util.List;

import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.PhysicalActivity;
import pl.se.fitnessapp.model.Sex;

import static pl.se.fitnessapp.model.PhysicalActivity.LIGHT;

public class ExerciseRecommendations {

    private String BMIStatus;
    Personal P = new Personal();


    public void calculateBMIStatus{

        P.calculateAndSetBmi();
        double BMI = P.getBmi();
        int Age = P.getAge();

        switch (P.getSex()) {
            case MALE:
                if (Age == 16) {

                    if ((19 <= BMI) && (BMI <= 24)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 19) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 24) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if (Age == 17 || Age == 18) {

                    if ((20 <= BMI) && (BMI <= 25)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 20) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 25) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((18 < Age) && (Age < 25)) {

                    if ((21 <= BMI) && (BMI <= 26)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 21) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 26) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((24 < Age) && (Age < 35)) {

                    if ((22 <= BMI) && (BMI <= 27)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 22) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 27) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((34 < Age) && (Age < 55)) {

                    if ((23 <= BMI) && (BMI <= 28)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 23) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 28) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((54 < Age) && (Age < 65)) {

                    if ((24 <= BMI) && (BMI <= 29)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 24) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 29) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if (Age > 64) {

                    if ((25 <= BMI) && (BMI <= 30)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 25) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 30) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                }
                break;
            case FEMALE:

                if ((15 < Age) && (Age < 25)) {

                    if ((19 <= BMI) && (BMI <= 24)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 19) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 24) {
                        BMIStatus = "Over_Normal_Weight";
                    }

                } else if ((24 < Age) && (Age < 35)) {

                    if ((20 <= BMI) && (BMI <= 25)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 20) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 25) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((34 < Age) && (Age < 45)) {

                    if ((21 <= BMI) && (BMI <= 26)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 21) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 26) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((44 < Age) && (Age < 55)) {

                    if ((22 <= BMI) && (BMI <= 27)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 22) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 27) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if ((54 < Age) && (Age < 65)) {

                    if ((23 <= BMI) && (BMI <= 28)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 23) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 28) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                } else if (Age > 64) {

                    if ((25 <= BMI) && (BMI <= 30)) {
                        BMIStatus = "Normal";

                    } else if (BMI < 25) {
                        BMIStatus = "Under_Normal_Weight";

                    } else if (BMI > 30) {
                        BMIStatus = "Over_Normal_Weight";

                    }

                }

                break;
            //default: System.out.println("PLEASE DEFINE YOUR GENDER");


        }
    }

        public void recommendation () {

            int WeekExerciseDays;

            switch (P.getPhysicalActivity()) {

                case LIGHT:

                    List<Exercise> Day1 = new LinkedList<Exercise>();
                    ////!!!

            }


    }

}
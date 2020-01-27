package pl.se.fitnessapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import pl.se.fitnessapp.R;
import pl.se.fitnessapp.logic.DBProvider;
import pl.se.fitnessapp.model.DatabaseIngredient;
import pl.se.fitnessapp.model.Dish;
import pl.se.fitnessapp.model.Exercise;
import pl.se.fitnessapp.model.Goal;
import pl.se.fitnessapp.model.Personal;
import pl.se.fitnessapp.model.PhysicalActivity;
import pl.se.fitnessapp.model.Sex;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText email1, username1, password1, verificationCode, age1, height1, weight1;
    Button buttonRegister, buttonVerify;
    CheckBox checkBoxPassword;
    Spinner spinner, goalSpinner;
    TextView email2, username2, password2, verify, age, height2, weight2, activity, goal;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ScrollView scroll;
    Goal g;
    PhysicalActivity p;
    Sex s;

    private static final String TAG = "RegisterActivity";

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^.{8,}$");  //at least 8 characters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email1 = (EditText) findViewById(R.id.signUpEmail);
        username1 = (EditText) findViewById(R.id.signUpUsername);
        password1 = (EditText) findViewById(R.id.signUpPassword);
        age1 = (EditText) findViewById(R.id.signUpAge);
        height1 = (EditText) findViewById(R.id.signUpHeight);
        weight1 = (EditText) findViewById(R.id.signUpWeight);
        verificationCode = (EditText) findViewById(R.id.verification_code);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonVerify = (Button) findViewById(R.id.verifyButton);
        checkBoxPassword = (CheckBox) findViewById(R.id.checkBoxPassword);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        scroll = (ScrollView) findViewById(R.id.scroll);

        email2 = (TextView) findViewById(R.id.email);
        username2 = (TextView) findViewById(R.id.username);
        password2 = (TextView) findViewById(R.id.password);
        age = (TextView) findViewById(R.id.age);
        height2 = (TextView) findViewById(R.id.height);
        weight2 = (TextView) findViewById(R.id.weight);
        activity = (TextView) findViewById(R.id.physicalActivity);
        goal = (TextView) findViewById(R.id.goal);
        spinner = (Spinner) findViewById(R.id.signUpPhysicalActivity);
        goalSpinner = (Spinner) findViewById(R.id.signUpGoal);
        verify = (TextView) findViewById(R.id.verify);

        verify.setVisibility(View.GONE);
        verificationCode.setVisibility(View.GONE);
        buttonVerify.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinnerActivity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_multiline);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.goals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_multiline);
        goalSpinner.setAdapter(adapter2);
        goalSpinner.setOnItemSelectedListener(this);

        checkBoxPassword.setOnCheckedChangeListener((compoundButton, value) -> {
            if (value) {
                password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        password1.setOnFocusChangeListener((v, hasFocus) -> {
            if (!PASSWORD_PATTERN.matcher(password1.getText()).matches() && password1.getText().toString().length() < 8) {
                Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters.", Toast.LENGTH_SHORT).show();
                password1.setError("Password must contain at least 8 characters.");
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spin = (Spinner) parent;
        Spinner spinGoal = (Spinner) parent;
        if(spin.getId() == R.id.signUpPhysicalActivity)
        {
            if (parent.getItemAtPosition(position).toString().equals("Sedentary active: No sport or exercise")) {

                p = PhysicalActivity.SEDENTARY;
            } else if (parent.getItemAtPosition(position).toString().equals("Lightly active (light exercise or sports: 1 or 3 days per week)")) {

                p = PhysicalActivity.LIGHT;
            } else if (parent.getItemAtPosition(position).toString().equals("Moderately active (moderate exercise or sports: 3 or 5 days per week)")) {

                p = PhysicalActivity.MODERATE;
            } else if (parent.getItemAtPosition(position).toString().equals("Active (hard exercise or sports or physical job: 6 or 7 days per week)")) {

                p = PhysicalActivity.ACTIVE;
            } else if (parent.getItemAtPosition(position).toString().equals("Very active (very hard exercise or sports and physical job or 2x training)")) {

                p = PhysicalActivity.VERY_ACTIVE;
            }
        }
        if(spinGoal.getId() == R.id.signUpGoal)
        {
            if (parent.getItemAtPosition(position).toString().equals("Gain muscles")) {
                g = Goal.MUSCLES;
            } else if (parent.getItemAtPosition(position).toString().equals("Increase stamina")) {
                g = Goal.STAMINA;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void checkRadioButton (View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(radioId);

        if(radioId == R.id.male) {
            s = Sex.MALE;
        } else if (radioId == R.id.female) {
            s = Sex.FEMALE;
        }
    }

    public void registerUser(View view) {
        final String username = username1.getText().toString();
        final String password = password1.getText().toString();
        final String email = email1.getText().toString();
        checkRadioButton(view);


        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email);


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(age1.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter age", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(height1.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter height", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(weight1.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter weight", Toast.LENGTH_SHORT).show();
            return;
        }

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(TAG, userStateDetails.getUserState().toString());
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        finish();
                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case SIGNED_OUT:
                        signUpUser(username,password,attributes);
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        signUpUser(username,password,attributes);
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.toString());
            }
        });
    }

    private void signUpUser(String username, String password, Map<String, String> attributes) {

        AWSMobileClient.getInstance().signUp(username, password, attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(() -> {
                    Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                    if (!signUpResult.getConfirmationState()) {
                        final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                        Toast.makeText(getApplicationContext(),"Confirm sign-up with: " + details.getDestination(), Toast.LENGTH_SHORT).show();
                        updateUI();
                    } else {
                        Toast.makeText(getApplicationContext(),"Sign-up done.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (e.getClass().getSimpleName().equals("UsernameExistsException")){
                            Toast.makeText(getApplicationContext(),"User already exists.", Toast.LENGTH_SHORT).show();
                        } else if (e.getClass().getSimpleName().equals("InvalidParameterException")){
                            Toast.makeText(getApplicationContext(),"Incorrect attributes values and/or password must contain at least 10 characters.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.e(TAG, "Sign-up error", e);
            }
        });
    }

    private void updateUI() {

        email1.setVisibility(View.GONE);
        username1.setVisibility(View.GONE);
        password1.setVisibility(View.GONE);
        age1.setVisibility(View.GONE);
        height1.setVisibility(View.GONE);
        weight1.setVisibility(View.GONE);
        activity.setVisibility(View.GONE);
        goal.setVisibility(View.GONE);
        radioButton.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        goalSpinner.setVisibility(View.GONE);
        checkBoxPassword.setVisibility(View.GONE);
        buttonRegister.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);

        email2.setVisibility(View.GONE);
        username2.setVisibility(View.GONE);
        password2.setVisibility(View.GONE);
        age.setVisibility(View.GONE);
        height2.setVisibility(View.GONE);
        weight2.setVisibility(View.GONE);


        verify.setVisibility(View.VISIBLE);
        verificationCode.setVisibility(View.VISIBLE);
        buttonVerify.setVisibility(View.VISIBLE);
    }

    public void verifyUser(View view) {

        final String code = verificationCode.getText().toString();
        final String username = username1.getText().toString();

        AWSMobileClient.getInstance().confirmSignUp(username, code, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                        if (!signUpResult.getConfirmationState()) {
                            final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                            Toast.makeText(getApplicationContext(),"Confirm sign-up with: " + details.getDestination(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "sign-up: first step of registration for " + username);
                            final String password = password1.getText().toString();

                            AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
                                @Override
                                public void onResult(final SignInResult signInResult) {
                                    runOnUiThread(() -> {
                                        Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                                        switch (signInResult.getSignInState()) {
                                            case DONE:
                                                final int age = Integer.parseInt(age1.getText().toString());
                                                final int height = Integer.parseInt(height1.getText().toString());
                                                final double weight = Double.parseDouble(weight1.getText().toString());
                                                List<DatabaseIngredient> allergies = new ArrayList<>();
                                                List<Dish> recommendedDishes = new ArrayList<>();
                                                List<Exercise> recommendedExercises = new ArrayList<>();
                                                Location location = new Location("local");
                                                location.setLatitude(0);
                                                location.setLongitude(0);

                                                Personal personal = new Personal();
                                                personal.setWeight(weight);
                                                personal.setHeight(height);
                                                personal.setGoal(g);
                                                personal.setSex(s);
                                                personal.setAge(age);
                                                personal.setPhysicalActivity(p);
                                                personal.setHome(location);
                                                personal.calculateAndSetBmr();
                                                personal.calculateAndSetBmi();
                                                personal.setAllergies(allergies);
                                                personal.setRecommendedDishes(recommendedDishes);
                                                personal.setRecommendedExercises(recommendedExercises);

                                                Runnable logCreatePersonal = () -> Log.d("createPersonalData", "created personal: " + personal.toString());
                                                Runnable logFailedPersonal = () -> Log.d("createPersonalData", "failed to create personal: " + personal.toString());

                                                DBProvider.getInstance().getIDBPersonal().createPersonal(logCreatePersonal, logFailedPersonal, personal);

                                                Toast.makeText(getApplicationContext(),"Sign-in done.", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(i);

                                                break;
                                            case PASSWORD_VERIFIER:
                                                Toast.makeText(getApplicationContext(),"Enter correct password.", Toast.LENGTH_SHORT).show();
                                                break;
                                            case NEW_PASSWORD_REQUIRED:
                                                Toast.makeText(getApplicationContext(),"Please confirm sign-in with new password.", Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                Toast.makeText(getApplicationContext(),"Unsupported sign-in confirmation: " + signInResult.getSignInState(), Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    });
                                }

                                @Override
                                public void onError(Exception e) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            if (e.getClass().getSimpleName().equals("UserNotFoundException")){
                                                Toast.makeText(getApplicationContext(),"User does not exist.", Toast.LENGTH_SHORT).show();
                                            } else if (e.getClass().getSimpleName().equals("NotAuthorizedException")){
                                                Toast.makeText(getApplicationContext(),"Incorrect username or password.", Toast.LENGTH_SHORT).show();
                                            } else if (e.getClass().getSimpleName().equals("UserNotConfirmedException")){
                                                Toast.makeText(getApplicationContext(),"User is not confirmed.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Log.e(TAG, "Sign-in error", e);
                                }
                            });

//                            finish();
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (e.getClass().getSimpleName().equals("CodeMismatchException")){
                            Toast.makeText(getApplicationContext(),"Invalid verification code provided, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Log.e(TAG, "Confirm sign-up error", e);
            }
        });

    }
}

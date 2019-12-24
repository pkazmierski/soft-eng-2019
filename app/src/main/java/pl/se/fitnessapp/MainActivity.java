package pl.se.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtMainUserWelcomeMsg = findViewById(R.id.txtMainUserWelcomeMsg);
        txtMainUserWelcomeMsg.setText("Welcome, " + AWSMobileClient.getInstance().getUsername() + ".");
    }
}

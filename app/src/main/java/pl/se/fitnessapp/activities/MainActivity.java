package pl.se.fitnessapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;

import pl.se.fitnessapp.R;

public class MainActivity extends NavigationDrawerActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressWarnings("ConstantConditions") @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawer.addView(contentView, 0);

        TextView txtMainUserWelcomeMsg = findViewById(R.id.txtMainUserWelcomeMsg);

        txtMainUserWelcomeMsg.setText("Welcome, " + AWSMobileClient.getInstance().getUsername() + ".");
    }
}

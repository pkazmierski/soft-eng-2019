package pl.se.fitnessapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import pl.se.fitnessapp.R;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_dishes:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                Intent intentD = new Intent(this, DishesActivity.class);
                startActivity(intentD);
                break;
            case R.id.nav_exercises:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                Intent intentE = new Intent(this, ExerciseActivity.class);
                startActivity(intentE);
                break;
            case R.id.nav_gcalendar_export:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                break;
            case R.id.nav_gyms:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                break;
            case R.id.nav_personal_data:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                break;
            case R.id.nav_preferences:
                //startAnimatedActivity(new Intent(getApplicationContext(), ReplaceMe.class));
                break;
            case R.id.nav_logoff:
                //logoff rotine
                break;
            default:
                throw new Error("No such navigation ID exists.");
        }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package pl.se.fitnessapp.logic;

import android.location.Location;

import java.util.List;

import pl.se.fitnessapp.model.Gym;


public interface IGyms {

	List<Gym> getGyms();


	Location getUserLocation();

}
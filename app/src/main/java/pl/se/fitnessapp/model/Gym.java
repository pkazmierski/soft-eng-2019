package pl.se.fitnessapp.model;

import android.location.Location;

public class Gym {

	private String name;
	private int distance;
	private double rating;
	private Location location;

	/**
	 * 
	 * @param name
	 * @param distance
	 * @param rating
	 * @param location
	 */
	public Gym(String name, int distance, double rating, Location location) {
		// TODO - implement Gym.Gym
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getDistance() {
		return this.distance;
	}

	/**
	 * 
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getRating() {
		return this.rating;
	}

	/**
	 * 
	 * @param rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	public Location getLocation() {
		return this.location;
	}

	/**
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

}
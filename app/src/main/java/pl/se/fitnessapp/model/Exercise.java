package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Exercise implements IEvent {

	private String name;
	private String content;
	private Duration duration;
	private Difficulty difficulty;
	private Goal goal;

	/**
	 * 
	 * @param name
	 * @param content
	 * @param duration
	 * @param difficulty
	 * @param goal
	 */
	public Exercise(String name, String content, Duration duration, Difficulty difficulty, Goal goal) {
		// TODO - implement Exercise.Exercise
		throw new UnsupportedOperationException();
	}

	@Override
	public LocalDateTime getDate() {
		return null;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public Duration getPreparationTime() {
		return null;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	/**
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public Duration getDuration() {
		return this.duration;
	}

	/**
	 * 
	 * @param duration
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	/**
	 * 
	 * @param difficulty
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Goal getGoal() {
		return this.goal;
	}

	/**
	 * 
	 * @param goal
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}

}
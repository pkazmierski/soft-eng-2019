package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Exercise implements IEvent {

	private String id;
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
	public Exercise(String id, String name, String content, Duration duration, Difficulty difficulty, Goal goal) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.duration = duration;
		this.difficulty = difficulty;
		this.goal = goal;
	}

	@Override
	public LocalDateTime getDate() {
		return null;
	}

	@Override
	public Duration getPreparationTime() {
		return null;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Duration getDuration() {
		return this.duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Goal getGoal() {
		return this.goal;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

}
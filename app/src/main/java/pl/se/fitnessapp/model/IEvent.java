package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalDateTime;

public interface IEvent {

	LocalDateTime getDate();

	String getName();

	Duration getPreparationTime();

}
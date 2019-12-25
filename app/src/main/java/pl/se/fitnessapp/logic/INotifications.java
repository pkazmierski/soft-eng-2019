package pl.se.fitnessapp.logic;

import java.util.List;

import pl.se.fitnessapp.model.IEvent;

public interface INotifications {

	List<IEvent> getEventsWithNotification();

	/**
	 * 
	 * @param event
	 */
	void addNotification(IEvent event);

}
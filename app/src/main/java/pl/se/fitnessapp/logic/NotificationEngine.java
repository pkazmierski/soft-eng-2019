package pl.se.fitnessapp.logic;

import java.util.List;

import pl.se.fitnessapp.model.IEvent;

public class NotificationEngine implements INotifications {

	private List<IEvent> events;

	public NotificationEngine() {
		// TODO - implement NotificationEngine.NotificationEngine
		throw new UnsupportedOperationException();
	}

	@Override
	public List<IEvent> getEventsWithNotification() {
		return null;
	}

	@Override
	public void addNotification(IEvent event) {

	}
}
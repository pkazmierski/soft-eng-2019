package pl.se.fitnessapp.logic;

import pl.se.fitnessapp.model.IEvent;

public interface ICalendarExport {

	/**
	 * 
	 * @param event
	 */
	void saveEvent(IEvent event);

}
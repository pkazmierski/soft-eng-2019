package pl.se.fitnessapp.data;

import pl.se.fitnessapp.model.Personal;

public interface IDBPersonal {

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param personalStorage
	 */
	void getPersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage);

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param personalStorage
	 */
	void updatePersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage);

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param personalStorage
	 */
	void createPersonal(Runnable onSuccess, Runnable onFailure, Personal personalStorage);

}
package pl.se.fitnessapp.data;

import pl.se.fitnessapp.model.Preferences;

public interface IDBPreferences {

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param preferencesStorage
	 */
	void getPreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage);

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param preferencesStorage
	 */
	void updatePreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage);

	/**
	 * 
	 * @param onSuccess
	 * @param onFailure
	 * @param preferencesStorage
	 */
	void createPreferences(Runnable onSuccess, Runnable onFailure, Preferences preferencesStorage);

	/**
	 *
	 * @param onSuccess
	 * @param onFailure
	 */
	void deletePreferences(Runnable onSuccess, Runnable onFailure);
}
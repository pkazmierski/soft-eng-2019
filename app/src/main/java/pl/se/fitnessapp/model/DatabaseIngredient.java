package pl.se.fitnessapp.model;

public class DatabaseIngredient {

	private String id;
	private String name;

	public String getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
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

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public DatabaseIngredient(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "DatabaseIngredient{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
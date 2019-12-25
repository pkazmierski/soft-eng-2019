package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Dish implements IEvent {

	private String name;
	private String content;
	private int calories;
	private List<DishIngredient> ingredients;
	private DishType type;

	/**
	 * 
	 * @param name
	 * @param content
	 * @param calories
	 * @param ingredients
	 * @param type
	 */
	public Dish(String name, String content, int calories, List<DishIngredient> ingredients, DishType type) {
		this.name = name;
		this.content = content;
		this.calories = calories;
		this.ingredients = ingredients;
		this.type = type;
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

	public int getCalories() {
		return this.calories;
	}

	/**
	 * 
	 * @param calories
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	public List<DishIngredient> getIngredients() {
		return this.ingredients;
	}

	/**
	 * 
	 * @param ingredients
	 */
	public void setIngredients(List<DishIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public DishType getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(DishType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder("Dish{" +
				"name='" + name + '\'' +
				", content='" + content + '\'' +
				", calories=" + calories +
				", type=" + type +
				", ingredients=[");
		for (DishIngredient ing : ingredients) {
			toReturn.append(ing.getName()).append(" ").append(ing.getAmount()).append(" ").append(ing.getUnit().toString()).append(", ");
		}

		toReturn.append("]}");
		return toReturn.toString();
	}
}
package pl.se.fitnessapp.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Dish implements IEvent {

	private String id;
	private String name;
	private String content;
	private int calories;
	private List<LocalIngredient> ingredients;
	private DishType type;

	/**
	 * 
	 * @param name
	 * @param content
	 * @param calories
	 * @param ingredients
	 * @param type
	 */
	public Dish(String id, String name, String content, int calories, List<LocalIngredient> ingredients, DishType type) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.calories = calories;
		this.ingredients = ingredients;
		this.type = type;
	}

	public Dish() {
		this.id = "NULL_ID";
		this.name = "NULL_NAME";
		this.content = "NULL_CONTENT";
		this.calories = 0;
		this.ingredients = new ArrayList<>();
		this.type = DishType.BREAKFAST;
	}

	@Override
	public LocalDateTime getDate() {
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

	@Override
	public Duration getPreparationTime() {
		return null;
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

	public int getCalories() {
		return this.calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public List<LocalIngredient> getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(List<LocalIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public DishType getType() {
		return this.type;
	}

	public void setType(DishType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder("Dish{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", calories=" + calories +
				", type=" + type +
				", ingredients=[");
		for (LocalIngredient ing : ingredients) {
			toReturn.append(ing.getName()).append(" ").append(ing.getAmount()).append(" ").append(ing.getUnit().toString()).append(", ");
		}

		toReturn.append("]}");
		return toReturn.toString();
	}
}
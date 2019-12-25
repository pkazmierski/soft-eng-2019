package pl.se.fitnessapp.model;

public class DishIngredient {

	private DatabaseIngredient dbIngredient;
	private double amount;
	private Unit unit;

	/**
	 * 
	 * @param dbIngredient
	 * @param amount
	 * @param unit
	 */
	public DishIngredient(DatabaseIngredient dbIngredient, double amount, Unit unit) {

	}

	public String getName() {
		// TODO - implement DishIngredient.getName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		// TODO - implement DishIngredient.setName
		throw new UnsupportedOperationException();
	}

	public double getAmount() {
		return this.amount;
	}

	/**
	 * 
	 * @param amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Unit getUnit() {
		return this.unit;
	}

	/**
	 * 
	 * @param unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
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
		this.dbIngredient = dbIngredient;
		this.amount = amount;
		this.unit = unit;
	}

	public String getName() {
		if(dbIngredient != null)
			return dbIngredient.getName();
		else
			return "MISSING_dbIngredient";
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

	public DatabaseIngredient getDbIngredient() {
		return this.dbIngredient;
	}

	/**
	 *
	 * @param dbIngredient
	 */
	public void setDbIngredient(DatabaseIngredient dbIngredient) {
		this.dbIngredient = dbIngredient;
	}

	@Override
	public String toString() {
		return "DishIngredient{" +
				"dbIngredient=" + dbIngredient +
				", amount=" + amount +
				", unit=" + unit +
				'}';
	}
}
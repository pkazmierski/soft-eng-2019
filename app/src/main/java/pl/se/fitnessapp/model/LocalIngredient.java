package pl.se.fitnessapp.model;

public class LocalIngredient extends DatabaseIngredient {
	private double amount;
	private Unit unit;

	/**
	 *
	 * @param amount
	 * @param unit
	 */
	public LocalIngredient(String id, String name, double amount, Unit unit) {
		super(id, name);
		this.amount = amount;
		this.unit = unit;
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

	@Override
	public String toString() {
		return "LocalIngredient{" +
				", amount=" + amount +
				", unit=" + unit +
				'}';
	}
}
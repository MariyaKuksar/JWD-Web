package by.epam.store.entity.comparator;

import java.util.Comparator;

import by.epam.store.entity.Product;

/**
 * Describes all product comparators
 * 
 * @author Mariya Kuksar
 */
public enum ProductComparator {

	/**
	 * A comparison function, which sets products in order of increasing price
	 */
	INCREASE_PRICE((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())),

	/**
	 * A comparison function, which sets products in order of decreasing price
	 */
	DECREASE_PRICE((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));

	private Comparator<Product> comporator;

	/**
	 * Constructs a new ProductComporator with the specified product comparison
	 * functions
	 * 
	 * @param comporator {@link Comparator} of {@link Product}} comparison functions
	 */
	ProductComparator(Comparator<Product> comporator) {
		this.comporator = comporator;
	}

	/**
	 * Gets product comparator
	 * 
	 * @return {@link Comparator} of {@link Product} comparison functions
	 */
	public Comparator<Product> getComporator() {
		return comporator;
	}
}

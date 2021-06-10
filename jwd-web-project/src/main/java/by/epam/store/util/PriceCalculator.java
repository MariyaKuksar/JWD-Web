package by.epam.store.util;

import java.math.BigDecimal;
import java.util.Map;

import by.epam.store.entity.Product;

/**
 * The utility is responsible for the calculation of prices
 * 
 * @author Mariya Kuksar
 */
public final class PriceCalculator {

	private PriceCalculator() {
	}

	/**
	 * Calculates the total cost of products
	 * 
	 * @param products {@link Map} of {@link Product} and {@link Integer} products
	 *                 and its quantity
	 * @return {@link BigDecimal} the total cost of products
	 */
	public static BigDecimal calculateTotalCost(Map<Product, Integer> products) {
		BigDecimal totalCost = BigDecimal.ZERO;
		if (products == null) {
			return totalCost;
		}
		for (Map.Entry<Product, Integer> productAndQuantity : products.entrySet()) {
			BigDecimal price = productAndQuantity.getKey().getPrice();
			BigDecimal quantity = new BigDecimal(productAndQuantity.getValue());
			BigDecimal productCost = price.multiply(quantity);
			totalCost = totalCost.add(productCost);
		}
		return totalCost;
	}
}

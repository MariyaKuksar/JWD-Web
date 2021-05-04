package by.epam.store.util;

import java.math.BigDecimal;
import java.util.Map;


import by.epam.store.model.entity.Product;

public final class PriceCalculator {
	
	private PriceCalculator() {
	}

	public static BigDecimal calculateTotalCost (Map<Product, Integer> products) {
		BigDecimal totalCost = BigDecimal.ZERO;
		for (Map.Entry<Product, Integer> productAndAmount: products.entrySet()) {
			BigDecimal price = productAndAmount.getKey().getPrice();
			BigDecimal amount = new BigDecimal(productAndAmount.getValue());
			BigDecimal productCost = price.multiply(amount);
			totalCost = totalCost.add(productCost);
		}
		return totalCost;		
	}
}

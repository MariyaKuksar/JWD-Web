package by.epam.store.entity.builder.impl;

import java.math.BigDecimal;
import java.util.Map;

import static by.epam.store.controller.command.ParameterAndAttribute.*;
import by.epam.store.entity.Product;
import by.epam.store.entity.builder.EntityBuilder;

/**
 * The builder is responsible for building product
 * 
 * @author Mariya Kuksar
 */
public class ProductBuilder implements EntityBuilder<Product> {
	private static final ProductBuilder instance = new ProductBuilder();

	private ProductBuilder() {
	}

	/**
	 * Get instance of this class
	 * 
	 * @return {@link ProductBuilder} instance
	 */
	public static ProductBuilder getInstance() {
		return instance;
	}

	@Override
	public Product build(Map<String, String> productInfo) {
		Product product = new Product();
		String productId = productInfo.get(PRODUCT_ID);
		if (productId != null) {
			product.setProductId(Long.valueOf(productId));
		}
		String categoryId = productInfo.get(CATEGORY_ID);
		if (categoryId != null) {
			product.setCategoryId(Long.valueOf(categoryId));
		}
		product.setProductName(productInfo.get(PRODUCT_NAME));
		product.setImageName(productInfo.get(IMAGE_NAME));
		product.setPrice(new BigDecimal(productInfo.get(PRICE)));
		return product;
	}
}

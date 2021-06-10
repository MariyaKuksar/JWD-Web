package by.epam.store.entity.builder.impl;

import java.math.BigDecimal;
import java.util.Map;

import by.epam.store.controller.command.ParameterAndAttribute;
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
		String productId = productInfo.get(ParameterAndAttribute.PRODUCT_ID);
		if (productId != null) {
			product.setProductId(Long.parseLong(productId));
		}
		String categoryId = productInfo.get(ParameterAndAttribute.CATEGORY_ID);
		if (categoryId != null) {
			product.setCategoryId(Long.parseLong(categoryId));
		}
		product.setProductName(productInfo.get(ParameterAndAttribute.PRODUCT_NAME));
		product.setImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME));
		product.setPrice(new BigDecimal(productInfo.get(ParameterAndAttribute.PRICE)));
		return product;
	}
}

package by.epam.store.model.entity.builder;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.util.ColumnName;
import by.epam.store.util.ParameterAndAttribute;

public class ProductBuilder implements EntityBuilder<Product> {
	private static final ProductBuilder instance = new ProductBuilder();

	private ProductBuilder() {
	}

	public static ProductBuilder getInstance() {
		return instance;
	}

	@Override
	public Product build(Map<String, String> productInfo) {
		Product product = new Product();
		ProductCategory category = new ProductCategory(
				Long.parseLong(productInfo.get(ParameterAndAttribute.CATEGORY_ID)));
		product.setCategory(category);
		product.setProductName(productInfo.get(ParameterAndAttribute.PRODUCT_NAME));
		product.setImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME));
		product.setPrice(new BigDecimal(productInfo.get(ParameterAndAttribute.PRICE)));
		return product;
	}

	@Override
	public List<Product> build(ResultSet resultSet) throws SQLException {
		List<Product> products = new ArrayList<>();
		while (resultSet.next()) {
			Product product = new Product();
			product.setProductId(resultSet.getLong(ColumnName.PRODUCTS_ID));
			Long categoryId = resultSet.getLong(ColumnName.PRODUCTS_CATEGORY_ID);
			String categoryName = resultSet.getString(ColumnName.PRODUCT_CATEGORIES_CATEGORY);
			ProductCategory category = new ProductCategory(categoryId, categoryName);
			product.setCategory(category);
			product.setProductName(resultSet.getString(ColumnName.PRODUCTS_NAME));
			product.setImageName(resultSet.getString(ColumnName.PRODUCTS_IMAGE_NAME));
			product.setPrice(resultSet.getBigDecimal(ColumnName.PRODUCTS_PRICE));
			product.setAmount(resultSet.getInt(ColumnName.PRODUCTS_AMOUNT));
			products.add(product);
		}
		return products;
	}
}

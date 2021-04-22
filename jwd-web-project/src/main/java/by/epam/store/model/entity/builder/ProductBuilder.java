package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.entity.Product;

public class ProductBuilder implements EntityBuilder<Product> {
	private static final ProductBuilder instance = new ProductBuilder();

	private ProductBuilder() {
	}

	public static ProductBuilder getInstance() {
		return instance;
	}
	
	@Override
	public Product build(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> build(ResultSet resultSet) throws SQLException {
		List<Product> products = new ArrayList<>();
		while (resultSet.next()) {
			Product product = new Product();
			product.setProductId(resultSet.getLong(ColumnName.PRODUCTS_ID));
			product.setCategoryId(resultSet.getLong(ColumnName.PRODUCTS_CATEGORY_ID));
			product.setProductName(resultSet.getString(ColumnName.PRODUCTS_NAME));
			product.setImageName(resultSet.getString(ColumnName.PRODUCTS_IMAGE_NAME));
			product.setPrice(resultSet.getBigDecimal(ColumnName.PRODUCTS_PRICE));
			product.setAmount(resultSet.getInt(ColumnName.PRODUCTS_AMOUNT));
			products.add(product);
		}
		return products;
	}
}

package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.entity.ProductCategory;

public class ProductCategoryBuilder implements EntityBuilder<ProductCategory> {
	private static final ProductCategoryBuilder instance = new ProductCategoryBuilder();

	private ProductCategoryBuilder() {
	}

	public static ProductCategoryBuilder getInstance() {
		return instance;
	}

	@Override
	public ProductCategory build(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductCategory> build(ResultSet resultSet) throws SQLException {
		List<ProductCategory> productCategories = new ArrayList<>();
		while (resultSet.next()) {
			ProductCategory category = new ProductCategory();
			category.setCategoryId(resultSet.getLong(ColumnName.PRODUCT_CATEGORIES_ID));
			category.setCategoryName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_CATEGORY));
			category.setImageName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_IMAGE_NAME));
			productCategories.add(category);
		}
		return productCategories;
	}
}

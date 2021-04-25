package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.model.entity.ProductCategory;
import by.epam.store.util.ColumnName;

public class ProductCategoryBuilder implements EntityBuilder<ProductCategory> {
	private static final ProductCategoryBuilder instance = new ProductCategoryBuilder();

	private ProductCategoryBuilder() {
	}

	public static ProductCategoryBuilder getInstance() {
		return instance;
	}

	@Override
	public ProductCategory build(Map<String, String> entityInfo) {
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

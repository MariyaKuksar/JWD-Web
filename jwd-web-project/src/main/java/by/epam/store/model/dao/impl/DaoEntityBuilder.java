package by.epam.store.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.OrderStatus;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.ProductCategory;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.entity.UserStatus;

final class DaoEntityBuilder {

	private DaoEntityBuilder() {
	}

	static User buildUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserId(resultSet.getLong(ColumnName.USERS_ID));
		user.setLogin(resultSet.getString(ColumnName.USERS_LOGIN));
		user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
		user.setRole(UserRole.valueOf(resultSet.getString(ColumnName.USERS_ROLE).toUpperCase()));
		user.setName(resultSet.getString(ColumnName.USERS_NAME));
		user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
		user.setStatus(UserStatus.valueOf(resultSet.getString(ColumnName.USERS_STATUS.toUpperCase())));
		return user;
	}

	static ProductCategory buildProductCategory(ResultSet resultSet) throws SQLException {
		ProductCategory category = new ProductCategory();
		category.setCategoryId(resultSet.getLong(ColumnName.PRODUCT_CATEGORIES_ID));
		category.setCategoryName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_CATEGORY));
		category.setImageName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_IMAGE_NAME));
		return category;
	}

	static Product buildProduct(ResultSet resultSet) throws SQLException {
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
		return product;
	}

	//TODO пока не использую, доработать
	static Order buildOrder(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setOrderId(resultSet.getLong(ColumnName.ORDERS_ID));
		order.setUserId(resultSet.getLong(ColumnName.ORDERS_USER_ID));
		order.setDataTime(resultSet.getTimestamp(ColumnName.ORDERS_DATA_TIME).toLocalDateTime());
		order.setOrderStatus(OrderStatus.valueOf(resultSet.getString(ColumnName.ORDERS_STATUS)));
		return order;
	}
}

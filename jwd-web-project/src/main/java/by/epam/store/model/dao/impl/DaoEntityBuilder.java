package by.epam.store.model.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import by.epam.store.entity.DeliveryMethod;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.PaymentMethod;
import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.User;
import by.epam.store.entity.UserRole;
import by.epam.store.entity.UserStatus;
import by.epam.store.model.dao.ColumnName;

/**
 * The builder is responsible for building different entities
 * 
 * @author Mariya Kuksar
 */
final class DaoEntityBuilder {

	private DaoEntityBuilder() {
	}

	/**
	 * Builds new User
	 * 
	 * @param resultSet {@link ResultSet} database result set
	 * @return {@link User}
	 * @throws SQLException
	 */
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

	/**
	 * Builds new ProductCategory
	 * 
	 * @param resultSet {@link ResultSet} database result set
	 * @return {@link ProductCategory}
	 * @throws SQLException
	 */
	static ProductCategory buildProductCategory(ResultSet resultSet) throws SQLException {
		ProductCategory category = new ProductCategory();
		category.setCategoryId(resultSet.getLong(ColumnName.PRODUCT_CATEGORIES_ID));
		category.setCategoryName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_CATEGORY));
		category.setImageName(resultSet.getString(ColumnName.PRODUCT_CATEGORIES_IMAGE_NAME));
		return category;
	}

	/**
	 * Builds new Product
	 * 
	 * @param resultSet {@link ResultSet} database result set
	 * @return {@link Product}
	 * @throws SQLException
	 */
	static Product buildProduct(ResultSet resultSet) throws SQLException {
		Product product = new Product();
		product.setProductId(resultSet.getLong(ColumnName.PRODUCTS_ID));
		product.setCategoryId(resultSet.getLong(ColumnName.PRODUCTS_CATEGORY_ID));
		product.setProductName(resultSet.getString(ColumnName.PRODUCTS_NAME));
		product.setImageName(resultSet.getString(ColumnName.PRODUCTS_IMAGE_NAME));
		product.setPrice(resultSet.getBigDecimal(ColumnName.PRODUCTS_PRICE));
		product.setQuantity(resultSet.getInt(ColumnName.PRODUCTS_QUANTITY));
		return product;
	}

	/**
	 * Builds new Order
	 * 
	 * @param resultSet {@link ResultSet} database result set
	 * @return {@link Order}
	 * @throws SQLException
	 */
	static Order buildOrder(ResultSet resultSet) throws SQLException {
		Order order = new Order();
		order.setOrderId(resultSet.getLong(ColumnName.ORDERS_ID));
		order.setUserId(resultSet.getLong(ColumnName.ORDERS_USER_ID));
		if (resultSet.getTimestamp(ColumnName.ORDERS_DATE_TIME) != null) {
			order.setDateTime(resultSet.getTimestamp(ColumnName.ORDERS_DATE_TIME).toLocalDateTime());
		}
		order.setOrderStatus(OrderStatus.valueOf(resultSet.getString(ColumnName.ORDERS_STATUS)));
		if (resultSet.getString(ColumnName.ORDERS_PAYMENT_METHOD) != null) {
			order.setPaymentMethod(
					PaymentMethod.valueOf(resultSet.getString(ColumnName.ORDERS_PAYMENT_METHOD).toUpperCase()));
		}
		order.setCost(resultSet.getBigDecimal(ColumnName.ORDERS_COST));
		if (resultSet.getString(ColumnName.ORDERS_DELIVERY_METHOD) != null) {
			order.setDeliveryMethod(
					DeliveryMethod.valueOf(resultSet.getString(ColumnName.ORDERS_DELIVERY_METHOD).toUpperCase()));
		}
		order.getAddress().setCity(resultSet.getString(ColumnName.ORDERS_CITY));
		order.getAddress().setStreet(resultSet.getString(ColumnName.ORDERS_STREET));
		order.getAddress().setHouse(resultSet.getString(ColumnName.ORDERS_HOUSE));
		order.getAddress().setApartment(resultSet.getString(ColumnName.ORDERS_APARTMENT));
		return order;
	}
}

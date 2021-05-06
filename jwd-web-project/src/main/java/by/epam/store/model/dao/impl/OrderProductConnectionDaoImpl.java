package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.entity.OrderProductConnection;
import by.epam.store.model.entity.Product;

public class OrderProductConnectionDaoImpl implements OrderProductConnectionDao {
	private static final String SQL_UPDATE_ORDER_PRODUCT_CONNECTION = "UPDATE ORDER_PRODUCT_CONNECTION SET AMOUNT_OF_PRODUCT=? WHERE ORDER_ID=? AND PRODUCT_ID=?";
	private static final String SQL_UPDATE_INCREASE_AMOUNT_ORDER_PRODUCT_CONNECTION = "UPDATE ORDER_PRODUCT_CONNECTION SET AMOUNT_OF_PRODUCT=AMOUNT_OF_PRODUCT+? WHERE ORDER_ID=? AND PRODUCT_ID=?";
	private static final String SQL_INSERT_ORDER_PRODUCT_CONNECTION = "INSERT INTO ORDER_PRODUCT_CONNECTION (ORDER_ID, PRODUCT_ID, AMOUNT_OF_PRODUCT) VALUES (?, ?, ?)";
	private static final String SQL_SELECT_ORDER_PRODUCT_CONNECTION_BY_ORDER_ID = "SELECT PRODUCTS.ID, CATEGORY_ID, CATEGORY, NAME, PRODUCTS.IMAGE_NAME, PRICE, AMOUNT, AMOUNT_OF_PRODUCT FROM ORDER_PRODUCT_CONNECTION JOIN PRODUCTS ON ORDER_PRODUCT_CONNECTION.PRODUCT_ID=PRODUCTS.ID JOIN PRODUCT_CATEGORIES ON PRODUCTS.CATEGORY_ID=PRODUCT_CATEGORIES.ID WHERE ORDER_ID=?";
	private static final String SQL_DELETE_ORDER_PRODUCT_CONNECTION = "DELETE FROM ORDER_PRODUCT_CONNECTION WHERE ORDER_ID=? AND PRODUCT_ID=?";
	private static final int ONE_UPDATED_ROW = 1;

	@Override
	public List<OrderProductConnection> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(OrderProductConnection orderProductConnection) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER_PRODUCT_CONNECTION)) {
			statement.setLong(1, orderProductConnection.getOrderId());
			statement.setLong(2, orderProductConnection.getProductId());
			statement.setInt(3, orderProductConnection.getAmountProducts());
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(OrderProductConnection orderProductConnection) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_PRODUCT_CONNECTION)) {
			statement.setInt(1, orderProductConnection.getAmountProducts());
			statement.setLong(2, orderProductConnection.getOrderId());
			statement.setLong(3, orderProductConnection.getProductId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == ONE_UPDATED_ROW;
	}
	
	@Override
	public boolean increaseAmountOfProduct(OrderProductConnection orderProductConnection) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INCREASE_AMOUNT_ORDER_PRODUCT_CONNECTION)) {
			statement.setInt(1, orderProductConnection.getAmountProducts());
			statement.setLong(2, orderProductConnection.getOrderId());
			statement.setLong(3, orderProductConnection.getProductId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == ONE_UPDATED_ROW;
	}
	
	@Override
	public Map<Product, Integer> findByOrderId(Long orderId) throws DaoException {
		Map<Product, Integer> products = new HashMap<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement(SQL_SELECT_ORDER_PRODUCT_CONNECTION_BY_ORDER_ID)) {
			statement.setLong(1, orderId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				Integer amountProduct = resultSet.getInt(ColumnName.ORDER_PRODUCT_CONNECTION_AMOUNT_OF_PRODUCT);
				products.put(product, amountProduct);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}

	@Override
	public boolean delete(OrderProductConnection orderProductConnection) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_PRODUCT_CONNECTION)) {
			statement.setLong(1, orderProductConnection.getOrderId());
			statement.setLong(2, orderProductConnection.getProductId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == ONE_UPDATED_ROW;
	}
}
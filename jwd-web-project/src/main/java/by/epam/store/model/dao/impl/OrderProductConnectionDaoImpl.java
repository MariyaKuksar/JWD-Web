package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.entity.OrderProductConnection;

public class OrderProductConnectionDaoImpl implements OrderProductConnectionDao {
	private static final String SQL_UPDATE_ORDER_PRODUCT_CONNECTION = "UPDATE ORDER_PRODUCT_CONNECTION SET AMOUNT_OF_PRODUCT=AMOUNT_OF_PRODUCT+? WHERE ORDER_ID=? AND PRODUCT_ID=?";
	private static final String SQL_INSERT_ORDER_PRODUCT_CONNECTION = "INSERT INTO ORDER_PRODUCT_CONNECTION (ORDER_ID, PRODUCT_ID, AMOUNT_OF_PRODUCT) VALUES (?, ?, ?)";

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
			statement.setLong(2, orderProductConnection.getProduct().getProductId());
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
			statement.setLong(3, orderProductConnection.getProduct().getProductId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == 1;
	}
}

package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;

public class OrderDaoImpl implements OrderDao {
	private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_ORDER_BASKET_BY_USER_ID = "SELECT ID FROM ORDERS WHERE USER_ID=? AND STATUS='BASKET'";
	private static final String SQL_INSERT_ORDER = "INSERT INTO ORDERS (USER_ID, STATUS) VALUES (?, 'BASKET')";
	private static final String SQL_UPDATE_ORDER = "UPDATE ORDERS SET DATE_TIME=?, STATUS=?, PAYMENT_METHOD=?, COST=?, DELIVERY_METHOD=?, CITY=?, STREET=?, HOUSE=?, APARTMENT=? WHERE ID=?";
	private static final String SQL_SELECT_ORDERS_BY_USER_ID = "SELECT ID, USER_ID, DATE_TIME, STATUS, PAYMENT_METHOD, COST, DELIVERY_METHOD, CITY, STREET, HOUSE, APARTMENT FROM ORDERS WHERE USER_ID=? AND STATUS!='BASKET'";
	private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE ORDERS SET STATUS=? WHERE ID=?";
	private static final String SQL_SELECT_ORDERS_BY_STATUS = "SELECT ID, USER_ID, DATE_TIME, STATUS, PAYMENT_METHOD, COST, DELIVERY_METHOD, CITY, STREET, HOUSE, APARTMENT FROM ORDERS WHERE STATUS=?";
	private static final int ONE_UPDATED_ROW = 1;

	@Override
	public List<Order> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Order order) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, order.getUserId());
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				order.setOrderId(resultSet.getLong(1));
				logger.debug("create order" + order.toString());
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(Order order) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {
			statement.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
			statement.setString(2, String.valueOf(order.getOrderStatus()));
			statement.setString(3, String.valueOf(order.getPaymentMethod()));
			statement.setBigDecimal(4, order.getCost());
			statement.setString(5, String.valueOf(order.getDeliveryMethod()));
			statement.setString(6, order.getAddress().getCity());
			statement.setString(7, order.getAddress().getStreet());
			statement.setString(8, order.getAddress().getHouse());
			statement.setString(9, order.getAddress().getApartment());
			statement.setLong(10, order.getOrderId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == ONE_UPDATED_ROW;
	}

	@Override
	public Optional<Long> findOrderBasketId(Long userId) throws DaoException {
		Optional<Long> orderBasketIdOptional;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BASKET_BY_USER_ID)) {
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Long orderBasketId = resultSet.getLong(ColumnName.ORDERS_ID);
				orderBasketIdOptional = Optional.of(orderBasketId);
			} else {
				logger.info("userId " + userId + "don't have order basket");
				orderBasketIdOptional = Optional.empty();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return orderBasketIdOptional;
	}

	@Override
	public List<Order> findOrdersByUserId(Long userId) throws DaoException {
		List<Order> orders = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_USER_ID)) {
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Order order = DaoEntityBuilder.buildOrder(resultSet);
				orders.add(order);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return orders;
	}

	@Override
	public boolean updateStatus(String orderId, OrderStatus orderStatus) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_STATUS)) {
			statement.setString(1, String.valueOf(orderStatus));
			statement.setString(2, orderId);
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == ONE_UPDATED_ROW;
	}

	@Override
	public List<Order> findOrdersByStatus(String orderStatus) throws DaoException {
		List<Order> orders = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_STATUS)) {
			statement.setString(1, orderStatus);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Order order = DaoEntityBuilder.buildOrder(resultSet);
				orders.add(order);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return orders;
	}
}

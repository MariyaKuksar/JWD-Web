package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.entity.Order;
import by.epam.store.util.ColumnName;

public class OrderDaoImpl implements OrderDao {
	private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_ORDER_BASKET_BY_USER_ID = "SELECT ID FROM ORDERS WHERE USER_ID=? AND STATUS='BASKET'";
	private static final String SQL_INSERT_ORDER = "INSERT INTO ORDERS (USER_ID, STATUS) VALUES (?, 'BASKET')";
	

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
			} else {
				throw new DaoException("database error");
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(Order entity) throws DaoException {
		// TODO Auto-generated method stub
		return false;
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
}

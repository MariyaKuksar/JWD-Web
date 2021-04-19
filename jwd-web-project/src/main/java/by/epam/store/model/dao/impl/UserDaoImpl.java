package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.UserDao;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.entity.UserStatus;

public class UserDaoImpl implements UserDao {
	private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_ALL_USERS = "SELECT ID, NAME, PHONE FROM USERS";
	private static final String SQL_SELECT_USERS_BY_NAME = "SELECT ID, NAME, PHONE FROM USERS WHERE NAME LIKE ?";
	private static final String SQL_SELECT_USERS_BY_LOGIN = "SELECT ID, LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS FROM USERS WHERE LOGIN=?";
	private static final String PERCENT = "%";
	private static final String SQL_INSERT_USER = "INSERT INTO USERS (LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_STATUS = "UPDATE USERS SET STATUS=? WHERE ID=? AND STATUS=?";

	@Override
	public Optional<User> findUserByLogin(String login) throws DaoException {
		Optional<User> userOptional;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong(ColumnName.USERS_ID));
				user.setLogin(resultSet.getString(ColumnName.USERS_LOGIN));
				user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
				user.setRole(UserRole.valueOf(resultSet.getString(ColumnName.USERS_ROLE).toUpperCase()));
				user.setName(resultSet.getString(ColumnName.USERS_NAME));
				user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
				user.setStatus(UserStatus.valueOf(resultSet.getString(ColumnName.USERS_STATUS.toUpperCase())));
				userOptional = Optional.of(user);
			} else {
				userOptional = Optional.empty();
				logger.info("user " + login + " not found in the database");
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return userOptional;
	}

	@Override
	public List<User> findUsersByName(String userName) throws DaoException {
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_NAME)) {
			statement.setString(1, userName + PERCENT);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getLong(ColumnName.USERS_ID));
				user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
				user.setName(resultSet.getString(ColumnName.USERS_NAME));
				users.add(user);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return users;
	}

	@Override
	public List<User> findAll() throws DaoException {
		List<User> users = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt(ColumnName.USERS_ID));
				user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
				user.setName(resultSet.getString(ColumnName.USERS_NAME));
				users.add(user);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return users;
	}

	@Override
	public long create(User user) throws DaoException {
		long userId;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, String.valueOf(user.getRole()));
			statement.setString(4, user.getName());
			statement.setString(5, user.getPhone());
			statement.setString(6, String.valueOf(user.getStatus()));
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				userId = resultSet.getLong(1);
			} else {
				throw new DaoException("database error");
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return userId;
	}

	@Override
	public boolean update(User user) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeUserStatus(long id, UserStatus statusFrom, UserStatus statusTo) throws DaoException {
		boolean statusChanged;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
			statement.setString(1, String.valueOf(statusTo));
			statement.setString(2, String.valueOf(id));
			statement.setString(3, String.valueOf(statusFrom));
			statusChanged = statement.executeUpdate() == 1;
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		logger.debug("changeUserStatus = " + statusChanged);
		return statusChanged;
	}
}

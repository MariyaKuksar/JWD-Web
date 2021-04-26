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
import by.epam.store.model.dao.UserDao;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserStatus;
import by.epam.store.model.entity.builder.UserBuilder;

public class UserDaoImpl implements UserDao {
	private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_ALL_USERS = "SELECT ID, LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS FROM USERS";
	private static final String SQL_SELECT_USERS_BY_NAME = "SELECT ID, LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS FROM USERS WHERE NAME LIKE ?";
	private static final String SQL_SELECT_USERS_BY_LOGIN = "SELECT ID, LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS FROM USERS WHERE LOGIN=?";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String SQL_INSERT_USER = "INSERT INTO USERS (LOGIN, PASSWORD, ROLE, NAME, PHONE, STATUS) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_STATUS = "UPDATE USERS SET STATUS=? WHERE ID=? AND STATUS=?";
	private static final String SQL_UPDATE_USER = "UPDATE USERS SET LOGIN=?, PASSWORD=?, NAME=?, PHONE=? WHERE ID=?";

	@Override
	public Optional<User> findUserByLogin(String login) throws DaoException {
		Optional<User> userOptional;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			List<User> usersList = UserBuilder.getInstance().build(resultSet);
			if (!usersList.isEmpty()) {
				User user = usersList.get(0);
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
		List<User> users;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_NAME)) {
			statement.setString(1, userName + ZERO_OR_MORE_CHARACTERS);
			ResultSet resultSet = statement.executeQuery();
			users = UserBuilder.getInstance().build(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return users;
	}

	@Override
	public List<User> findAll() throws DaoException {
		List<User> users;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
			users = UserBuilder.getInstance().build(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return users;
	}

//нужно ли тут возвращающее значение
	@Override
	public boolean create(User user) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, String.valueOf(user.getRole()));
			statement.setString(4, user.getName());
			statement.setString(5, user.getPhone());
			statement.setString(6, String.valueOf(user.getStatus()));
			numberUpdatedRows = statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				user.setUserId(resultSet.getLong(1));
			} else {
				throw new DaoException("database error");
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == 1;
	}

	@Override
	public boolean update(User user) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {
			statement.setString(1, user.getLogin());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getPassword());
			statement.setLong(5, user.getUserId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == 1;
	}

	@Override
	public boolean changeUserStatus(String id, UserStatus statusFrom, UserStatus statusTo) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
			statement.setString(1, String.valueOf(statusTo));
			statement.setString(2, id);
			statement.setString(3, String.valueOf(statusFrom));
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return numberUpdatedRows == 1;
	}
}

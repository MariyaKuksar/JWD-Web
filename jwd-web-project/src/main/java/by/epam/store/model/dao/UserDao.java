package by.epam.store.model.dao;

import java.sql.SQLException;
import java.util.Optional;

import by.epam.store.entity.User;
import by.epam.store.entity.UserStatus;
import by.epam.store.model.connection.ConnectionPoolException;

/**
 * The interface for working with database table users
 * 
 * @author Mariya Kuksar
 * @see BaseDao
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * Updates user password
	 * 
	 * @param login    {@link String} user login
	 * @param password {@link String} new password
	 * @return boolean true if the password has been updated, else false
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	boolean updatePassword(String login, String password) throws DaoException;

	/**
	 * Updates user password checking if the entered password is correct
	 * 
	 * @param login           {@link String} user login
	 * @param newPassword     {@link String} new password
	 * @param currentPassword {@link String} current password
	 * @return boolean true if the password has been updated, else false
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	boolean updatePassword(String login, String newPassword, String currentPassword) throws DaoException;

	/**
	 * Changes user status
	 * 
	 * @param id         {@link Long} user id
	 * @param statusFrom {@link UserStatus} current user status
	 * @param statusTo   {@link UserStatus} new user status
	 * @return boolean true if the user status has been updated, else false
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	boolean changeUserStatus(Long id, UserStatus statusFrom, UserStatus statusTo) throws DaoException;

	/**
	 * Looking for user by id
	 * 
	 * @param userId {@link String} user id
	 * @return {@link Optional} of {@link User} received from database
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Optional<User> findUserById(String userId) throws DaoException;

	/**
	 * Looking for user by login
	 * 
	 * @param login {@link String} user login
	 * @return {@link Optional} of {@link User} received from database
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	Optional<User> findUserByLogin(String login) throws DaoException;
}

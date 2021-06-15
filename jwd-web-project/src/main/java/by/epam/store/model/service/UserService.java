package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.User;
import by.epam.store.model.dao.DaoException;

/**
 * The interface for user operations
 * 
 * @author Mariya Kuksar
 */
public interface UserService {

	/**
	 * Registers a new user
	 * 
	 * @param userInfo {@link Map} of {@link String} and {@link String} the names of
	 *                 the {@link User} fields and its values
	 * @return boolean true if the user is registered and a registration
	 *         confirmation message has been sent, else false
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if user info is invalid
	 */
	boolean registration(Map<String, String> userInfo) throws ServiceException, InvalidDataException;

	/**
	 * Activates an inactive user
	 * 
	 * @param userId {@link String} user id
	 * @return boolean true if user has been activated, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean activation(String userId) throws ServiceException;

	/**
	 * Checks login and password
	 * 
	 * @param login    {@link String} user login
	 * @param password {@link String} user password
	 * @return {@link Optional} of {@link User} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<User> authorization(String login, String password) throws ServiceException;

	/**
	 * Changes forgotten password
	 * 
	 * @param login {@link String} user login
	 * @return boolean true if password has been changed, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean changeForgottenPassword(String login) throws ServiceException;

	/**
	 * Changes password
	 * 
	 * @param login       {@link String} user login
	 * @param password    {@link String} current password
	 * @param newPassword {@link String} new password
	 * @return boolean true if password has been changed, else false
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if login or new password is invalid
	 */
	boolean changePassword(String login, String password, String newPassword)
			throws ServiceException, InvalidDataException;

	/**
	 * Changes user data
	 * 
	 * @param userInfo {@link Map} of {@link String} and {@link String} the names of
	 *                 the {@link User} fields and its values
	 * @return boolean true if user data has been changed, else false
	 * @throws ServiceException     if {@link DaoException} occurs
	 * @throws InvalidDataException if user info is invalid
	 */
	boolean changeUserData(Map<String, String> userInfo) throws ServiceException, InvalidDataException;

	/**
	 * Blocks the user
	 * 
	 * @param userId {@link String} user id
	 * @return boolean true if user has been blocked, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean blockUser(String userId) throws ServiceException;

	/**
	 * Unblocks the user
	 * 
	 * @param userId {@link String} user id
	 * @return boolean true if user has been unblocked, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean unblockUser(String userId) throws ServiceException;

	/**
	 * Sends messages to users
	 * 
	 * @param email   {@link String} user email (login)
	 * @param message {@link String} message text
	 * @return boolean true if message has been sent, else false
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	boolean sendMessage(String email, String message) throws ServiceException;

	/**
	 * Gives all users
	 * 
	 * @return {@link List} of {@link User} received from database if users are
	 *         found, else emptyList
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	List<User> takeAllUsers() throws ServiceException;

	/**
	 * Gives the user by id
	 * 
	 * @param userId {@link String} user id
	 * @return {@link Optional} of {@link User} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<User> takeUserById(String userId) throws ServiceException;

	/**
	 * Gives the user by login
	 * 
	 * @param login {@link String} user login
	 * @return {@link Optional} of {@link User} received from database
	 * @throws ServiceException if {@link DaoException} occurs
	 */
	Optional<User> takeUserByLogin(String login) throws ServiceException;
}

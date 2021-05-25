package by.epam.store.model.dao;

import java.util.Optional;

import by.epam.store.entity.User;
import by.epam.store.entity.UserStatus;

public interface UserDao extends BaseDao<User> {

	boolean changeUserStatus(Long id, UserStatus statusFrom, UserStatus statusTo) throws DaoException;

	Optional<User> findUserByLogin(String login) throws DaoException;

	boolean updatePassword(String login, String password) throws DaoException;

	boolean updatePassword(String login, String newPassword, String currentPassword) throws DaoException;

	Optional<User> findUserById(String userId) throws DaoException;
}

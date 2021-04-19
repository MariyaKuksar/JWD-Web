package by.epam.store.model.dao;

import java.util.List;
import java.util.Optional;

import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserStatus;

public interface UserDao extends BaseDao<User> {
	boolean changeUserStatus(long id, UserStatus statusFrom, UserStatus statusTo) throws DaoException;
	
	Optional<User> findUserByLogin(String login) throws DaoException;

	List<User> findUsersByName(String userName) throws DaoException;	
}
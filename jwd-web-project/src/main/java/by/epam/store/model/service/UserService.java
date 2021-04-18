package by.epam.store.model.service;

import java.util.List;
import java.util.Optional;

import by.epam.store.model.entity.User;

public interface UserService {
	List<String> registration(User user) throws ServiceException;
	
	boolean activation(String userId) throws ServiceException;	
	
	Optional<User> authorization(String login, String password) throws ServiceException;
	
	List<User> findAllUsers() throws ServiceException;

	List<User> findUsersByName(String userName) throws ServiceException;	
}

package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.model.entity.User;

public interface UserService {
	List<String> registration(Map <String,String> userInfo) throws ServiceException;
	
	boolean activation(String userId) throws ServiceException;	
	
	Optional<User> authorization(String login, String password) throws ServiceException;
	
	boolean changeForgottenPassword(String login) throws ServiceException;	
	
	List<User> findAllUsers() throws ServiceException;

	List<User> findUsersByName(String userName) throws ServiceException;
}

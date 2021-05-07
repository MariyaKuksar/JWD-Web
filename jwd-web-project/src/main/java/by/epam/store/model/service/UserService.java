package by.epam.store.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.User;

public interface UserService {
	
	void registration(Map <String,String> userInfo) throws ServiceException, InvalidDataException;
	
	boolean activation(String userId) throws ServiceException;	
	
	Optional<User> authorization(String login, String password) throws ServiceException;
	
	boolean changeForgottenPassword(String login) throws ServiceException;	
	
	List<User> takeAllUsers() throws ServiceException;

	List<User> takeUsersByName(String userName) throws ServiceException;
}

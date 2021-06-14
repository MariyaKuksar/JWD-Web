package by.epam.store.entity.builder.impl;

import java.util.Map;

import static by.epam.store.controller.command.ParameterAndAttribute.*;
import by.epam.store.entity.User;
import by.epam.store.entity.builder.EntityBuilder;

/**
 * The builder is responsible for building user
 * 
 * @author Mariya Kuksar
 */
public class UserBuilder implements EntityBuilder<User> {
	private static final UserBuilder instance = new UserBuilder();

	private UserBuilder() {
	}

	/**
	 * Get instance of this class
	 * 
	 * @return {@link UserBuilder} instance
	 */
	public static UserBuilder getInstance() {
		return instance;
	}

	@Override
	public User build(Map<String, String> userInfo) {
		User user = new User();
		user.setLogin(userInfo.get(LOGIN));
		user.setPassword(userInfo.get(PASSWORD));
		user.setName(userInfo.get(USER_NAME));
		user.setPhone(userInfo.get(PHONE));
		return user;
	}
}

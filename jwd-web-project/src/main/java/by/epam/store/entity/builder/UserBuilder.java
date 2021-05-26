package by.epam.store.entity.builder;

import java.util.Map;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.User;

public class UserBuilder implements EntityBuilder<User> {
	private static final UserBuilder instance = new UserBuilder();

	private UserBuilder() {
	}

	public static UserBuilder getInstance() {
		return instance;
	}

	@Override
	public User build(Map<String, String> userInfo) {
		User user = new User();
		user.setLogin(userInfo.get(ParameterAndAttribute.LOGIN));
		user.setPassword(userInfo.get(ParameterAndAttribute.PASSWORD));	
		user.setName(userInfo.get(ParameterAndAttribute.USER_NAME));
		user.setPhone(userInfo.get(ParameterAndAttribute.PHONE));
		return user;
	}
}

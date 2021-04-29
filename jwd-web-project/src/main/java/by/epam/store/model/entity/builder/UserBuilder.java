package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.entity.UserStatus;
import by.epam.store.util.ColumnName;
import by.epam.store.util.ParameterAndAttribute;

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
		user.setRole(UserRole.CLIENT);
		user.setName(userInfo.get(ParameterAndAttribute.USER_NAME));
		user.setPhone(userInfo.get(ParameterAndAttribute.PHONE));
		user.setStatus(UserStatus.INACTIVE);
		return user;
	}

	@Override
	public User build(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserId(resultSet.getLong(ColumnName.USERS_ID));
		user.setLogin(resultSet.getString(ColumnName.USERS_LOGIN));
		user.setPassword(resultSet.getString(ColumnName.USERS_PASSWORD));
		user.setRole(UserRole.valueOf(resultSet.getString(ColumnName.USERS_ROLE).toUpperCase()));
		user.setName(resultSet.getString(ColumnName.USERS_NAME));
		user.setPhone(resultSet.getString(ColumnName.USERS_PHONE));
		user.setStatus(UserStatus.valueOf(resultSet.getString(ColumnName.USERS_STATUS.toUpperCase())));
		return user;
	}
}

package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.model.dao.ColumnName;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.UserRole;
import by.epam.store.model.entity.UserStatus;

public class UserBuilder implements EntityBuilder<User> {
	private static final UserBuilder instance = new UserBuilder();

	private UserBuilder() {
	}

	public static UserBuilder getInstance() {
		return instance;
	}

	@Override
	public User build(HttpServletRequest request) {
		User user = new User();
		user.setName(request.getParameter(ParameterAndAttribute.USER_NAME));
		user.setPhone(request.getParameter(ParameterAndAttribute.PHONE));
		user.setLogin(request.getParameter(ParameterAndAttribute.LOGIN));
		user.setPassword(request.getParameter(ParameterAndAttribute.PASSWORD));
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

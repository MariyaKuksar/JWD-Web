package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface EntityBuilder <T> {
	T build (HttpServletRequest request);
	
	List<T> build (ResultSet resultSet) throws SQLException;
}

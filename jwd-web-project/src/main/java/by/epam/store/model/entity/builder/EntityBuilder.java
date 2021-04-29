package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface EntityBuilder <T> {
	T build (Map<String, String> entityInfo);
	
	T build (ResultSet resultSet) throws SQLException;
}

package by.epam.store.model.entity.builder;

import java.util.Map;

public interface EntityBuilder <T> {
	
	T build (Map<String, String> entityInfo);
}

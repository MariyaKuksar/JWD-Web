package by.epam.store.entity.builder;

import java.util.Map;

public interface EntityBuilder <T> {
	
	T build (Map<String, String> entityInfo);
}

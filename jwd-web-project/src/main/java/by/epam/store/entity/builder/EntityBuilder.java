package by.epam.store.entity.builder;

import java.util.Map;

/**
 * The interface for different entity builders
 * 
 * @author Mariya Kuksar
 * @param <T> entity
 */
public interface EntityBuilder<T> {

	/**
	 * Builds entity
	 * 
	 * @param entityInfo {@link Map} of {@link String} and {@link String} the names
	 *                   of the entity fields and its values
	 * @return entity
	 */
	T build(Map<String, String> entityInfo);
}

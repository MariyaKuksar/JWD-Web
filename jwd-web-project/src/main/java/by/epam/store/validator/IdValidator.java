package by.epam.store.validator;

/**
 * Validates id
 * 
 * @author Mariya Kuksar
 */
public final class IdValidator {
	private static final String ID_PATTERN = "\\d{1,19}";

	private IdValidator() {
	}

	/**
	 * Checks if id is valid
	 * 
	 * @param id {@link String}
	 * @return boolean true if id is valid, else false
	 */
	public static boolean isValidId(String id) {
		return (id != null) ? id.matches(ID_PATTERN) : false;
	}
}

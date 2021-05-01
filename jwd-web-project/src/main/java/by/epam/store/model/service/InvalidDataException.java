package by.epam.store.model.service;

import java.util.List;

public class InvalidDataException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<String> errorDescription;
	
	public InvalidDataException() {
		super();
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
	}
	
	public InvalidDataException(String message, List<String> errorDescription) {
		super(message);
		this.errorDescription = errorDescription;
	}

	public List<String> getErrorDescription() {
		return errorDescription;
	}
}

package by.epam.store.model.service;

public class ServiceException extends Exception {
private static final long serialVersionUID = 1L;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable e) {
		super(e);
	}
	
	public ServiceException(String message, Throwable e) {
		super(message, e);
	}
}

package by.epam.store.model.service;

import by.epam.store.model.service.impl.CatalogServiceImpl;
import by.epam.store.model.service.impl.OrderServiceImpl;
import by.epam.store.model.service.impl.UserServiceImpl;

/**
 * The factory is responsible for service instances
 * 
 * @author Mariya Kuksar
 */
public final class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private final UserService userService = new UserServiceImpl();
	private final CatalogService catalogService = new CatalogServiceImpl();
	private final OrderService orderService = new OrderServiceImpl();

	private ServiceFactory() {
	}

	/**
	 * Get instance of this class
	 * 
	 * @return {@link ServiceFactory} instance
	 */
	public static ServiceFactory getInstance() {
		return instance;
	}

	/**
	 * Get instance of {@link UserService}
	 * 
	 * @return {@link UserService} instance
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Get instance of {@link CatalogService}
	 * 
	 * @return {@link CatalogService} instance
	 */
	public CatalogService getCatalogService() {
		return catalogService;
	}

	/**
	 * Get instance of {@link OrderService}
	 * 
	 * @return {@link OrderService} instance
	 */
	public OrderService getOrderService() {
		return orderService;
	}
}

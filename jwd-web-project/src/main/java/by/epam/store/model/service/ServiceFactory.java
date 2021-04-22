package by.epam.store.model.service;

import by.epam.store.model.service.impl.CatalogServiceImpl;
import by.epam.store.model.service.impl.OrderServiceImpl;
import by.epam.store.model.service.impl.UserServiceImpl;

public final class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private final UserService userService = new UserServiceImpl();
	private final CatalogService catalogService = new CatalogServiceImpl();
	private final OrderService orderService = new OrderServiceImpl();
	
	private ServiceFactory() {
	}
	
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public CatalogService getCatalogService() {
		return catalogService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
}

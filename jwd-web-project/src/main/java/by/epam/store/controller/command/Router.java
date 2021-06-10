package by.epam.store.controller.command;

/**
 * Describes the router
 * 
 * @author Mariya Kuksar
 */
public final class Router {

	/**
	 * Describes all route types
	 * 
	 * @author Mariya Kuksar
	 */
	public enum RouteType {
		FORWARD, REDIRECT
	}

	private final String pagePath;
	private final RouteType routeType;

	/**
	 * Constructs a new Router with the specified page path and route type
	 * 
	 * @param pagePath  {@link PagePath} path to page
	 * @param routeType {@link RouteType} route type
	 */
	public Router(String pagePath, RouteType routeType) {
		this.pagePath = pagePath;
		this.routeType = routeType;
	}

	public String getPagePath() {
		return pagePath;
	}

	public RouteType getRouteType() {
		return routeType;
	}
}

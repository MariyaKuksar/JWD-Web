package by.epam.store.controller.command;

public final class Router {
	private final String pagePath;
	private final RouteType routeType;

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

	public enum RouteType {
		FORWARD, REDIRECT
	}
}

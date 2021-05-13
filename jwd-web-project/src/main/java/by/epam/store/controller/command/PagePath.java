package by.epam.store.controller.command;

public final class PagePath {
	public static final String ERROR = "jsp/error.jsp";
	public static final String MAIN = "jsp/main.jsp";
	public static final String LOGIN = "jsp/login.jsp";
	public static final String REGISTRATION = "jsp/registration.jsp";
	public static final String FORGOT_PASSWORD = "jsp/forgot_password.jsp";
	public static final String ADDED_PRODUCT = "jsp/admin/adding_product.jsp";
	public static final String BASKET = "jsp/client/basket.jsp";
	public static final String ORDERS = "jsp/orders.jsp";
	public static final String PROFILE = "jsp/client/profile.jsp";
	public static final String CLIENTS = "jsp/admin/clients.jsp";

	public static final String GO_TO_MAIN_PAGE = "controller?command=go_to_main_page";
	public static final String SHOW_PRODUCTS_FROM_CATEGORY = "controller?command=show_products_from_category&categoryId=";
	public static final String FIND_PRODUCTS_BY_NAME = "controller?command=find_products_by_name&productName=";
	public static final String GO_TO_BASKET_PAGE = "controller?command=go_to_basket_page";
	public static final String GO_TO_ORDERS_PAGE = "controller?command=go_to_orders_page";
	public static final String GO_TO_PROFILE_PAGE = "controller?command=go_to_profile_page";
	public static final String GO_TO_CLIENTS_PAGE = "controller?command=go_to_clients_page";
	public static final String FIND_USER_BY_LOGIN = "controller?command=find_user_by_login&login=";
	
	private PagePath() {
	}
}

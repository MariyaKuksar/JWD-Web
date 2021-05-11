package by.epam.store.controller.command;

public final class PagePath {
	public static final String INDEX = "index.jsp";
	public static final String ERROR = "jsp/error.jsp";
	public static final String ADMIN = "jsp/admin/admin.jsp";
	public static final String USERS = "jsp/admin/users.jsp";
	public static final String CLIENT = "jsp/client/client.jsp";
	public static final String MAIN = "jsp/main.jsp";
	public static final String LOGIN = "jsp/login.jsp";
	public static final String REGISTRATION = "jsp/registration.jsp";
	public static final String FORGOT_PASSWORD = "jsp/forgot_password.jsp";
	public static final String ADDED_PRODUCT = "jsp/admin/adding_product.jsp";
	public static final String BASKET = "jsp/client/basket.jsp";
	public static final String ORDERS = "jsp/orders.jsp";
	public static final String PROFILE = "jsp/client/profile.jsp";

	public static final String GO_TO_MAIN_PAGE = "controller?command=go_to_main_page";
	public static final String SHOW_PRODUCTS_FROM_CATEGORY = "controller?command=show_products_from_category&categoryId=";
	public static final String FIND_PRODUCTS_BY_NAME = "controller?command=find_products_by_name&productName=";
	public static final String GO_TO_BASKET_PAGE = "controller?command=go_to_basket_page";
	public static final String GO_TO_ORDERS_PAGE = "controller?command=go_to_orders_page";
	public static final String GO_TO_PROFILE_PAGE = "controller?command=go_to_profile_page";
	
	private PagePath() {
	}
}

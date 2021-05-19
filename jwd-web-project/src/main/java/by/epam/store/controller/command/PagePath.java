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
	public static final String PRODUCTS = "jsp/admin/products.jsp";

	public static final String GO_TO_MAIN_PAGE = "controller?command=go_to_main_page";
	public static final String SHOW_PRODUCTS_FROM_CATEGORY = "controller?command=show_products_from_category&categoryId=";
	public static final String FIND_PRODUCTS_BY_NAME = "controller?command=find_products_by_name&productName=";
	public static final String GO_TO_BASKET_PAGE = "controller?command=go_to_basket_page";
	public static final String GO_TO_ORDERS_PAGE = "controller?command=go_to_orders_page";
	public static final String GO_TO_PROFILE_PAGE = "controller?command=go_to_profile_page";
	public static final String GO_TO_CLIENTS_PAGE = "controller?command=go_to_clients_page";
	public static final String FIND_USER_BY_LOGIN = "controller?command=find_user_by_login&login=";
	public static final String CONFIRM_REGISTRATION = "controller?command=confirm_registration&userId=";
	public static final String FIND_ORDERS_BY_STATUS = "controller?command=find_orders_by_status&status=";
	public static final String FIND_ORDER_BY_ID = "controller?command=find_order_by_id&orderId=";
	public static final String FIND_USER_ORDERS = "controller?command=find_user_orders&login=";
	public static final String SHOW_PRODUCTS_IN_STOCK = "controller?command=show_products_in_stock";
	public static final String SHOW_PRODUCTS_ON_ORDER = "controller?command=show_products_on_order";
	
	private PagePath() {
	}
}

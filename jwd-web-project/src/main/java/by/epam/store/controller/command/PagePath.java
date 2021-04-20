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

	public static final String GO_TO_MAIN_PAGE = "controller?command=go_to_main_page";
	public static final Object GO_TO_SHOW_PRODUCTS = "controller?command=show_products_from_category&categoryId=";
	
	private PagePath() {
	}
}

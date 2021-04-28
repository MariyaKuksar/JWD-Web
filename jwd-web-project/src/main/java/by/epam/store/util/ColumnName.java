package by.epam.store.util;

public final class ColumnName {
	public static final String USERS_ID = "id";
	public static final String USERS_PHONE = "phone";
	public static final String USERS_NAME = "name";
	public static final String USERS_PASSWORD = "password";
	public static final String USERS_ROLE = "role";
	public static final String USERS_STATUS = "status";
	public static final String USERS_LOGIN = "login";
	
	public static final String PRODUCT_CATEGORIES_ID = "id";
	public static final String PRODUCT_CATEGORIES_CATEGORY = "category";
	public static final String PRODUCT_CATEGORIES_IMAGE_NAME = "image_name";
	
	public static final String PRODUCTS_ID = "id";
	public static final String PRODUCTS_CATEGORY_ID = "category_id";
	public static final String PRODUCTS_NAME = "name";
	public static final String PRODUCTS_IMAGE_NAME = "image_name";
	public static final String PRODUCTS_PRICE = "price";
	public static final String PRODUCTS_AMOUNT = "amount";
	
	public static final String ORDERS_ID = "id";
	public static final String ORDERS_USER_ID = "user_id";
	public static final String ORDERS_DATA_TIME = "data_time";
	public static final String ORDERS_STATUS = "orders_status";
	public static final String ORDERS_PAYMENT_METHOD = "payment_method";
	public static final String ORDERS_PRICE = "price";
	
	private ColumnName() {
	}
}

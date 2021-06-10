package by.epam.store.model.dao;

/**
 * Describes all column name
 * 
 * @author Mariya Kuksar
 */
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
	public static final String PRODUCTS_QUANTITY = "quantity";

	public static final String ORDERS_ID = "id";
	public static final String ORDERS_USER_ID = "user_id";
	public static final String ORDERS_DATE_TIME = "date_time";
	public static final String ORDERS_STATUS = "status";
	public static final String ORDERS_PAYMENT_METHOD = "payment_method";
	public static final String ORDERS_COST = "cost";
	public static final String ORDERS_DELIVERY_METHOD = "delivery_method";
	public static final String ORDERS_CITY = "city";
	public static final String ORDERS_STREET = "street";
	public static final String ORDERS_HOUSE = "house";
	public static final String ORDERS_APARTMENT = "apartment";

	public static final String ORDER_PRODUCT_CONNECTION_QUANTITY_OF_PRODUCT = "quantity_of_product";

	private ColumnName() {
	}
}

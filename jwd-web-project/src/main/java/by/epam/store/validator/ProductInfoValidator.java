package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.Product;
import by.epam.store.util.MessageKey;
import by.epam.store.util.XssProtectionUtil;

/**
 * Validates product info
 * 
 * @author Mariya Kuksar
 */
public final class ProductInfoValidator {
	private static final String PRICE_PATTERN = "^[1-9]\\d{0,8}(\\.\\d{2})?$";
	private static final String IMAGE_NAME_PATTERN = "^.{1,40}\\.jpg$";
	private static final String NAME_PATTERN = "^.{1,45}$";
	private static final String QUANTITY_PATTERN = "^\\d{1,2}$";

	private ProductInfoValidator() {
	}

	/**
	 * Looking for invalid product data
	 * 
	 * @param productInfo {@link Map} of {@link String} and {@link String} the names
	 *                    of the {@link Product} fields and its values
	 * @return {@link List} of {@link String} error messages if product info is
	 *         invalid, else emptyList
	 */
	public static List<String> findInvalidData(Map<String, String> productInfo) {
		List<String> errorMessageList = new ArrayList<>();
		if (MapUtils.isEmpty(productInfo)) {
			errorMessageList.add(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return errorMessageList;
		}
		if (!isValidPrice(productInfo.get(ParameterAndAttribute.PRICE))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRICE_MESSAGE);
		}
		if (!isValidName(productInfo.get(ParameterAndAttribute.PRODUCT_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_NAME_MESSAGE);
		} else {
			productInfo.put(ParameterAndAttribute.PRODUCT_NAME,
					XssProtectionUtil.correctText(productInfo.get(ParameterAndAttribute.PRODUCT_NAME)));
		}
		return errorMessageList;
	}

	/**
	 * Checks if price is valid
	 * 
	 * @param price {@link String}
	 * @return boolean true if price is valid, else false
	 */
	public static boolean isValidPrice(String price) {
		return (price != null) ? price.matches(PRICE_PATTERN) : false;
	}

	/**
	 * Checks if image name is valid
	 * 
	 * @param imageName {@link String}
	 * @return boolean true if image name is valid, else false
	 */
	public static boolean isValidImageName(String imageName) {
		return (imageName != null) ? imageName.matches(IMAGE_NAME_PATTERN) : false;
	}

	/**
	 * Checks if name is valid
	 * 
	 * @param name {@link String}
	 * @return boolean true if name is valid, else false
	 */
	public static boolean isValidName(String name) {
		return (name != null) ? name.matches(NAME_PATTERN) : false;
	}

	/**
	 * Checks if quantity is valid
	 * 
	 * @param quantity {@link String}
	 * @return boolean true if quantity is valid, else false
	 */
	public static boolean isValidQuantity(String quantity) {
		return (quantity != null) ? quantity.matches(QUANTITY_PATTERN) : false;
	}
}

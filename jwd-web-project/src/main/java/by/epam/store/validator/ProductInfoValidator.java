package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.XssProtectUtil;

public final class ProductInfoValidator {
	private static final String PRICE_PATTERN = "^\\d{1,8}(\\.\\d{2})?$";
	private static final String IMAGE_NAME_PATTERN = "^.{1,40}\\.jpg$";
	private static final String NAME_PATTERN = "^.{1,45}$";
	private static final String AMOUNT_PATTERN = "^\\d{1,2}$";

	private ProductInfoValidator() {
	}

	public static List<String> findInvalidData(Map<String, String> productInfo) {
		List<String> errorMessageList = new ArrayList<>();
		if (productInfo == null) {
			errorMessageList.add(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return errorMessageList;
		}
		if (!isValidPrice(productInfo.get(ParameterAndAttribute.PRICE))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRICE_MESSAGE);
		}
		if (!isValidImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_IMAGE_NAME_MESSAGE);
		}
		if (!isValidName(productInfo.get(ParameterAndAttribute.PRODUCT_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_NAME_MESSAGE);
		} else {
			productInfo.put(ParameterAndAttribute.PRODUCT_NAME,
					XssProtectUtil.correctText(productInfo.get(ParameterAndAttribute.PRODUCT_NAME)));
		}
		return errorMessageList;
	}

	public static boolean isValidPrice(String price) {
		return (price != null) ? price.matches(PRICE_PATTERN) : false;
	}

	public static boolean isValidImageName(String imageName) {
		return (imageName != null) ? imageName.matches(IMAGE_NAME_PATTERN) : true;
	}

	public static boolean isValidName(String name) {
		return (name != null) ? name.matches(NAME_PATTERN) : false;
	}

	public static boolean isValidAmount(String amount) {
		return (amount != null) ? amount.matches(AMOUNT_PATTERN) : false;
	}
}

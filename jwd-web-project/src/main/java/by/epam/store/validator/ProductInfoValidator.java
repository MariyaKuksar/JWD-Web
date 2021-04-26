package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;
import by.epam.store.util.XssProtectUtil;

public final class ProductInfoValidator {
	private static final String PRICE_PATTERN = "^\\d{1,8}(\\.\\d{2})?$";
	private static final String IMAGE_NAME_PATTERN = "^.+\\.\\p{Lower}+$";

	private ProductInfoValidator() {
	}

	public static List<String> getErrorMessageList(Map<String, String> productInfo) {
		List<String> errorMessageList = new ArrayList<>();
		//вызываю валидатор из валидатора, норм?
		if (!IdValidator.isValidId(productInfo.get(ParameterAndAttribute.CATEGORY_ID))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_CATEGORY_MESSAGE);
		}
		if (!isValidName(productInfo.get(ParameterAndAttribute.PRODUCT_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRODUCT_NAME_MESSAGE);
		}
		if (!isValidPrice(productInfo.get(ParameterAndAttribute.PRICE))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRICE_MESSAGE);
		}
		if (!isValidImageName(productInfo.get(ParameterAndAttribute.IMAGE_NAME))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_IMAGE_NAME_MESSAGE);
		}
		return errorMessageList;
	}

	public static boolean isValidName(String productName) {
		if (productName == null) {
			return false;
		}
		XssProtectUtil.correctText(productName);
		return true;
	}

	public static boolean isValidPrice(String price) {
		return (price != null) ? price.matches(PRICE_PATTERN) : false;
	}

	public static boolean isValidImageName(String imageName) {
		return (imageName != null) ? imageName.matches(IMAGE_NAME_PATTERN) : false;
	}
}

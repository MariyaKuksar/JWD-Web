package by.epam.store.model.service.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.DeliveryMethod;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.PaymentMethod;
import by.epam.store.util.MessageKey;

/**
 * Validates order info
 * 
 * @author Mariya Kuksar
 */
public final class OrderInfoValidator {
	private static final String COST_PATTERN = "^[1-9]\\d{0,8}(\\.\\d{2})?$";
	private static final String CITY_PATTERN = "^[a-zA-Zа-яА-Я-\\s\\.]{1,45}$";
	private static final String STREET_PATTERN = "^[\\da-zA-Zа-яА-Я-\\s\\.]{1,45}$";
	private static final String HOUSE_PATTERN = "^\\d{1,3}[\\s-/]?[абвгдАБВГД\\d]?$";
	private static final String APARTMENT_PATTERN = "[\\d]{0,4}";

	private OrderInfoValidator() {
	}

	/**
	 * Looking for invalid user data
	 * 
	 * @param orderInfo {@link Map} of {@link String} and {@link String} the names
	 *                  of the {@link Order} fields and its values
	 * @return {@link List} of {@link String} error messages if user info is
	 *         invalid, else emptyList
	 */
	public static List<String> findInvalidData(Map<String, String> orderInfo) {
		List<String> errorMessageList = new ArrayList<>();
		if (MapUtils.isEmpty(orderInfo)) {
			errorMessageList.add(MessageKey.ERROR_IMPOSSIBLE_OPERATION_MESSAGE);
			return errorMessageList;
		}
		if (!OrderInfoValidator.isValidCost(orderInfo.get(ParameterAndAttribute.COST))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PRICE_MESSAGE);
		}
		if (!OrderInfoValidator.isValidPaymentMethod(orderInfo.get(ParameterAndAttribute.PAYMENT_METHOD))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_PAYMENT_METHOD_MESSAGE);
		}
		if (!OrderInfoValidator.isValidDeliveryMethod(orderInfo.get(ParameterAndAttribute.DELIVERY_METHOD))) {
			errorMessageList.add(MessageKey.ERROR_INCORRECT_DELVERY_METHOD_MESSAGE);
		}
		String deliveryMethod = orderInfo.get(ParameterAndAttribute.DELIVERY_METHOD);
		if (deliveryMethod != null && DeliveryMethod.valueOf(deliveryMethod.toUpperCase()) == DeliveryMethod.DELIVERY) {
			if (!isValidCity(orderInfo.get(ParameterAndAttribute.CITY))) {
				errorMessageList.add(MessageKey.ERROR_INCORRECT_CITY_MESSAGE);
			}
			if (!isValidStreet(orderInfo.get(ParameterAndAttribute.STREET))) {
				errorMessageList.add(MessageKey.ERROR_INCORRECT_STREET_MESSAGE);
			}
			if (!isValidHouse(orderInfo.get(ParameterAndAttribute.HOUSE))) {
				errorMessageList.add(MessageKey.ERROR_INCORRECT_HOUSE_MESSAGE);
			}
			if (!isValidApartment(orderInfo.get(ParameterAndAttribute.APARTMENT))) {
				errorMessageList.add(MessageKey.ERROR_INCORRECT_APARTMENT_MESSAGE);
			}
		}
		return errorMessageList;
	}

	/**
	 * Checks if cost is valid
	 * 
	 * @param cost {@link String}
	 * @return boolean true if cost is valid, else false
	 */
	public static boolean isValidCost(String cost) {
		return (cost != null) ? cost.matches(COST_PATTERN) : false;
	}

	/**
	 * Checks if payment method is valid
	 * 
	 * @param paymentMethod {@link String}
	 * @return boolean true if payment method is valid, else false
	 */
	public static boolean isValidPaymentMethod(String paymentMethod) {
		if (paymentMethod == null) {
			return false;
		}
		try {
			PaymentMethod.valueOf(paymentMethod.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if delivery method is valid
	 * 
	 * @param deliveryMethod {@link String}
	 * @return boolean true if delivery method is valid, else false
	 */
	public static boolean isValidDeliveryMethod(String deliveryMethod) {
		if (deliveryMethod == null) {
			return false;
		}
		try {
			DeliveryMethod.valueOf(deliveryMethod.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if city is valid
	 * 
	 * @param city {@link String}
	 * @return boolean true if city is valid, else false
	 */
	public static boolean isValidCity(String city) {
		return (city != null) ? city.matches(CITY_PATTERN) : false;
	}

	/**
	 * Checks if street is valid
	 * 
	 * @param street {@link String}
	 * @return boolean true if street is valid, else false
	 */
	public static boolean isValidStreet(String street) {
		return (street != null) ? street.matches(STREET_PATTERN) : false;
	}

	/**
	 * Checks if house is valid
	 * 
	 * @param house {@link String}
	 * @return boolean true if house is valid, else false
	 */
	public static boolean isValidHouse(String house) {
		return (house != null) ? house.matches(HOUSE_PATTERN) : false;
	}

	/**
	 * Checks if apartment is valid
	 * 
	 * @param apartment {@link String}
	 * @return boolean true if apartment is valid, else false
	 */
	public static boolean isValidApartment(String apartment) {
		return (apartment != null) ? apartment.matches(APARTMENT_PATTERN) : true;
	}

	/**
	 * Checks if order status is valid
	 * 
	 * @param orderStatus {@link String}
	 * @return boolean true if order status is valid, else false
	 */
	public static boolean isValidOrderStatus(String orderStatus) {
		if (orderStatus == null) {
			return false;
		}
		try {
			OrderStatus.valueOf(orderStatus.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}

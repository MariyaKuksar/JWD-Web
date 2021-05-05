package by.epam.store.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.epam.store.model.entity.DeliveryMethod;
import by.epam.store.model.entity.PaymentMethod;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;

public final class OrderInfoValidator {
	private static final String COST_PATTERN = "^\\d{1,8}(\\.\\d{2})?$";
	private static final String CITY_PATTERN = "^[a-zA-Zа-яА-Я-\s\\.]{1,20}$";
	private static final String STREET_PATTERN = "^[\\da-zA-Zа-яА-Я-\s\\.]{1,20}$";
	private static final String HOUSE_PATTERN = "([1-9]\\d*(\\s*[-/]\\s*[1-9]\\d*)?(\\s?[a-zA-Z])?){1,5}";
	private static final String APARTMENT_PATTERN = "\\d{1,3}";
	
	private OrderInfoValidator() {
	}

	public static List<String> findInvalidData(Map<String, String> orderInfo) {
		List<String> errorMessageList = new ArrayList<>();
		// TODO вызываю валидатор из валидатора, норм?
		if (!IdValidator.isValidId(orderInfo.get(ParameterAndAttribute.ORDER_BASKET_ID))
				|| !isValidCost(orderInfo.get(ParameterAndAttribute.COST))
				|| !isValidPaymentMethod(orderInfo.get(ParameterAndAttribute.PAYMENT_METHOD))
				|| !isValidDeliveryMethod(orderInfo.get(ParameterAndAttribute.DELIVERY_METHOD))) {
			errorMessageList.add(MessageKey.ERROR_CHECKOUT_MESSAGE);
		}
		String deliveryMethod = orderInfo.get(ParameterAndAttribute.DELIVERY_METHOD);
		if (deliveryMethod != null && DeliveryMethod.valueOf(deliveryMethod) == DeliveryMethod.DELIVERY) {
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

	public static boolean isValidCost(String cost) {
		return (cost != null) ? cost.matches(COST_PATTERN) : false;
	}

	public static boolean isValidPaymentMethod(String paymentMethod) {
		if (paymentMethod == null) {
			return false;
		}
		try {
			PaymentMethod.valueOf(paymentMethod);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public static boolean isValidDeliveryMethod(String deliveryMethod) {
		if (deliveryMethod == null) {
			return false;
		}
		try {
			DeliveryMethod.valueOf(deliveryMethod);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidCity(String city) {
		return (city != null) ? city.matches(CITY_PATTERN) : false;
	}
	
	public static boolean isValidStreet(String street) {
		return (street != null) ? street.matches(STREET_PATTERN) : false;
	}
	
	public static boolean isValidHouse(String house) {
		return (house != null) ? house.matches(HOUSE_PATTERN) : false;
	}
	
	public static boolean isValidApartment(String apartment) {
		return (apartment != null) ? apartment.matches(APARTMENT_PATTERN) : false;
	}
}

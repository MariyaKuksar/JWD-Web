package by.epam.store.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OrderInfoValidatorTest {

	@DataProvider(name = "validCost")
	public static Object[][] validCost() {
		return new Object[][] { { "25" }, { "352.50" }, { "6565656.00" }, { "5" } };
	}

	@Test(dataProvider = "validCost")
	public void isValidCostPositiveTest(String cost) {
		Assert.assertTrue(OrderInfoValidator.isValidCost(cost));
	}

	@DataProvider(name = "invalidCost")
	public static Object[][] invalidCost() {
		return new Object[][] { { "fdg" }, { "352.50524" }, { null }, { "525h" }, { "0" } };
	}

	@Test(dataProvider = "invalidCost")
	public void isValidCostNegativeTest(String cost) {
		Assert.assertFalse(OrderInfoValidator.isValidCost(cost));
	}

	@DataProvider(name = "validPaymentMethod")
	public static Object[][] validPaymentMethod() {
		return new Object[][] { { "Cash" }, { "CARD" }, { "online" } };
	}

	@Test(dataProvider = "validPaymentMethod")
	public void isValidPaymentMethodPositiveTest(String paymentMethod) {
		Assert.assertTrue(OrderInfoValidator.isValidPaymentMethod(paymentMethod));
	}

	@DataProvider(name = "invalidPaymentMethod")
	public static Object[][] invalidPaymentMethod() {
		return new Object[][] { { "credit" }, { "USD" }, { null } };
	}

	@Test(dataProvider = "invalidPaymentMethod")
	public void isValidPaymentMethodNegativeTest(String paymentMethod) {
		Assert.assertFalse(OrderInfoValidator.isValidPaymentMethod(paymentMethod));
	}

	@DataProvider(name = "validDeliveryMethod")
	public static Object[][] validDeliveryMethod() {
		return new Object[][] { { "pickup" }, { "Delivery" }, { "PICKUP" } };
	}

	@Test(dataProvider = "validDeliveryMethod")
	public void isValidDeliveryMethodPositiveTest(String deliveryMethod) {
		Assert.assertTrue(OrderInfoValidator.isValidDeliveryMethod(deliveryMethod));
	}

	@DataProvider(name = "invalidDeliveryMethod")
	public static Object[][] invalidDeliveryMethod() {
		return new Object[][] { { "post" }, { null }, { "courier" } };
	}

	@Test(dataProvider = "invalidDeliveryMethod")
	public void isValidDeliveryMethodNegativeTest(String deliveryMethod) {
		Assert.assertFalse(OrderInfoValidator.isValidDeliveryMethod(deliveryMethod));
	}

	@DataProvider(name = "validCity")
	public static Object[][] validCity() {
		return new Object[][] { { "Minsk" }, { "Старые Дороги" }, { "Давид-городок" } };
	}

	@Test(dataProvider = "validCity")
	public void isValidCityPositiveTest(String city) {
		Assert.assertTrue(OrderInfoValidator.isValidCity(city));
	}

	@DataProvider(name = "invalidCity")
	public static Object[][] invalidCity() {
		return new Object[][] { { "Minsk1" }, { null }, { "Давид/Городок" } };
	}

	@Test(dataProvider = "invalidCity")
	public void isValidCityNegativeTest(String city) {
		Assert.assertFalse(OrderInfoValidator.isValidCity(city));
	}

	@DataProvider(name = "validStreet")
	public static Object[][] validStreet() {
		return new Object[][] { { "Заречный 1-й переулок" }, { "50 лет Победы" }, { "Karvata" } };
	}

	@Test(dataProvider = "validStreet")
	public void isValidStreetPositiveTest(String street) {
		Assert.assertTrue(OrderInfoValidator.isValidStreet(street));
	}

	@DataProvider(name = "invalidStreet")
	public static Object[][] invalidStreet() {
		return new Object[][] { { null }, { "//" }, { "+375" } };
	}

	@Test(dataProvider = "invalidStreet")
	public void isValidStreetNegativeTest(String street) {
		Assert.assertFalse(OrderInfoValidator.isValidStreet(street));
	}

	@DataProvider(name = "validHouse")
	public static Object[][] validHouse() {
		return new Object[][] { { "3" }, { "10В" }, { "25/3" } };
	}

	@Test(dataProvider = "validHouse")
	public void isValidHousePositiveTest(String house) {
		Assert.assertTrue(OrderInfoValidator.isValidHouse(house));
	}

	@DataProvider(name = "invalidHouse")
	public static Object[][] invalidHouse() {
		return new Object[][] { { "hgf" }, { null }, { "12345678" } };
	}

	@Test(dataProvider = "invalidHouse")
	public void isValidHouseNegativeTest(String house) {
		Assert.assertFalse(OrderInfoValidator.isValidHouse(house));
	}

	@DataProvider(name = "validApartment")
	public static Object[][] validApartment() {
		return new Object[][] { { "3" }, { null }, { "349" } };
	}

	@Test(dataProvider = "validApartment")
	public void isValidApartmentPositiveTest(String apartment) {
		Assert.assertTrue(OrderInfoValidator.isValidApartment(apartment));
	}

	@DataProvider(name = "invalidApartment")
	public static Object[][] invalidApartment() {
		return new Object[][] { { "g" }, { "56а" }, { "12345678" } };
	}

	@Test(dataProvider = "invalidApartment")
	public void isValidApartmentNegativeTest(String apartment) {
		Assert.assertFalse(OrderInfoValidator.isValidApartment(apartment));
	}

	@DataProvider(name = "validOrderStatus")
	public static Object[][] validOrderStatus() {
		return new Object[][] { { "basket" }, { "PLACED" }, { "Canceled" } };
	}

	@Test(dataProvider = "validOrderStatus")
	public void isValidOrderStatusPositiveTest(String orderStatus) {
		Assert.assertTrue(OrderInfoValidator.isValidOrderStatus(orderStatus));
	}

	@DataProvider(name = "invalidOrderStatus")
	public static Object[][] invalidOrderStatus() {
		return new Object[][] { { "checkout" }, { "getting" }, { null } };
	}

	@Test(dataProvider = "invalidOrderStatus")
	public void isValidOrderStatusNegativeTest(String orderStatus) {
		Assert.assertFalse(OrderInfoValidator.isValidOrderStatus(orderStatus));
	}
}

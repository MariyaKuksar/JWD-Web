package by.epam.store.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.store.model.service.validator.UserInfoValidator;

public class UserInfoValidatorTest {

	@DataProvider(name = "validLogin")
	public static Object[][] validLogin() {
		return new Object[][] { { "kuksar@gmail.com" }, { "mistwins@tut.by" }, { "podgayskaya@mail.ru" } };
	}

	@Test(dataProvider = "validLogin")
	public void isValidLoginPositiveTest(String login) {
		Assert.assertTrue(UserInfoValidator.isValidLogin(login));
	}

	@DataProvider(name = "invalidLogin")
	public static Object[][] invalidLogin() {
		return new Object[][] { { "kuksar" }, { null }, { "podgayskaya@mailru" } };
	}

	@Test(dataProvider = "invalidLogin")
	public void isValidLoginNegativeTest(String login) {
		Assert.assertFalse(UserInfoValidator.isValidLogin(login));
	}

	@DataProvider(name = "validPassword")
	public static Object[][] validPassword() {
		return new Object[][] { { "123456" }, { "kuksar" }, { "1992kuksar" } };
	}

	@Test(dataProvider = "validPassword")
	public void isValidPasswordPositiveTest(String password) {
		Assert.assertTrue(UserInfoValidator.isValidPassword(password));
	}

	@DataProvider(name = "invalidPassword")
	public static Object[][] invalidPassword() {
		return new Object[][] { { "1" }, { "куксар" }, { null } };
	}

	@Test(dataProvider = "invalidPassword")
	public void isValidPasswordNegativeTest(String password) {
		Assert.assertFalse(UserInfoValidator.isValidPassword(password));
	}

	@DataProvider(name = "validName")
	public static Object[][] validName() {
		return new Object[][] { { "Mariya" }, { "Мария Куксар" }, { "Анна-Луиза" } };
	}

	@Test(dataProvider = "validName")
	public void isValidNamePositiveTest(String name) {
		Assert.assertTrue(UserInfoValidator.isValidName(name));
	}

	@DataProvider(name = "invalidName")
	public static Object[][] invalidName() {
		return new Object[][] { { "Mariya123" }, { null }, { "Таня/Аня" } };
	}

	@Test(dataProvider = "invalidName")
	public void isValidNameNegativeTest(String name) {
		Assert.assertFalse(UserInfoValidator.isValidName(name));
	}

	@DataProvider(name = "validPhone")
	public static Object[][] validPhone() {
		return new Object[][] { { "+375298136629" }, { "+375448541269" }, { "+375259874152" } };
	}

	@Test(dataProvider = "validPhone")
	public void isValidPhonePositiveTest(String phone) {
		Assert.assertTrue(UserInfoValidator.isValidPhone(phone));
	}

	@DataProvider(name = "invalidPhone")
	public static Object[][] invalidPhone() {
		return new Object[][] { { "+37529813662" }, { null }, { "375259874152" } };
	}

	@Test(dataProvider = "invalidPhone")
	public void isValidPhoneNegativeTest(String phone) {
		Assert.assertFalse(UserInfoValidator.isValidPhone(phone));
	}
}

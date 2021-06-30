package by.epam.store.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import by.epam.store.model.service.validator.ProductInfoValidator;

public class ProductInfoValidatorTest {

	@DataProvider(name = "validPrice")
	public static Object[][] validPrice() {
		return new Object[][] { { "300" }, { "170.50" }, { "6565656.00" }, { "3" } };
	}

	@Test(dataProvider = "validPrice")
	public void isValidPricePositiveTest(String price) {
		Assert.assertTrue(ProductInfoValidator.isValidPrice(price));
	}

	@DataProvider(name = "invalidPrice")
	public static Object[][] invalidPrice() {
		return new Object[][] { { "0" }, { "hg" }, { "6565656." }, { "1245778785454777454" } };
	}

	@Test(dataProvider = "invalidPrice")
	public void isValidPriceNegativeTest(String price) {
		Assert.assertFalse(ProductInfoValidator.isValidPrice(price));
	}

	@DataProvider(name = "validImageName")
	public static Object[][] validImageName() {
		return new Object[][] { { "sddsad.jpg" }, { "155.jpg" }, { "fg14sgs5s.jpg" } };
	}

	@Test(dataProvider = "validImageName")
	public void isValidImageNamePositiveTest(String imageName) {
		Assert.assertTrue(ProductInfoValidator.isValidImageName(imageName));
	}

	@DataProvider(name = "invalidImageName")
	public static Object[][] inalidImageName() {
		return new Object[][] { { "dgrgg" }, { null }, { "fgfgfg.exe" } };
	}

	@Test(dataProvider = "invalidImageName")
	public void isValidImageNameNegativeTest(String imageName) {
		Assert.assertFalse(ProductInfoValidator.isValidImageName(imageName));
	}

	@DataProvider(name = "validName")
	public static Object[][] validName() {
		return new Object[][] { { "Desk BLACK" }, { "Стол Натуральный" }, { "Cupboard <MONTESSORI>" } };
	}

	@Test(dataProvider = "validName")
	public void isValidNamePositiveTest(String name) {
		Assert.assertTrue(ProductInfoValidator.isValidName(name));
	}

	@DataProvider(name = "invalidName")
	public static Object[][] invalidName() {
		return new Object[][] { { "" }, { null }, { "dfdgfgfgtfhg htfgyfh fghgfhrshr tyrdstytryyh  ghthtrhthth" } };
	}

	@Test(dataProvider = "invalidName")
	public void isValidNameNegativeTest(String name) {
		Assert.assertFalse(ProductInfoValidator.isValidName(name));
	}

	@DataProvider(name = "validQuantity")
	public static Object[][] validQuantity() {
		return new Object[][] { { "1" }, { "0" }, { "99" } };
	}

	@Test(dataProvider = "validQuantity")
	public void isValidQuantityPositiveTest(String quantity) {
		Assert.assertTrue(ProductInfoValidator.isValidQuantity(quantity));
	}

	@DataProvider(name = "invalidQuantity")
	public static Object[][] invalidQuantity() {
		return new Object[][] { { null }, { "f" }, { "257527" } };
	}

	@Test(dataProvider = "invalidQuantity")
	public void isValidQuantityNegativeTest(String quantity) {
		Assert.assertFalse(ProductInfoValidator.isValidQuantity(quantity));
	}
}

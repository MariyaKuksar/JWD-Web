package by.epam.store.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ProductInfoValidatorTest {

	@DataProvider(name = "validPrice")
	public static Object[][] validPrice() {
		return new Object[][] { { "300" }, { "170.50" }, { "6565656.00" }, { "3" } };
	}

	@Test(dataProvider = "validPrice")
	public void isValidPriceTest01(String price) {
		Assert.assertTrue(ProductInfoValidator.isValidPrice(price));
	}
	
	@DataProvider(name = "invalidPrice")
	public static Object[][] invalidPrice() {
		return new Object[][] { { "0" }, { "hg" }, { "6565656." }, { "1245778785454777454" } };
	}

	@Test(dataProvider = "invalidPrice")
	public void isValidPriceTest02(String price) {
		Assert.assertFalse(ProductInfoValidator.isValidPrice(price));
	}
}

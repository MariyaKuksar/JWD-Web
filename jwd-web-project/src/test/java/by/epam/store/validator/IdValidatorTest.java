package by.epam.store.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class IdValidatorTest {

	@DataProvider(name = "validId")
	public static Object[][] validId() {
		return new Object[][] { { "25" }, { "35289" }, { "123456987" }, { "08" } };
	}

	@Test(dataProvider = "validId")
	public void isValidIdTest01(String id) {
		Assert.assertTrue(IdValidator.isValidId(id));
	}

	@DataProvider(name = "invalidId")
	public static Object[][] invalidId() {
		return new Object[][] { { null }, { "jpp" }, { "1234569875456565654545466" }, { "35h" } };
	}

	@Test(dataProvider = "invalidId")
	public void isValidIdTest02(String id) {
		Assert.assertFalse(IdValidator.isValidId(id));
	}
}

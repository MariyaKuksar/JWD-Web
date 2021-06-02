package by.epam.store.model.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.store.entity.ProductCategory;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductCategoryDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.SupplyDao;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;

public class CatalogServiceImplTest {
	@Mock
	private ProductCategoryDao productCategoryDao;
	@Mock
	private ProductDao productDao;
	@Mock
	private SupplyDao supplyDao;
	private AutoCloseable autoCloseable;
	private Map<String, String> productInfo;

	@InjectMocks
	CatalogServiceImpl catalogService;

	@BeforeClass
	public void setUp() {
		productInfo = new HashMap<>();
		productInfo.put("productId", "1");
		productInfo.put("price", "100");
		productInfo.put("productName", "Desk");
		autoCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterClass
	public void tierDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	public void changeProductDataTest01() throws DaoException, ServiceException, InvalidDataException {
		Mockito.when(productDao.update(Mockito.any())).thenReturn(true);
		Assert.assertTrue(catalogService.changeProductData(productInfo));
	}

	@Test
	public void changeProductDataTest02() throws DaoException, ServiceException, InvalidDataException {
		Mockito.when(productDao.update(Mockito.any())).thenReturn(false);
		Assert.assertFalse(catalogService.changeProductData(productInfo));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void changeProductDataTest03() throws DaoException, ServiceException, InvalidDataException {
		Mockito.when(productDao.update(Mockito.any())).thenThrow(DaoException.class);
		Assert.assertFalse(catalogService.changeProductData(productInfo));
	}

	@Test(expectedExceptions = InvalidDataException.class)
	public void changeProductDataTest04() throws DaoException, ServiceException, InvalidDataException {
		Assert.assertFalse(catalogService.changeProductData(Collections.emptyMap()));
	}

	@Test
	public void takeAllProductCategoriesTest01() throws DaoException, ServiceException {
		List<ProductCategory> expected = new ArrayList<>();
		expected.add(new ProductCategory(1L, "Desk"));
		Mockito.when(productCategoryDao.findAll()).thenReturn(expected);
		List<ProductCategory> actual = catalogService.takeAllProductCategories();
		Assert.assertEquals(actual, expected);
	}
}

package by.epam.store.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.store.entity.Product;
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
	private Product product;

	@InjectMocks
	CatalogServiceImpl catalogService;

	@BeforeClass
	public void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		productInfo = new HashMap<>();
		productInfo.put("productId", "1");
		productInfo.put("price", "320");
		productInfo.put("productName", "Desk TRIO");
		product = new Product();
		product.setProductId(1L);
		product.setCategoryId(1L);
		product.setProductName("Desk TRIO");
		product.setImageName("desk.jpg");
		product.setPrice(new BigDecimal(320));
		product.setQuantity(10);
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

	@Test(expectedExceptions = ServiceException.class)
	public void takeAllProductCategoriesTest02() throws DaoException, ServiceException {
		Mockito.when(productCategoryDao.findAll()).thenThrow(DaoException.class);
		catalogService.takeAllProductCategories();
	}

	@Test
	public void takeProductByIdTest01() throws DaoException, ServiceException {
		Optional<Product> expected = Optional.of(product);
		Mockito.when(productDao.findProductById(Mockito.anyString())).thenReturn(expected);
		Optional<Product> actual = catalogService.takeProductById("1");
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void takeProductByIdTest02() throws DaoException, ServiceException {
		Optional<Product> expected = Optional.empty();
		Mockito.when(productDao.findProductById(Mockito.anyString())).thenReturn(expected);
		Optional<Product> actual = catalogService.takeProductById("123568");
		Assert.assertEquals(actual, expected);
	}
	
	@Test
	public void takeProductByIdTest03() throws DaoException, ServiceException {
		Optional<Product> expected = Optional.empty();
		Optional<Product> actual = catalogService.takeProductById("f");
		Assert.assertEquals(actual, expected);
	}
	
	@Test (expectedExceptions = ServiceException.class)
	public void takeProductByIdTest04() throws DaoException, ServiceException {
		Mockito.when(productDao.findProductById(Mockito.anyString())).thenThrow(DaoException.class);
		catalogService.takeProductById("1");
	}
}

package by.epam.store.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.entity.Product;
import by.epam.store.entity.ProductCategory;
import by.epam.store.entity.ProductList;
import by.epam.store.entity.Supply;
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
	private Map<String, String> productInfo;
	private Product product;
	private AutoCloseable autoCloseable;
	@InjectMocks
	private CatalogServiceImpl catalogService;

	@BeforeClass
	public void setUp() {
		productInfo = new HashMap<>();
		productInfo.put(ParameterAndAttribute.PRODUCT_ID, "1");
		productInfo.put(ParameterAndAttribute.CATEGORY_ID, "1");
		productInfo.put(ParameterAndAttribute.PRICE, "320");
		productInfo.put(ParameterAndAttribute.PRODUCT_NAME, "Desk TRIO");
		productInfo.put(ParameterAndAttribute.IMAGE_NAME, "desk.jpg");
		product = new Product();
		product.setProductId(1L);
		product.setCategoryId(1L);
		product.setProductName("Desk TRIO");
		product.setImageName("desk.jpg");
		product.setPrice(new BigDecimal(320));
		product.setQuantity(10);
	}

	@BeforeMethod
	public void init() {
		autoCloseable = MockitoAnnotations.openMocks(this);
	}

	@AfterMethod
	public void tierDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	public void addProductPositiveTest() throws DaoException, ServiceException, InvalidDataException {
		doNothing().when(productDao).create(isA(Product.class));
		catalogService.addProduct(productInfo);
		verify(productDao, times(1)).create(isA(Product.class));
	}

	@Test(expectedExceptions = InvalidDataException.class)
	public void addProductInvalidDataExceptionTest() throws ServiceException, InvalidDataException {
		catalogService.addProduct(Collections.emptyMap());
	}

	@Test(expectedExceptions = ServiceException.class)
	public void addProductServiceExceptionTest() throws DaoException, ServiceException, InvalidDataException {
		doThrow(DaoException.class).when(productDao).create(isA(Product.class));
		catalogService.addProduct(productInfo);
	}

	@Test
	public void acceptProductsPositiveTest() throws DaoException, ServiceException, InvalidDataException {
		Map<Product, Integer> suppliedProducts = new HashMap<>();
		suppliedProducts.put(product, 1);
		doNothing().when(supplyDao).create(isA(Supply.class));
		doNothing().when(supplyDao).createSupplyProductConnection(isA(Supply.class));
		doNothing().when(productDao).increaseQuantity(anyMap());
		Assert.assertTrue(catalogService.acceptProducts(suppliedProducts));
	}

	@Test
	public void acceptProductsNegativeTest() throws ServiceException {
		Assert.assertFalse(catalogService.acceptProducts(Collections.emptyMap()));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void acceptProductsServiceExceptionTest() throws DaoException, ServiceException {
		Map<Product, Integer> suppliedProducts = new HashMap<>();
		suppliedProducts.put(product, 1);
		doThrow(DaoException.class).when(supplyDao).create(isA(Supply.class));
		catalogService.acceptProducts(suppliedProducts);
	}

	@Test
	public void changeProductDataPositiveTest() throws DaoException, ServiceException, InvalidDataException {
		when(productDao.update(isA(Product.class))).thenReturn(true);
		Assert.assertTrue(catalogService.changeProductData(productInfo));
	}

	@Test
	public void changeProductDataNegativeTest() throws DaoException, ServiceException, InvalidDataException {
		when(productDao.update(isA(Product.class))).thenReturn(false);
		Assert.assertFalse(catalogService.changeProductData(productInfo));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void changeProductDataServiceExceptionTest() throws DaoException, ServiceException, InvalidDataException {
		when(productDao.update(isA(Product.class))).thenThrow(DaoException.class);
		Assert.assertFalse(catalogService.changeProductData(productInfo));
	}

	@Test(expectedExceptions = InvalidDataException.class)
	public void changeProductDataInvalidDataExceptionTest()
			throws DaoException, ServiceException, InvalidDataException {
		Assert.assertFalse(catalogService.changeProductData(Collections.emptyMap()));
	}

	@Test
	public void takeAllProductCategoriesPositiveTest() throws DaoException, ServiceException {
		List<ProductCategory> expected = new ArrayList<>();
		expected.add(new ProductCategory(1L, "Desk"));
		when(productCategoryDao.findAll()).thenReturn(expected);
		List<ProductCategory> actual = catalogService.takeAllProductCategories();
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeAllProductCategoriesNegativeTest() throws DaoException, ServiceException {
		List<ProductCategory> expected = Collections.emptyList();
		when(productCategoryDao.findAll()).thenReturn(expected);
		List<ProductCategory> actual = catalogService.takeAllProductCategories();
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeAllProductCategoriesServiceExceptionTest() throws DaoException, ServiceException {
		when(productCategoryDao.findAll()).thenThrow(DaoException.class);
		catalogService.takeAllProductCategories();
	}

	@Test
	public void takeProductByIdPositiveTest() throws DaoException, ServiceException {
		Optional<Product> expected = Optional.of(product);
		when(productDao.findProductById(anyString())).thenReturn(expected);
		Optional<Product> actual = catalogService.takeProductById("1");
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeProductByIdNegativeTest() throws ServiceException {
		Optional<Product> expected = Optional.empty();
		Optional<Product> actual = catalogService.takeProductById("f");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeProductByIdServiceExceptionTest() throws DaoException, ServiceException {
		when(productDao.findProductById(anyString())).thenThrow(DaoException.class);
		catalogService.takeProductById("1");
	}

	@Test
	public void takeProductsFromCategoryPositiveTest() throws DaoException, ServiceException {
		List<Product> expected = new ArrayList<>();
		expected.add(product);
		when(productDao.findProductsByCategoryId(anyString())).thenReturn(expected);
		List<Product> actual = catalogService.takeProductsFromCategory("1", null);
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeProductsFromCategoryNegativeTest() throws ServiceException {
		List<Product> expected = Collections.emptyList();
		List<Product> actual = catalogService.takeProductsFromCategory("f", null);
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeProductsFromCategoryServiceExceptionTest() throws DaoException, ServiceException {
		when(productDao.findProductsByCategoryId(anyString())).thenThrow(DaoException.class);
		catalogService.takeProductsFromCategory("1", null);
	}

	@Test
	public void takeProductsWithNamePositiveTest() throws DaoException, ServiceException {
		List<Product> expected = new ArrayList<>();
		expected.add(product);
		when(productDao.findProductsByName(anyString())).thenReturn(expected);
		List<Product> actual = catalogService.takeProductsWithName("desk", null);
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeProductsWithNameNegativeTest() throws ServiceException {
		List<Product> expected = Collections.emptyList();
		List<Product> actual = catalogService.takeProductsWithName(null, null);
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeProductsWithNameServiceExceptionTest() throws DaoException, ServiceException {
		when(productDao.findProductsByName(anyString())).thenThrow(DaoException.class);
		catalogService.takeProductsWithName("desk", null);
	}

	@Test
	public void takeProductsInStockPositiveTest() throws DaoException, ServiceException {
		ProductList expected = new ProductList();
		expected.setProducts(Arrays.asList(product));
		when(productDao.findProductsInStock(anyInt(), anyInt())).thenReturn(expected);
		ProductList actual = catalogService.takeProductsInStock("1");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeProductsInStockServiceExceptionTest() throws DaoException, ServiceException {
		when(productDao.findProductsInStock(anyInt(), anyInt())).thenThrow(DaoException.class);
		catalogService.takeProductsInStock("1");
	}

	@Test
	public void takeProductsOnOrderPositiveTest() throws DaoException, ServiceException {
		ProductList expected = new ProductList();
		expected.setProducts(Arrays.asList(product));
		when(productDao.findProductsOnOrder(anyInt(), anyInt())).thenReturn(expected);
		ProductList actual = catalogService.takeProductsOnOrder("1");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeProductsOnOrderServiceExceptionTest() throws DaoException, ServiceException {
		when(productDao.findProductsOnOrder(anyInt(), anyInt())).thenThrow(DaoException.class);
		catalogService.takeProductsOnOrder("1");
	}
}

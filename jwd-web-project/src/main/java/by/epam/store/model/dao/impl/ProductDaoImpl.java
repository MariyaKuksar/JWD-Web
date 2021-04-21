package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.builder.ProductBuilder;
import by.epam.store.model.entity.builder.UserBuilder;

public class ProductDaoImpl implements ProductDao {
	private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, AMOUNT, IS_PRODUCABLE FROM PRODUCTS WHERE CATEGORY_ID=?";
	private static final String SQL_SELECT_PRODUCTS_BY_NAME = "SELECT ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, AMOUNT, IS_PRODUCABLE FROM PRODUCTS WHERE NAME LIKE ?";
	private static final String PERCENT = "%";
	
	@Override
	public List<Product> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long create(Product entity) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Product entity) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> findProductByCategoryId(Long id) throws DaoException {
		List<Product> products;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_CATEGORY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			products = ProductBuilder.getInstance().build(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}

	@Override
	public List<Product> findProductsByName(String productName) throws DaoException {
		List<Product> products;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_NAME)) {
			statement.setString(1, PERCENT + productName + PERCENT);
			ResultSet resultSet = statement.executeQuery();
			products = ProductBuilder.getInstance().build(resultSet);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}
}

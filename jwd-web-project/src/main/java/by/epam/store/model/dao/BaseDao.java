package by.epam.store.model.dao;

import java.util.List;

public interface BaseDao<T> {
	List<T> findAll() throws DaoException;

	long create(T entity) throws DaoException;

	boolean update(T entity) throws DaoException;
}

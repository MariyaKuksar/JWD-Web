package by.epam.store.model.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
	List<T> findAll() throws DaoException;

	long create(T entity) throws DaoException;

	boolean update(T entity) throws DaoException;
}

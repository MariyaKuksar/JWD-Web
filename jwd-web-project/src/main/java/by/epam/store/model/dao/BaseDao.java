package by.epam.store.model.dao;

import java.util.List;

public interface BaseDao<T> {

	void create(T entity) throws DaoException;

	boolean update(T entity) throws DaoException;
	
	List<T> findAll() throws DaoException;
}

package by.epam.store.model.dao;

import java.util.List;
//TODO подумать нужно ли оно мне
public interface BaseDao<T> {
	
	List<T> findAll() throws DaoException;

	void create(T entity) throws DaoException;

	boolean update(T entity) throws DaoException;
}

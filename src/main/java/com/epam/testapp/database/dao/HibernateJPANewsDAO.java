package com.epam.testapp.database.dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.epam.testapp.ArrayConverter;
import com.epam.testapp.model.News;

/**
 * NewsDAO --- implementation of INewsDAO interface
 * 
 * @author Marharyta_Amelyanchuk
 */
@Transactional
public final class HibernateJPANewsDAO implements INewsDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Gets all the news from database
	 * 
	 * @return ArrayList of News
	 * @exception DAOException
	 *                if connection can't be taken or if some problems occur
	 *                with dao
	 */
	@Override
	public ArrayList<News> getList() throws DAOException {
		ArrayList<News> list = (ArrayList<News>) entityManager
				.createNamedQuery("getAllOrderByDateAndId", News.class)
				.getResultList();
		return list;
	}

	/**
	 * Insert new news or update existing in database, select its id from
	 * database and sets it to this news
	 * 
	 * @param object
	 *            The News object to insert in database
	 * @return News
	 * @exception DAOException
	 *                if connection can't be taken or if some problems occur
	 *                with dao
	 */
	@Override
	public News save(News object) throws DAOException {
		object = entityManager.merge(object);
		return object;
	}

	/**
	 * Delete in database array of news by their id numbers
	 * 
	 * @param ids
	 *            Array of news id numbers to delete in database
	 * @return void
	 * @exception DAOException
	 *                if connection can't be taken, if some problems occur with
	 *                dao
	 */
	@Override
	public void remove(Integer[] ids) throws DAOException {
		Query query = entityManager.createNamedQuery("deleteByArrayOfId");
		query.setParameter("ids", ArrayConverter.convertIntArrayToList(ids));
		query.executeUpdate();
	}

	/**
	 * Select news by id from database and stores his data in News object
	 * 
	 * @param id
	 *            The id of news to get from database
	 * @return News
	 * @exception DAOException
	 *                if connection can't be taken, if some problems occur with
	 *                dao
	 */
	@Override
	public News fetchById(int id) throws DAOException {
		News object = entityManager.find(News.class, id);
		return object;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}

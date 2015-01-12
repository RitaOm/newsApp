package com.epam.testapp.database.dao;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.epam.testapp.model.News;

/**
 * NewsDAO --- implementation of INewsDAO interface
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class HibernateXMLNewsDAO implements INewsDAO {

	private SessionFactory sessionFactory;

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
		final ArrayList<News> list = new ArrayList<>();
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.getNamedQuery("getAllOrderByDateAndId");
			for (final Object o : query.list()) {
				list.add((News) o);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e);
		}
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
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(object);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e);
		}
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
		Transaction tx = null;
		try {			
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.getNamedQuery("deleteByArrayOfId");
			query.setParameterList("ids", ids);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e);
		}
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
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			News news = (News) session.get(News.class, id);
			tx.commit();
			return news;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new DAOException(e);
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}

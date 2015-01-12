package com.epam.testapp.database.dao;

import java.util.ArrayList;
import com.epam.testapp.model.News;

/**
 * INewsDAO --- interface which defines methods for access to data stored in
 * database
 * 
 * @author Marharyta_Amelyanchuk
 */
public interface INewsDAO {

	/**
	 * Gets all the news from database
	 * 
	 * @return ArrayList of News
	 * @exception DAOException if connection can't be taken or if some
	 *                problems occur with dao
	 */
	public ArrayList<News> getList() throws DAOException;

	/**
	 * Insert new news or update existing in database
	 * 
	 * @param object
	 *            The News object to insert in database
	 * @return News
	 * @exception DAOException if connection can't be taken or if some
	 *                problems occur with dao
	 */
	public News save(News object) throws DAOException;

	/**
	 * Delete in database array of news by their id numbers
	 * 
	 * @param ids
	 *            Array of news id numbers to delete in database
	 * @return void
	 * @exception DAOException if connection can't be taken, if some
	 *                problems occur with dao
	 */
	public void remove(Integer[] id) throws DAOException;

	/**
	 * Select news by id from database and stores his data in News object
	 * 
	 * @param id
	 *            The id of news to get from database
	 * @return News
	 * @exception DAOException if connection can't be taken, if some
	 *                problems occur with dao
	 */
	public News fetchById(int id) throws DAOException;

}

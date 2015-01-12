package com.epam.testapp.database.conn_pool;

import java.sql.Connection;

/**
 * ConnectionPool --- interface which defines methods for getting connection to
 * database, release it and close all opened connection (method shutdown)
 * 
 * @author Marharyta_Amelyanchuk
 */
public interface ConnectionPool {
	/**
	 * Gets connection to database
	 * 
	 * @return Connection which must be released after using
	 * @exception ConnectionPoolException
	 *                if connection can't be taken
	 */
	public Connection getConnection() throws ConnectionPoolException;

	/**
	 * Releases connection
	 * 
	 * @param connection
	 *            Object of class Connection which will be released
	 * @return void
	 */
	public void release(Connection connection);

	/**
	 * Closes all opened connections in this instance of connection pool
	 * 
	 * @return void
	 */
	public void shutdown();

}
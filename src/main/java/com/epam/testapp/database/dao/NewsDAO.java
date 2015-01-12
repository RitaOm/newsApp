package com.epam.testapp.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.testapp.ArrayConverter;
import com.epam.testapp.database.conn_pool.ConnectionPool;
import com.epam.testapp.database.conn_pool.ConnectionPoolException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.DateConverterCaller;
import com.epam.testapp.util.LocaleLang;

/**
 * NewsDAO --- implementation of INewsDAO interface
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class NewsDAO implements INewsDAO {
	/** Connection pool instance to get connections */
	private ConnectionPool connectionPool;
	/** constants which contain sql queries */
	private final String SELECT_ALL_QUERY = "SELECT * FROM NEWS ORDER BY DAY DESC, ID DESC";
	private final String SELECT_BY_ID_QUERY = "SELECT * FROM NEWS WHERE id = ?";
	private final String SELECT_PARAMS_QUERY = "SELECT ID FROM NEWS WHERE TITLE=? AND DAY = TO_DATE(?,'MM/DD/YYYY') AND BRIEF = ? AND CONTENT = ?";
	private final String CREATE_QUERY = "INSERT INTO NEWS VALUES(?, TO_DATE(?,'MM/DD/YYYY'), ?, ?, news_id_seq.nextval)";
	private final String UPDATE_QUERY = "UPDATE NEWS SET TITLE = ?, DAY = TO_DATE(?,'MM/DD/YYYY'), BRIEF = ?, CONTENT = ? WHERE ID = ?";
	private final String DELETE_QUERY = "DELETE FROM NEWS WHERE ID IN ";

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
		Connection connection = getConnectionFromPool();
		ArrayList<News> list = new ArrayList<News>();
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);
			list = parseResultSet(rs);
		} catch (SQLException e) {
			throw new DAOException("Can't get all news from table: "
					+ e.getMessage());
		} finally {
			connectionPool.release(connection);
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
		Connection connection = getConnectionFromPool();
		if (object.getId() == 0) {
			try (PreparedStatement createStatement = connection
					.prepareStatement(CREATE_QUERY);
					PreparedStatement getIdStatement = connection
							.prepareStatement(SELECT_PARAMS_QUERY)) {
				setValuesForPrepareStatement(createStatement, object);
				createStatement.executeUpdate();
				setValuesForPrepareStatement(getIdStatement, object);
				ResultSet rs = getIdStatement.executeQuery();
				rs.next();
				int id = rs.getInt("id");
				object.setId(id);
			} catch (SQLException e) {
				throw new DAOException("Can't create a new record: "
						+ e.getMessage());
			} finally {
				connectionPool.release(connection);
			}

		} else {
			try (PreparedStatement statement = connection
					.prepareStatement(UPDATE_QUERY)) {
				setValuesForPrepareStatement(statement, object);
				statement.setInt(5, object.getId());
				int count = statement.executeUpdate();
				if (count != 1) {
					throw new DAOException("Update " + count + " news, id "
							+ object.getId());
				}
			} catch (SQLException e) {
				throw new DAOException("Can't update news with id "
						+ object.getId() + ": " + e.getMessage());
			} finally {
				connectionPool.release(connection);
			}
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
		Connection connection = getConnectionFromPool();
		String idString = ArrayConverter.convertIntArrayToString(ids);
		try (Statement statement = connection.createStatement()) {
			statement.execute(DELETE_QUERY + "(" + idString + ")");
		} catch (SQLException e) {
			throw new DAOException("Can't delete by id news " + idString + ". "
					+ e.getMessage());
		} finally {
			connectionPool.release(connection);
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
		Connection connection = getConnectionFromPool();
		List<News> list;
		try (PreparedStatement statement = connection
				.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			list = parseResultSet(rs);
		} catch (SQLException e) {
			throw new DAOException("Can't choose new from DB by id " + id
					+ ": " + e.getMessage());
		} finally {
			connectionPool.release(connection);
		}
		int n = 0;
		if (list == null || list.size() == 0 || (n = list.size()) > 1) {
			throw new DAOException("There are " + n + " news for id " + id);
		}
		return list.iterator().next();
	}

	/**
	 * private method Parse ResultSet: create ArrayList of news and adds there
	 * parsed news
	 * 
	 * @param rs
	 *            ResultSet to be parsed
	 * @return ArrayList of News
	 * @exception DAOException
	 *                if some problems occur with dao
	 */
	private ArrayList<News> parseResultSet(ResultSet rs) throws DAOException {
		ArrayList<News> result = new ArrayList<News>();
		try {
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt(5));
				news.setTitle(rs.getString(1));
				news.setDate(rs.getDate(2));
				news.setBrief(rs.getString(3));
				news.setContent(rs.getString(4));
				result.add(news);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
		return result;
	}

	/**
	 * private method Set values for prepare statement retrieving their from
	 * News object
	 * 
	 * @param statement
	 *            PreparedStatement to set values
	 * @param object
	 *            News object to retrieve values
	 * @return ArrayList of News
	 * @exception DAOException
	 *                if some problems occur with dao
	 */
	private void setValuesForPrepareStatement(PreparedStatement statement,
			News object) throws DAOException {
		try {
			statement.setString(1, object.getTitle());
			String date = DateConverterCaller.dateToString(object.getDate(),
					LocaleLang.EN_RU);
			statement.setString(2, date);
			statement.setString(3, object.getBrief());
			statement.setString(4, object.getContent());

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * private method Gets a connection instance from the connection pool
	 * 
	 * @return a Connection
	 * @exception DAOException
	 *                if some problems occur with dao
	 */
	private Connection getConnectionFromPool() throws DAOException {
		Connection connection;
		try {
			connection = connectionPool.getConnection();
		} catch (ConnectionPoolException e) {
			throw new DAOException(e.getMessage());
		}
		return connection;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}
}

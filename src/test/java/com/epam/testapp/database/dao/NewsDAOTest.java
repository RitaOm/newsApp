package com.epam.testapp.database.dao;

import java.sql.Connection;
import java.sql.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.testapp.model.News;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FullXmlDataFileLoader;

public class NewsDAOTest {
	private static ApplicationContext context;
	private static NewsDAO dao;
	private static IDataSet xmlDataSet;
	private static News news;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(
				"testContext.xml");
		dao = (NewsDAO) context.getBean("dao");
		FullXmlDataFileLoader loader = new FullXmlDataFileLoader();
		xmlDataSet = loader.load("/testNewsDataSet.xml");		
	}
	
	private void fill() throws Exception {
		Connection connect = null;
		try {
			connect = dao.getConnectionPool().getConnection();
			IDatabaseConnection dbUnitConnect = new DatabaseConnection(connect);
			DatabaseOperation.CLEAN_INSERT.execute(dbUnitConnect, xmlDataSet);
		} finally {
			dao.getConnectionPool().release(connect);
		}
	}

	private void clean() throws Exception {
		Connection connect = null;
		try {
			connect = dao.getConnectionPool().getConnection();
			IDatabaseConnection dbUnitConnect = new DatabaseConnection(connect);
			DatabaseOperation.DELETE_ALL.execute(dbUnitConnect, xmlDataSet);
		} finally {
			dao.getConnectionPool().release(connect);
		}
	}

	@Test
	public void fetchById() throws Exception {
		fill();
		News news = dao.fetchById(2);
		Assert.assertNotNull(news);
		news = dao.fetchById(3);
		Assert.assertNotNull(news);
		news = dao.fetchById(4);
		Assert.assertNotNull(news);
	}

	@Test
	public void insertSave() throws Exception {
		clean();
		news = new News("title", new Date(System.currentTimeMillis()), "brief",
				"content");
		News insertedNews = dao.save(news);
		Assert.assertTrue(insertedNews.getId() > 0);
		Assert.assertEquals("title", insertedNews.getTitle());
		Assert.assertEquals("brief", insertedNews.getBrief());
		Assert.assertEquals("content", insertedNews.getContent());
		String date = new Date(System.currentTimeMillis()).toString();
		Assert.assertEquals(date, insertedNews.getDate().toString());
	}

	@Test
	public void updateSave() throws Exception {
		clean();
		news = new News("title", new Date(System.currentTimeMillis()), "brief",
				"content");
		News insertedNews = dao.save(news);
		insertedNews.setTitle("newTitle");
		insertedNews.setBrief("newBrief");
		insertedNews.setContent("newContent");
		Date date = Date.valueOf("2014-10-12");
		insertedNews.setDate(date);
		dao.save(insertedNews);
		News updatedNews = dao.fetchById(news.getId());
		Assert.assertEquals("newTitle", updatedNews.getTitle());
		Assert.assertEquals("newBrief", updatedNews.getBrief());
		Assert.assertEquals("newContent", updatedNews.getContent());
		Assert.assertEquals(date.toString(), updatedNews.getDate().toString());
	}

	@Test
	public void getAll() throws Exception {
		fill();
		int size = dao.getList().size();
		Assert.assertSame(3, size);
	}

	@Test
	public void delete() throws Exception {
		fill();
		Integer[] ids = { 2, 3, 4 };
		dao.remove(ids);
		int size = dao.getList().size();
		Assert.assertSame(0, size);
	}

	public NewsDAO getDao() {
		return dao;
	}

	public void setDao(NewsDAO dao) {
		NewsDAOTest.dao = dao;
	}

}

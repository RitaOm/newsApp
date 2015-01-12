package com.epam.testapp.presentation.form;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.struts.validator.ValidatorActionForm;

import com.epam.testapp.model.News;
import com.epam.testapp.util.DateConverterCaller;
import com.epam.testapp.util.LocaleLang;

/**
 * NewsForm --- a JavaBean class with properties and their setters and getters
 * methods helping association between NewsAction class methods and jsp pages.
 * It extends ValidatorActionForm so that form fields can be validated with
 * Struts validation framework.
 * 
 * @author Marharyta_Amelyanchuk
 */
public class NewsForm extends ValidatorActionForm {

	private static final long serialVersionUID = 2720974016223644827L;
	/** News model */
	private News news;
	/** list of News entities */
	private ArrayList<News> newsList;
	/** String array of news id numbers which had been checked in html form */
	private Integer[] ids;
	

	/**
	 * Constructor: Initializes news  field and sets for news field
	 * date current date, if it is null.
	 */
	public NewsForm() {
		news = new News();
		if (news.getDate() == null) {
			news.setDate(new Date(System.currentTimeMillis()));
		}
	}

	public ArrayList<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<News> newsList) {
		this.newsList = newsList;
	}

	public int getId() {
		return news.getId();
	}

	public String getTitle() {
		return news.getTitle();
	}

	public void setTitle(String title) {
		news.setTitle(title);
	}

	/**
	 * Getter for news field date: Converts java.sql.Date to String 
	 * 
	 * @return Converted to String java.sql.Date
	 */
	public String getDate() {
		Date sqlDate = news.getDate();
		String stringDate = DateConverterCaller.dateToString(sqlDate, LocaleLang.EN_RU);
		return stringDate;
	}

	/**
	 * Setter for news field date: Converts String date to java.sql.Date and
	 * sets it to news field date
	 * 
	 * @param String
	 *            date value
	 * @return void
	 */
	public void setDate(String date) {
		Date sqlDate = DateConverterCaller.stringToDate(date, LocaleLang.EN_RU);
		news.setDate(sqlDate);
	}

	public String getBrief() {
		return news.getBrief();
	}

	public void setBrief(String brief) {
		news.setBrief(brief);
	}

	public String getContent() {
		return news.getContent();
	}

	public void setContent(String content) {
		news.setContent(content);
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	
	
}
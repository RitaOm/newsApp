package com.epam.testapp.presentation.action;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.epam.testapp.database.dao.DAOException;
import com.epam.testapp.database.dao.INewsDAO;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;

/**
 * NewsAction --- a class which accepts incoming HTTP requests and call
 * appropriate method to process these requests
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class NewsAction extends MappingDispatchAction {

	private static final Logger LOG = Logger.getLogger(NewsAction.class);
	private INewsDAO dao;

	/**
	 * Gets list of all news by dao object and sets it to newsForm field
	 * newsList
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: failure if
	 *         DAOException/ConnectionPoolException occurs, success if there is
	 *         no exceptions
	 * @exception Exception
	 *                if an exception occurs
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList<News> listNews;
		try {
			listNews = dao.getList();
		} catch (DAOException e) {
			LOG.error(e.getMessage());
			return mapping.findForward(ActionConstants.FAILURE.toString());
		}
		NewsForm newsForm = (NewsForm) form;
		newsForm.setNewsList(listNews);
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Gets by dao object news by id (request parameter) and sets it to newsForm
	 * field news
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: failure if
	 *         DAOException/ConnectionPoolException occurs or request parameter
	 *         id is undefined, success in other case
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsForm newsForm = (NewsForm) form;
		int id = newsForm.getNews().getId();
		if (id == 0) {
			LOG.error("Undefined news id");
			return mapping.findForward(ActionConstants.UNDEFINED.toString());
		}
		String cancelForward = request
				.getParameter(ActionConstants.CANCEL_FORWARD.toString());
		if (cancelForward == null) {
			cancelForward = "";
		}
		request.getSession().setAttribute(
				ActionConstants.CANCEL_FORWARD.toString(), cancelForward);
		News news;
		try {
			news = dao.fetchById(id);
		} catch (DAOException e) {
			LOG.error(e.getMessage());
			return mapping.findForward(ActionConstants.FAILURE.toString());
		}
		newsForm.setNews(news);
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Save by dao object new news if id (request parameter) undefined, or
	 * updates existing news in other case and sets it to class field news
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: failure if
	 *         DAOException/ConnectionPoolException occurs, success if there is
	 *         no exceptions
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsForm newsForm = (NewsForm) form;
		News news = newsForm.getNews();
		int id = news.getId();
		try {
			if (id != 0) {
				news.setId(id);
			}
			news = dao.save(news);
		} catch (DAOException e) {
			LOG.error(e.getMessage());
			return mapping.findForward(ActionConstants.FAILURE.toString());
		}
		newsForm.setNews(news);
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Deletes by dao object news by request parameter array of ids, gets list
	 * of all news by dao object and sets it to newsForm field newsList
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: failure if
	 *         DAOException/ConnectionPoolException occurs or form parameter
	 *         array of ids is undefined, success in other case
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsForm newsForm = (NewsForm) form;
Integer[] ids = newsForm.getIds();
		if (ids == null || ids.length == 0) {
			LOG.error("Undefined news id for delete");
			return mapping.findForward(ActionConstants.SUCCESS.toString());
		}
		try {
			dao.remove(ids);
		} catch (DAOException e) {
			LOG.error(e.getMessage());
			return mapping.findForward(ActionConstants.FAILURE.toString());
		}
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	/**
	 * Retrieve lang value from the request and sets locale
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The ActionMapping parameter's value: success if request parameter
	 *         lang not null and failure if lang is null
	 */
	public ActionForward locale(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lang = (String) request.getParameter(ActionConstants.LANG
				.toString());
		if (lang == null) {
			return mapping.findForward(ActionConstants.FAILURE.toString());
		}
		Locale locale = new Locale(lang);
		request.getSession().setAttribute(Globals.LOCALE_KEY, locale);
		return mapping.findForward(ActionConstants.SUCCESS.toString());
	}

	public INewsDAO getDao() {
		return dao;
	}

	public void setDao(INewsDAO dao) {
		this.dao = dao;
	}

}

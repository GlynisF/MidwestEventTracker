package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.eventtracker.entity.User;
import com.glynisf.eventtracker.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;

@WebServlet(
		urlPatterns = {"/details"}
)

public class NotebookDisplay extends HttpServlet {
	private final Object obj = new Object();

	private GenericDao<User, Serializable> userDao;
	private GenericDao<Notebook, Serializable> notebookDao;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		userDao = new GenericDao<>(User.class);

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		notebookDao = new GenericDao<>(Notebook.class);

		String title = (String) request.getParameter("title");

		Notebook notebook = new Notebook(0, title, user);

		int notebookId = notebookDao.insert(notebook);
		user.addNotebook(notebook);

		request.setAttribute("formSubmitted", true);
		request.setAttribute("notebook", notebook);
		//Create the url
		String url = "/eventplanner.jsp";    //Forward to jsp page

		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
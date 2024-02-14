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
		urlPatterns = {"/addNotebook"}
)
public class AddNotebook extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GenericDao<Notebook, Serializable> notebookDao = new GenericDao<>(Notebook.class);
		GenericDao<User, Serializable> userDao = new GenericDao<>(User.class);

		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = userDao.getById(userId);
		Notebook notebook = new Notebook(0, request.getParameter("title"), user);
		notebookDao.insert(notebook);
		System.out.println("new notebook: " + notebook);

		int notebookId = notebook.getId();

		request.setAttribute("notebookId", notebookId);
		user.addNotebook(notebook);

		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/planner.jsp");
		dispatcher.forward(request, response);
	}
}
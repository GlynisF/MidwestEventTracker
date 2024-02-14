package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.entity.Notebook;
import com.glynisf.eventtracker.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet (    name= "nav",
		  urlPatterns = {"/eventPlanner"})

public class NavigationServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Set<Notebook> notebooks = user.getNotebooks();
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		session.setAttribute("notebooks", notebooks);

		System.out.println("session user: " + session.getAttribute("user"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/add-notebook.jsp");
		dispatcher.forward(request, response);

	}
}
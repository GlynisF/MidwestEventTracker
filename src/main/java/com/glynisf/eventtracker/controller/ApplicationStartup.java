package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.util.PropertiesLoader;
import com.mysql.cj.log.Log;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet (
		name="applicationStartup",
		urlPatterns = {"/appStartup"},
		loadOnStartup = 1
)

public class ApplicationStartup extends HttpServlet implements PropertiesLoader {
	private Properties properties;


	@Override
	public void init() throws ServletException {
		properties = new Properties(loadProperties("/cognito.properties"));

		ServletContext context = getServletContext();
		context.setAttribute("cognitoProperties", properties);
		LogIn login = new LogIn();
		context.setAttribute("login", login);
		Auth auth = new Auth();
		context.setAttribute("auth", auth);


		log(context.toString());
	}

}
package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.persistence.GenericDao;
import com.glynisf.eventtracker.util.PropertiesLoader;
import com.sun.xml.bind.v2.model.core.ID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.Properties;


@WebServlet (
		name="applicationStartup",
		urlPatterns = {"/appStartup"},
		loadOnStartup = 1
)

public class ApplicationStartup<T> extends HttpServlet implements PropertiesLoader {
	private Properties properties;
	private GenericDao T;

	@Override
	public void init() throws ServletException {
		properties = new Properties(loadProperties("/cognito.properties"));
		ServletContext context = getServletContext();
		GenericDao<T, ID> dao = new GenericDao(Object.class);
		context.setAttribute("cognitoProperties", properties);
	}

}
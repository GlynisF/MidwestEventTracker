package com.glynisf.eventtracker.controller;

import com.glynisf.eventtracker.entity.User;
import com.glynisf.eventtracker.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(
        urlPatterns = {"/userSignUp"}
)


public class UserSignUp extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenericDao gd = new GenericDao(User.class);
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String userName = req.getParameter("user_name");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");
        String email = req.getParameter("email_address");
        LocalDate birthdate = LocalDate.parse(req.getParameter("date_of_birth"));
        User user = new User(firstName, lastName, userName, email, 0, password, birthdate, gender);
        int id = gd.insert(user);
        req.setAttribute("users2", id);
        req.setAttribute("users", gd.getAll());

        logger.info(gender);
        logger.info(email);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/results.jsp");
        dispatcher.forward(req, resp);
    }


}
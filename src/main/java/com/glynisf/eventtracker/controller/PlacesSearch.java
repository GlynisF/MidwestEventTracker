package com.glynisf.eventtracker.controller;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.glynisf.eventtracker.entity.Event;
import com.glynisf.eventtracker.entity.Notebook;
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
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/form"}, asyncSupported = true)


public class PlacesSearch extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Create an AsyncContext to handle the asynchronous processing
        final AsyncContext asyncContext = req.startAsync();

        // Start a new thread for the asynchronous task
        new Thread(() -> {
            try {RequestDispatcher dispatcher = req.getRequestDispatcher("/event-form.jsp");
                // Simulate some asynchronous processing
                Thread.sleep(2000);
                // Get the response writer
                PrintWriter writer = asyncContext.getResponse().getWriter();
                Thread.sleep(2000);
                System.out.println("sleeping...");
                writer.println("Async processing completed!");
                Thread.sleep(2000);;
                System.out.println("One last sleep....");
                dispatcher.forward(req, resp);

                // Complete the asynchronous processing
                asyncContext.complete();
            } catch (InterruptedException | IOException | ServletException e) {
                e.printStackTrace();
            }
        }).start();
    }

    }
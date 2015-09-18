package com.controller;

import com.google.gson.Gson;
import com.model.PersonData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@javax.servlet.annotation.WebServlet(name = "javaAngularJS", urlPatterns = {"/javaAngularJS"})
public class AngularJsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        PersonData personData = new PersonData();
        personData.setFirstName("Kartik");
        personData.setLastName("Singal");

        String json = new Gson().toJson(personData);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

}

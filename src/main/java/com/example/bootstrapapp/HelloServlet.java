package com.example.bootstrapapp;

import java.io.*;
import java.sql.Connection;
import java.util.regex.Pattern;

import com.example.bootstrapapp.entities.User;
import com.example.bootstrapapp.repository.UserRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
    private UserRepository repository;
    private final String ERROR_PAGE = "error.jsp";
    private final String SUCCESS_PAGE = "welcome.jsp";
    private final int PASSWORD_VALID_PAGE = 6;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.repository = new UserRepository();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = new User();
        String login = "2";
        String password = "2";
        String redirectPage = ERROR_PAGE;

        if (isEmailValid(login) && isPasswordValid(password)) {
            user.setLogin(login);
            user.setPassword(password);
            if (repository.authUser(user)) {
                redirectPage = SUCCESS_PAGE;
            }
        }

        request.getRequestDispatcher(redirectPage).forward(request, response);
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.length() >= PASSWORD_VALID_PAGE;
    }

    private boolean isEmailValid(String email) {
        return email != null && !email.equals("") &&
                Pattern.compile("^(.+)@(\\S+)$")
                        .matcher(email)
                        .matches();
    }

}
package com.example.CA3;

import com.example.CA3.model.BolbolestanApplication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BolbolestanApplication.getInstance().setLoggedInStudent("");
        response.sendRedirect("/CA3_war_exploded/login");
    }
}

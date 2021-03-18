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
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BolbolestanApplication app = BolbolestanApplication.getInstance();
        String studentId = request.getParameter("std_id");
        if (app.studentExists(studentId)) {
            app.setLoggedInStudent(studentId);
            response.sendRedirect("/CA3_war_exploded");
        }
        else
            response.sendRedirect("/CA3_war_exploded/login");
    }
}

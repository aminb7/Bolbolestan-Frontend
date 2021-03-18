package com.example.CA3;

import com.example.CA3.model.BolbolestanApplication;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "HomeServlet", value = "/")
public class HomeServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BolbolestanApplication app = BolbolestanApplication.getInstance();
        if (app.getLoggedInStudentId().equals(""))
            response.sendRedirect("/login");
        else
            request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

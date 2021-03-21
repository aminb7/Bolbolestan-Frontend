package com.example.CA3;

import com.example.CA3.model.BolbolestanApplication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PlanServlet", value = "/plan")
public class PlanServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BolbolestanApplication app = BolbolestanApplication.getInstance();
        if (app.getLoggedInStudentId().equals(""))
            response.sendRedirect("/login");
        else
            request.getRequestDispatcher("/plan.jsp").forward(request, response);
    }
}

package com.example.CA3;

import com.example.CA3.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "Courses", value = "/courses")
public class CoursesServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/courses.jsp").forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BolbolestanApplication app = BolbolestanApplication.getInstance();
		String action = request.getParameter("action");

		switch (action) {
			case "remove" -> {
				String courseCode = request.getParameter("course_code");
				app.getLoggedInStudent().getSelectedCourses().remove(courseCode);
			}
			case "add" -> {
				String courseCode = request.getParameter("course_code");
				String classCode = request.getParameter("class_code");
				Map<String, Course> courseGroup = app.getCourses().get(courseCode);

				if (courseGroup == null) {
					break;
				}

				Course course = courseGroup.get(classCode);
				Student student = app.getLoggedInStudent();

				if (course == null || student == null) {
					break;
				}

				boolean hasPreconditions = true;

				for (String code : course.getPrerequisites()) {
					GradedCourse gradedCourse = student.getGradedCourses().get(code);

					if (gradedCourse == null || gradedCourse.getGrade() < 10)
						hasPreconditions = false;
				}

				boolean hasConflict = false;

				for (SelectedCourse selectedCourse : new ArrayList<>(student.getSelectedCourses().values())) {
					if (selectedCourse.getCourse().getClassTime().overlaps(course.getClassTime())
							|| selectedCourse.getCourse().getExamTime().overlaps(course.getExamTime()))
						hasConflict = true;
				}

				if (hasPreconditions && !hasConflict)
					student.addCourse(course);
			}
		}

		response.sendRedirect("/courses");
	}
}

package org.ie.bolbolestan.system;

import io.javalin.Javalin;

import java.io.IOException;

public class WebServerApplication {
	private final int port;
	Javalin app;
	private HelperApplication helperApplication;

	public WebServerApplication(int port, HelperApplication helperApplication) {
		this.port = port;
		this.helperApplication = helperApplication;
	}

	public void fillInformation() {
		helperApplication.fillInformation();
	}

	public void serve() {
		app = Javalin.create().start(port);

		app.get("/courses", helperApplication.new GetCoursesHandler());
		app.get("/profile/*", helperApplication.new GetProfileHandler());
		app.get("/course/*/*", helperApplication.new ViewAddCourseHandler());
		app.post("/course/*/*", helperApplication.new AddCourseHandler());
		app.get("/change_plan/*", helperApplication.new ChangePlanHandler());
		app.post("/change_plan/*", helperApplication.new RemoveCourseHandler());
		app.get("/plan/*", helperApplication.new ViewPlanHandler());
		app.get("/submit/*", helperApplication.new ViewCoursesSubmissionHandler());
		app.post("/submit/*", helperApplication.new CoursesSubmissionHandler());
	}

	public void stop() {
		app.stop();
	}
}

package org.ie.bolbolestan.system;

import io.javalin.Javalin;

import java.io.IOException;

public class WebServerApplication {
	private final int port;
	private HelperApplication helperApplication;

	public WebServerApplication(int port, HelperApplication helperApplication) {
		this.port = port;
		this.helperApplication = helperApplication;
	}

	public void fillInformation() throws IOException, InterruptedException {
		helperApplication.fillInformation();
	}

	public void serve() {
		Javalin app = Javalin.create().start(port);

		app.get("/courses", helperApplication.new GetCoursesHandler());
		app.get("/profile/*", helperApplication.new GetProfileHandler());
		app.get("/course/*/*", helperApplication.new ViewAddCourseHandler());
		app.post("/course/*/*", helperApplication.new AddCourseHandler());
//		app.get("/change_plan/*", helperApplication.new ChangePlanHandler());
//		app.get("/remove_course/*", helperApplication.new RemoveCourseHandler());
//		app.get("/plan/*", helperApplication.new ViewPlanHandler());
//		app.get("/submit/*", helperApplication.new SubmissionHandler());
//		app.get("/check_submission/", helperApplication.new CheckSubmissionHandler());
//		app.get("/submit_ok", helperApplication.new SubmissionOKHandler());
//		app.get("/submit_failed", helperApplication.new SubmissionFailedHandler());
	}
}

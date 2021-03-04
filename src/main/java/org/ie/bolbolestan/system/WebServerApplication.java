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
	}
}

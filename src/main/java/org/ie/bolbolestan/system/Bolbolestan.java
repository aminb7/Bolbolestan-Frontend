package org.ie.bolbolestan.system;

import java.io.IOException;

public class Bolbolestan {
	private WebServerApplication webServerApplication;

	public Bolbolestan(int port) {
		this.webServerApplication = new WebServerApplication(port, new HelperApplication());
	}

	public void start() throws IOException, InterruptedException {
		this.webServerApplication.fillInformation();
		this.webServerApplication.serve();
	}
}

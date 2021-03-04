package org.ie.bolbolestan;

import org.ie.bolbolestan.system.Bolbolestan;
import org.ie.bolbolestan.utility.HttpGetter;

import java.io.IOException;

public class BolbolestanWebSite {
	public static void main(String[] args) throws IOException, InterruptedException {
		Bolbolestan bolbolestan = new Bolbolestan(8081);
		bolbolestan.start();
	}
}

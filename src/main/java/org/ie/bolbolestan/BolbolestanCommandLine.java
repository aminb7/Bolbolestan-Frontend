package org.ie.bolbolestan;

import org.ie.bolbolestan.utility.HttpGetter;

import java.io.IOException;

public class BolbolestanCommandLine {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println(HttpGetter.Get("http://138.197.181.131:5000/api/courses"));
	}
}

package org.ie.bolbolestan.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BolbolestanTest {
    ObjectMapper objectMapper;
    Bolbolestan bolbolestan;

    public BolbolestanTest() {
        objectMapper = new ObjectMapper();
        bolbolestan = new Bolbolestan(8081);
    }

    @BeforeEach
    public void Setup() {
        bolbolestan.start();
    }

    @Test
    public void failedResultShouldBeGivenFromBolbolestanBecauseOfWrongSID() throws IOException, InterruptedException {
        String uri = "http://localhost:8081/submit/2";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(response.statusCode(), 404);
    }

    @Test
    public void failedResultShouldBeGivenFromBolbolestanBecauseOfInadequateSelectedUnits() throws IOException, InterruptedException {
        String uri = "http://localhost:8081/submit/810196285";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    @Test
    public void succeededResultShouldBeGivenFromBolbolestanInSucceededSubmissionScenario() throws IOException, InterruptedException {
        String sid = "810196285";
        String addCourseParam = "std_id=" + sid;

        String course2Uri = "http://localhost:8081/course/8101020/01";
        String course1Uri = "http://localhost:8081/course/8101021/01";
        String course3Uri = "http://localhost:8081/course/8101022/01";
        String course4Uri = "http://localhost:8081/course/8101013/01";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest addCourse1Request = HttpRequest.newBuilder()
                .uri(URI.create(course1Uri))
                .POST(HttpRequest.BodyPublishers.ofString(addCourseParam))
                .build();
        HttpRequest addCourse2Request = HttpRequest.newBuilder()
                .uri(URI.create(course2Uri))
                .POST(HttpRequest.BodyPublishers.ofString(addCourseParam))
                .build();
        HttpRequest addCourse3Request = HttpRequest.newBuilder()
                .uri(URI.create(course3Uri))
                .POST(HttpRequest.BodyPublishers.ofString(addCourseParam))
                .build();
        HttpRequest addCourse4Request = HttpRequest.newBuilder()
                .uri(URI.create(course4Uri))
                .POST(HttpRequest.BodyPublishers.ofString(addCourseParam))
                .build();

        HttpResponse<String> addCourse1Response = client.send(addCourse1Request,
                HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> addCourse2Response = client.send(addCourse2Request,
                HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> addCourse3Response = client.send(addCourse3Request,
                HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> addCourse4Response = client.send(addCourse4Request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(addCourse1Response.statusCode(), 200);
        assertEquals(addCourse2Response.statusCode(), 200);
        assertEquals(addCourse3Response.statusCode(), 200);
        assertEquals(addCourse4Response.statusCode(), 200);

        String submissionUri = "http://localhost:8081/submit/" + sid;
        String submissionParam = "";
        HttpRequest submissionRequest = HttpRequest.newBuilder()
                .uri(URI.create(submissionUri))
                .POST(HttpRequest.BodyPublishers.ofString(submissionParam))
                .build();

        HttpResponse<String> submissionResponse = client.send(submissionRequest,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(submissionResponse.statusCode(), 200);
    }
}
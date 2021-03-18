package com.example.CA3.model;

import com.example.CA3.utility.RawDataCollector;
import com.example.CA3.utility.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BolbolestanDatabase {
    private static BolbolestanDatabase single_instance = null;

    private Map<String, Map<String, Course>> courses;
    private Map<String, Student> students;

    private BolbolestanDatabase()
    {
        this.courses = new HashMap<>();
        this.students = new HashMap<>();

        fillInformation();
    }

    public static BolbolestanDatabase getInstance()
    {
        if (single_instance == null)
            single_instance = new BolbolestanDatabase();

        return single_instance;
    }

    private void fillInformation() {
        String host = "http://138.197.181.131:5000";
        Course[] coursesList = null;
        try {
            coursesList = JsonParser.createObject(RawDataCollector.requestCourses(host), Course[].class);
        }
        catch (Exception e) {
        }

        List.of(coursesList).forEach(course -> {
            if (!courses.containsKey(course.getCode()))
                courses.put(course.getCode(), new HashMap<>());

            this.courses.get(course.getCode()).put(course.getClassCode(), course);
        });

        Student[] studentsList = null;
        try {
            studentsList = JsonParser.createObject(RawDataCollector.requestStudents(host), Student[].class);
        }
        catch (Exception e){
        }
        List<String> studentIds = new ArrayList<>();
        List.of(studentsList).forEach(student -> studentIds.add(student.getId()));

        try {
            Map<String, String> rawGrades = RawDataCollector.requestGrades(host, studentIds);
            for (Student student : studentsList) {
                student.setGradedCourses(JsonParser.createObject(rawGrades.get(student.getId()), GradedCourse[].class));

                for (Map.Entry<String, GradedCourse> entry : student.getGradedCourses().entrySet()) {
                    entry.getValue().setCourse(this.courses.get(entry.getKey()).entrySet().iterator().next().getValue());
                }
            }

            List.of(studentsList).forEach(student -> this.students.put(student.getId(), student));
        }
        catch (Exception e) {
        }
    }
}

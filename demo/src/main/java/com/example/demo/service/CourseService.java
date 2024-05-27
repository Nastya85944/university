package com.example.demo.service;

import com.example.demo.entities.Course;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface CourseService {

    Course addCourse(Course course);

    Course updateCourse(Course course, int courseId) throws ResourceNotFoundException;

    void deleteCourse(int courseId) throws ResourceNotFoundException;

    List<Course> findAllCourses();

    Course findCourseById(int courseId) throws ResourceNotFoundException;
}

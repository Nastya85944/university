package com.example.demo.controllers;

import com.example.demo.entities.Course;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/university")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses/{id}")
    public Course findCourseById(@PathVariable("id") int courseId) throws ResourceNotFoundException {
        return courseService.findCourseById(courseId);
    }

    @GetMapping("/courses")
    public List<Course> findAllCourses() {
        return courseService.findAllCourses();
    }

    @PostMapping("/courses/{id}")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping("/courses/{id}")
    public Course updateCourse(@RequestBody Course course, @PathVariable("id") int courseId) throws ResourceNotFoundException {
        return courseService.updateCourse(course, courseId);
    }

    @DeleteMapping("/courses/{id}")
    public void deleteCourse(@PathVariable("id") int courseId) throws ResourceNotFoundException {
        courseService.deleteCourse(courseId);
    }
}

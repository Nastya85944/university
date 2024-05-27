package com.example.demo.service;

import com.example.demo.entities.Course;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Course course, int courseId) throws ResourceNotFoundException {
        Course existingCourse = findCourseById(courseId);
        existingCourse.setId(course.getId());
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setPrice(course.getPrice());
        existingCourse.setProfessor(course.getProfessor());
        existingCourse.setStudents(course.getStudents());
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(int courseId) throws ResourceNotFoundException {
        Course course = findCourseById(courseId);
        courseRepository.deleteById(course.getId());
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course findCourseById(int courseId) throws ResourceNotFoundException {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by " + courseId));
    }
}

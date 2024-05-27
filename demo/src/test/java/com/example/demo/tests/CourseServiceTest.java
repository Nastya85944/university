package com.example.demo.tests;

import com.example.demo.entities.Course;
import com.example.demo.entities.Professor;
import com.example.demo.entities.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;
    private Course course;

    @BeforeEach
    void init() {
        course = new Course();
        course.setId(1);
        course.setCourseName("Mathematics");
        course.setDescription("An advanced mathematics course");
        course.setPrice(500);
    }

    @Test
    void findCourseById_WhenStudentExists() throws ResourceNotFoundException {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        Optional<Course> foundCourse = Optional.of(courseService.findCourseById(1));

        assertAll(
                () -> assertNotNull(foundCourse),
                () -> assertEquals(course.getId(), foundCourse.get().getId()),
                () -> assertEquals(course.getCourseName(), foundCourse.get().getCourseName()),
                () -> assertEquals(course.getDescription(), foundCourse.get().getDescription()),
                () -> assertEquals(course.getPrice(), foundCourse.get().getPrice())
        );
        verify(courseRepository).findById(1);
    }

    @Test
    void findCourseById_WhenStudentDoesNotExist_ThanExceptionThrowed() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.findCourseById(anyInt()));
        verify(courseRepository).findById(anyInt());
    }

    @Test
    void findAllCourses_WhenCoursesExist() {
        List<Course> courses = new ArrayList<>(Collections.singletonList(course));
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> foundCourses = courseService.findAllCourses();
        assertEquals(foundCourses.size(), courses.size());
        verify(courseRepository).findAll();
    }

    @Test
    void findAllCourses_WhenCoursesDoNotExist() {
        when(courseRepository.findAll()).thenReturn(null);

        List<Course> foundCourses = courseService.findAllCourses();
        assertNull(foundCourses);
        verify(courseRepository).findAll();
    }

    @Test
    void addCourse_WhenCourseDoesNotExist() {
        Course newCourse = new Course();
        newCourse.setId(2);
        newCourse.setCourseName("Logistic");
        newCourse.setDescription("Logistic course");
        newCourse.setPrice(500);
        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        Course addedCourse = courseService.addCourse(newCourse);
        assertAll(
                () -> assertNotNull(addedCourse),
                () -> assertEquals(newCourse.getId(), addedCourse.getId()),
                () -> assertEquals(newCourse.getCourseName(), addedCourse.getCourseName()),
                () -> assertEquals(newCourse.getDescription(), addedCourse.getDescription()),
                () -> assertEquals(newCourse.getPrice(), addedCourse.getPrice())
        );
        verify(courseRepository).save(any(Course.class));

    }

    @Test
    void deleteCourse_WhenCourseExist() throws ResourceNotFoundException {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).deleteById(anyInt());

        courseService.deleteCourse(1);
        verify(courseRepository).findById(anyInt());
        verify(courseRepository).deleteById(anyInt());
    }

    @Test
    void deleteCourse_WhenCourseDoseNotExist_ThanThrowResourceNotFoundException() {
        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(1));
        verify(courseRepository).findById(anyInt());
        verify(courseRepository, never()).deleteById(anyInt());
    }

    @Test
    void updateCourse_WhenCourseExist() throws ResourceNotFoundException {
        Professor professor = new Professor();
        professor.setId(2);
        professor.setFirstName("Mark");
        professor.setLastName("Markov");
        professor.setEmail("mark.markow@gmail.com");

        Set<Student> students = new HashSet<>();
        Student student = new Student();
        student.setId(2);
        student.setFirstName("Ivona");
        student.setLastName("Lonova");
        student.setEmail("ivona.linova@gmail.com");
        student.setDateOfBirth(LocalDate.of(2015, 7, 11));
        students.add(student);

        course.setId(1);
        course.setPrice(700);
        course.setProfessor(professor);
        course.setStudents(students);

        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course updatedCourse = courseService.updateCourse(course, 1);

        assertAll(
                () -> assertEquals(700, updatedCourse.getPrice()),
                () -> assertEquals(1, updatedCourse.getStudents().size()),
                () -> assertEquals("Markov", updatedCourse.getProfessor().getLastName())
        );

        verify(courseRepository).findById(1);
        verify(courseRepository).save(any(Course.class));
    }
}

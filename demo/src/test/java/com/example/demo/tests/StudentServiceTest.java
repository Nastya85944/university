package com.example.demo.tests;

import com.example.demo.entities.Course;
import com.example.demo.entities.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void init() {
        student = new Student();
        student.setId(1);
        student.setFirstName("Anna");
        student.setLastName("Ivanova");
        student.setEmail("anna.ivanove@gmail.com");
        student.setDateOfBirth(LocalDate.of(2014, 5, 15));
    }

    @Test
    void findStudentById_WhenStudentExists() throws ResourceNotFoundException {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        Optional<Student> foundStudent = Optional.of(studentService.findStudentById(1));

        assertAll(
                () -> assertNotNull(foundStudent),
                () -> assertEquals(student.getId(), foundStudent.get().getId()),
                () -> assertEquals(student.getFirstName(), foundStudent.get().getFirstName()),
                () -> assertEquals(student.getLastName(), foundStudent.get().getLastName()),
                () -> assertEquals(student.getEmail(), foundStudent.get().getEmail())
        );
        verify(studentRepository).findById(1);
    }

    @Test
    void findStudentById_WhenStudentDoesNotExist_ThanExceptionThrowed() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.findStudentById(anyInt()));
        verify(studentRepository).findById(anyInt());
    }

    @Test
    void findAllStudents_WhenStudentsExist() {
        List<Student> students = new ArrayList<>(Collections.singletonList(student));
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> foundStudents = studentService.findAllStudents();
        assertEquals(foundStudents.size(), students.size());
        verify(studentRepository).findAll();
    }

    @Test
    void findAllStudents_WhenStudentsDoNotExist() {
        when(studentRepository.findAll()).thenReturn(null);

        List<Student> foundStudents = studentService.findAllStudents();
        assertNull(foundStudents);
        verify(studentRepository).findAll();
    }

    @Test
    void addStudent_WhenStudentDoesNotExist() {
        Student newStudent = new Student();
        newStudent.setId(2);
        newStudent.setFirstName("Olada");
        newStudent.setLastName("Mira");
        newStudent.setEmail("olga.mira@gmail.com");
        newStudent.setDateOfBirth(LocalDate.of(2016, 7, 25));

        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        Student addedStudent = studentService.addStudent(newStudent);
        assertAll(
                () -> assertNotNull(addedStudent),
                () -> assertEquals(addedStudent.getId(), addedStudent.getId()),
                () -> assertEquals(addedStudent.getFirstName(), addedStudent.getFirstName()),
                () -> assertEquals(addedStudent.getLastName(), addedStudent.getLastName()),
                () -> assertEquals(addedStudent.getEmail(), addedStudent.getEmail())
        );
        verify(studentRepository).save(any(Student.class));

    }

    @Test
    void deleteStudent_WhenStudentExist() throws ResourceNotFoundException {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(anyInt());

        studentService.deleteStudent(1);
        verify(studentRepository).findById(anyInt());
        verify(studentRepository).deleteById(anyInt());
    }

    @Test
    void deleteStudent_WhenStudentDoesNotExist_ThanThrowResourceNotFoundException() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(1));
        verify(studentRepository).findById(anyInt());
        verify(studentRepository, never()).deleteById(anyInt());
    }

    @Test
    void updateStudent_WhenStudentExist() throws ResourceNotFoundException{
        Set<Course> courses = new HashSet<>();
        Course course = new Course();
        course.setId(2);
        course.setCourseName("Psychology");
        course.setDescription("An advanced psychology course");
        course.setPrice(300);
        courses.add(course);

        student.setId(1);
        student.setFirstName("Anna");
        student.setLastName("Petrova");
        student.setEmail("anna.petrova@gmail.com");
        student.setDateOfBirth(LocalDate.of(2014, 5, 15));
        student.setCourses(courses);

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = studentService.updateStudent(student, 1);

        assertEquals("Petrova", updatedStudent.getLastName());
        verify(studentRepository).findById(1);
        verify(studentRepository).save(any(Student.class));
    }
}

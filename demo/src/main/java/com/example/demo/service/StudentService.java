package com.example.demo.service;

import com.example.demo.entities.Student;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface StudentService {

    Student addStudent(Student student);
    Student  updateStudent(Student student, int studentId) throws ResourceNotFoundException ;
    Student findStudentById(int studentId) throws ResourceNotFoundException;
    List<Student> findAllStudents();
    void deleteStudent(int studentId) throws ResourceNotFoundException;
}

package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/university")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/{id}")
    public Student findStudentById(@PathVariable("id") int studentId) throws ResourceNotFoundException {
        return studentService.findStudentById(studentId);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable("id") int studentId) throws ResourceNotFoundException {
        return studentService.updateStudent(student, studentId);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable("id") int studentId) throws ResourceNotFoundException {
        studentService.deleteStudent(studentId);
    }

    @GetMapping("/students")
    public List<Student> findAllStudents() {
        return studentService.findAllStudents();
    }
}

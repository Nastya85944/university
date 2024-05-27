package com.example.demo.service;

import com.example.demo.entities.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student, int studentId) throws ResourceNotFoundException {
        Student existingStudent = findStudentById(studentId);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setCourses(student.getCourses());
        return studentRepository.save(existingStudent);
    }

    @Override
    public Student findStudentById(int studentId) throws ResourceNotFoundException {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by " + studentId));
    }

    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(int studentId) throws ResourceNotFoundException {
        Student student = findStudentById(studentId);
        studentRepository.deleteById(student.getId());
    }

    public static String testStaticMethod(String message) {
        return "Welcome " + message;
    }
}

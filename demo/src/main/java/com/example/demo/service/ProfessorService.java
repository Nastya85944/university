package com.example.demo.service;

import com.example.demo.entities.Professor;
import com.example.demo.exception.ResourceNotFoundException;

import java.util.List;

public interface ProfessorService {

    Professor addProfessor(Professor professor);

    Professor updateProfessor(Professor professor, int professorId) throws ResourceNotFoundException;

    void deleteProfessor(int professorId) throws ResourceNotFoundException;

    List<Professor>findAllProfessors();

    Professor findProfessorById(int professorId) throws ResourceNotFoundException;
}

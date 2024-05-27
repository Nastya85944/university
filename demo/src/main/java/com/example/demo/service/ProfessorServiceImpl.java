package com.example.demo.service;

import com.example.demo.entities.Professor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public Professor addProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor updateProfessor(Professor professor, int professorId) throws ResourceNotFoundException{
        Professor existingProfessor = findProfessorById(professorId);
        existingProfessor.setId(professor.getId());
        existingProfessor.setFirstName(professor.getFirstName());
        existingProfessor.setLastName(professor.getLastName());
        existingProfessor.setEmail(professor.getEmail());
        return professorRepository.save(existingProfessor);
    }

    @Override
    public void deleteProfessor(int professorId) throws ResourceNotFoundException{
        Professor professor = findProfessorById(professorId);
        professorRepository.deleteById(professor.getId());
    }

    @Override
    public List<Professor> findAllProfessors() {
        return professorRepository.findAll();
    }

    @Override
    public Professor findProfessorById(int professorId) throws ResourceNotFoundException {
        return professorRepository.findById(professorId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found by " + professorId));
    }
}

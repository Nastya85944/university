package com.example.demo.controllers;

import com.example.demo.entities.Professor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ProfessorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/university")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/professors/{id}")
    public Professor findProfessorById(@PathVariable("id") int professorId) throws ResourceNotFoundException {
        return professorService.findProfessorById(professorId);
    }

    @GetMapping("/professors")
    public List<Professor> findAllProfessors() {
        return professorService.findAllProfessors();
    }

    @PostMapping("/professors")
    public Professor addProfessor(@RequestBody Professor professor) {
        return professorService.addProfessor(professor);
    }

    @PutMapping("professor/{id}")
    public Professor updateProfessor(@RequestBody Professor professor, @PathVariable("id") int professorId) throws ResourceNotFoundException {
        return professorService.updateProfessor(professor, professorId);
    }

    @DeleteMapping("professor/{id}")
    public void deleteProfessor(@PathVariable("id") int professorId) throws ResourceNotFoundException {
        professorService.deleteProfessor(professorId);
    }

}

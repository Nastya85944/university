package com.example.demo.tests;

import com.example.demo.entities.Professor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ProfessorRepository;
import com.example.demo.service.ProfessorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;
    @InjectMocks
    private ProfessorServiceImpl professorService;

    private Professor professor;

    @BeforeEach
    void init() {
        professor = new Professor();
        professor.setId(1);
        professor.setFirstName("Michal");
        professor.setLastName("Jank");
        professor.setEmail("michal.jank@gmail.com");
    }

    @Test
    void findProfessorById_WhenProfessorExists() throws ResourceNotFoundException {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.of(professor));
        Optional<Professor> foundProfessor = Optional.of(professorService.findProfessorById(1));

        assertAll(
                () -> assertNotNull(foundProfessor),
                () -> assertEquals(professor.getId(), foundProfessor.get().getId()),
                () -> assertEquals(professor.getFirstName(), foundProfessor.get().getFirstName()),
                () -> assertEquals(professor.getLastName(), foundProfessor.get().getLastName()),
                () -> assertEquals(professor.getEmail(), foundProfessor.get().getEmail())
        );
        verify(professorRepository).findById(1);
    }

    @Test
    void findProfessorById_WhenProfessorDoesNotExist_ThanExceptionThrowed() {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> professorService.findProfessorById(anyInt()));
        verify(professorRepository).findById(anyInt());
    }

    @Test
    void findAllProfessors_WhenStudentsExist() {
        List<Professor> students = new ArrayList<>(Collections.singletonList(professor));
        when(professorRepository.findAll()).thenReturn(students);

        List<Professor> foundProfessor = professorService.findAllProfessors();
        assertEquals(foundProfessor.size(), students.size());
        verify(professorRepository).findAll();
    }

    @Test
    void findAllProfessors_WhenProfessorsDoNotExist() {
        when(professorRepository.findAll()).thenReturn(null);

        List<Professor> foundProfessor = professorService.findAllProfessors();
        assertNull(foundProfessor);
        verify(professorRepository).findAll();
    }

    @Test
    void addProfessor_WhenProfessorDoesNotExist() {
        Professor newProfessor = new Professor();
        newProfessor.setId(2);
        newProfessor.setFirstName("Mark");
        newProfessor.setLastName("Miron");
        newProfessor.setEmail("mark.miron@gmail.com");

        when(professorRepository.save(any(Professor.class))).thenReturn(newProfessor);

        Professor addedStudent = professorService.addProfessor(newProfessor);
        assertAll(
                () -> assertNotNull(addedStudent),
                () -> assertEquals(addedStudent.getId(), addedStudent.getId()),
                () -> assertEquals(addedStudent.getFirstName(), addedStudent.getFirstName()),
                () -> assertEquals(addedStudent.getLastName(), addedStudent.getLastName()),
                () -> assertEquals(addedStudent.getEmail(), addedStudent.getEmail())
        );
        verify(professorRepository).save(any(Professor.class));

    }

    @Test
    void deleteProfessor_WhenProfessorExists() throws ResourceNotFoundException {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.of(professor));
        doNothing().when(professorRepository).deleteById(anyInt());

        professorService.deleteProfessor(1);
        verify(professorRepository).findById(anyInt());
        verify(professorRepository).deleteById(anyInt());
    }

    @Test
    void deleteProfessor_WhenProfessorDoseNotExist_ThanThrowResourceNotFoundException() {
        when(professorRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> professorService.deleteProfessor(1));
        verify(professorRepository).findById(anyInt());
        verify(professorRepository, never()).deleteById(anyInt());
    }

    @Test
    void updateProfessor_WhenProfessorExist() throws ResourceNotFoundException{
        professor.setId(1);
        professor.setFirstName("Johna");
        professor.setLastName("Doe");
        professor.setEmail("johna.doe@example.com");

        when(professorRepository.findById(anyInt())).thenReturn(Optional.of(professor));
        when(professorRepository.save(any(Professor.class))).thenReturn(professor);

        Professor updatedProfessor = professorService.updateProfessor(professor, 1);

        assertEquals("Johna", updatedProfessor.getFirstName());
        verify(professorRepository).findById(1);
        verify(professorRepository).save(professor);
    }
}

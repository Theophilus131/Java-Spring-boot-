package com.example.studentapi.service;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setEmail("john@gmail.com");
        student.setCourse("Computer Science");
        
    }

    @Test
    void createStudent_shouldReturnSavedStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.createStudent(student);

        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isEqualTo(1L);
        assertThat(savedStudent.getName()).isEqualTo("John");
        verify(studentRepository,times(1)).save(student);
    }

    @Test
    void getStudentById_whenStudentExists_shouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getId()).isEqualTo(1L);
        assertThat(foundStudent.getName()).isEqualTo("John");

        verify(studentRepository,times(1)).findById(1L);
    }

    @Test
    void getStudentById_whenStudentDoesNotExist_shouldThrowResourceNotFoundException() {

        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, times(1)).findById(99L);

    }

    @Test
    void getAllStudents_shouldReturnAllStudents() {

        Student student1 = new Student();
        student1.setId(2L);
        student1.setName("smith");
        student1.setEmail("Smith@gmail.com");
        student1.setCourse("Computer");

        when(studentRepository.findAll()).thenReturn(List.of(student, student1));

        List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(1).getName()).isEqualTo("smith");
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getAllStudents_whenNoStudents_shouldReturnEmptyList() {
        when(studentRepository.findAll()).thenReturn(List.of());

        List<Student> result = studentService.getAllStudents();

        assertThat(result).isEmpty();
        verify(studentRepository, times(1)).findAll();

    }

    @Test
    void updateStudent_whenStudentExists_shouldReturnUpdatedStudent() {

        Student updatedData = new Student();
        updatedData.setName("John Updated");
        updatedData.setEmail("johnupdated@example.com");
        updatedData.setCourse("Data Science");


        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("John Updated");
        savedStudent.setEmail("johnupdated@example.com");
        savedStudent.setCourse("Data Science");


        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        Student result = studentService.updateStudent(1L, updatedData);

        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getEmail()).isEqualTo("johnupdated@example.com");
        assertThat(result.getCourse()).isEqualTo("Data Science");
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(Student.class));


    }

    @Test
    void updateStudent_whenStudentDoesNotExist_shouldThrowResourceNotFoundException() {

        Student updatedData = new Student();
        updatedData.setName("Ghost");
        updatedData.setEmail("ghost@example.com");
        updatedData.setCourse("Physics");

        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(99L, updatedData))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudentById_whenStudentExists_shouldDeleteStudent() {

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudentById(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void deleteStudentById_whenStudentDoesNotExist_shouldThrowResourceNotFoundException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.deleteStudentById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found");

        verify(studentRepository, never()).delete(any());
    }
}
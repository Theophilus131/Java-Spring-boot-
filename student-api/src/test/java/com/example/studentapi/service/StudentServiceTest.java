package com.example.studentapi.service;

import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        assertThat(savedStudent.getName()).isEqualTo("john");
        verify(studentRepository,times(1)).save(student);
    }

    @Test
    void getStudentById() {
    }

    @Test
    void updateStudent() {
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void deleteStudentById() {
    }
}
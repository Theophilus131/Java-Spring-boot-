package com.example.studentapi.controller;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student.setCourse("Computer Science");
    }

    @Test
    void createStudent_withValidPayload_shouldReturn201() throws Exception {
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.course").value("Computer Science"));

        verify(studentService, times(1)).createStudent(any(Student.class));
    }


    @Test
    void createStudent_withBlankName_shouldReturn400() throws Exception {
        student.setName("");

        mockMvc.perform(post("/api/v1/students")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any());
    }

    @Test
    void createStudent_withInvalidEmail_shouldReturn400() throws Exception {
        student.setEmail("not-an-email");

        mockMvc.perform(post("/api/v1/students")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any());
    }


    @Test
    void createStudent_withBlankCourse_shouldReturn400() throws Exception {
        student.setCourse("");

        mockMvc.perform(post("/api/v1/students")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any());
    }

    @Test
    void getAllStudents_shouldReturn200WithList() throws Exception {
        Student second = new Student();
        second.setId(2L);
        second.setName("Jane Smith");
        second.setEmail("jane@example.com");
        second.setCourse("Mathematics");

        when(studentService.getAllStudents()).thenReturn(List.of(student, second));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getAllStudents_whenEmpty_shouldReturn200WithEmptyList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getStudentById_whenExists_shouldReturn200() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(studentService, times(1)).getStudentById(1L);
    }


    @Test
    void getStudentById_whenNotFound_shouldReturn404() throws Exception {
        when(studentService.getStudentById(99L))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        mockMvc.perform(get("/api/v1/students/99"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).getStudentById(99L);
    }


    @Test
    void updateStudent_withValidPayload_shouldReturn200() throws Exception {
        Student updated = new Student();
        updated.setId(1L);
        updated.setName("John Updated");
        updated.setEmail("johnupdated@example.com");
        updated.setCourse("Data Science");

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.course").value("Data Science"));

        verify(studentService, times(1)).updateStudent(eq(1L), any(Student.class));
    }


    @Test
    void updateStudent_whenNotFound_shouldReturn404() throws Exception {
        when(studentService.updateStudent(eq(99L), any(Student.class)))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        mockMvc.perform(put("/api/v1/students/99")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }


    @Test
    void updateStudent_withInvalidPayload_shouldReturn400() throws Exception {
        student.setName("");

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).updateStudent(any(), any());
    }

    @Test
    void deleteStudent_whenExists_shouldReturn204() throws Exception {
        doNothing().when(studentService).deleteStudentById(1L);

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudentById(1L);
    }

    @Test
    void deleteStudent_whenNotFound_shouldReturn404() throws Exception {
        doThrow(new ResourceNotFoundException("Student not found"))
                .when(studentService).deleteStudentById(99L);

        mockMvc.perform(delete("/api/v1/students/99"))
                .andExpect(status().isNotFound());

        verify(studentService, times(1)).deleteStudentById(99L);
    }


}
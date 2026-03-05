package com.example.studentapi.service;

import com.example.studentapi.exception.ResourceNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        return studentRepository.save(student);

    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public Iterable<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void deleteStudentById(Long id){
        studentRepository.deleteById(id);
    }









}

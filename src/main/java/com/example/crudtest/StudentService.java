package com.example.crudtest;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student changeIsWorking(Long studentId, Boolean isWorking) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        student.setWorking(isWorking);
        return studentRepository.save(student);
    }
}

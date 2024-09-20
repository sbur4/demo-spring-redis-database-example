package com.springboot.repository;

import com.springboot.model.Student;

public interface StudentRepository {

    void save(Student student);

    Student find(Long id);

    void delete(Long id);
}
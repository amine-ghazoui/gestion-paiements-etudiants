package org.example.enset_spg_ang.repositories;

import org.example.enset_spg_ang.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByCode(String code);
    List<Student> findByProgramId(String programId);
}

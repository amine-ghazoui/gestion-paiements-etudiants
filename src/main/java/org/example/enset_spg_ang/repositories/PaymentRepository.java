package org.example.enset_spg_ang.repositories;

import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String studentCode);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);

}


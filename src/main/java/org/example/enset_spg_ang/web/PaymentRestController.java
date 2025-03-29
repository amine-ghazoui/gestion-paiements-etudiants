package org.example.enset_spg_ang.web;

import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.example.enset_spg_ang.repositories.PaymentRepository;
import org.example.enset_spg_ang.repositories.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentRestController {

    StudentRepository studentRepository;
    PaymentRepository paymentRepository;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    // ensemble de méthode de consultations

    @GetMapping(path = "/payments")
    public List<Payment> allPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> listPaymentsByStudent(@PathVariable String code) {
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping("/payments/byType")
    public List<Payment> listPaymentsByType(@RequestParam PaymentType type) {
        return paymentRepository.findByType(type);
    }

    @GetMapping("/payments/byStatus")
    public List<Payment> listPaymentsByStatus(@RequestParam PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).get();
    }

    @GetMapping(path = "/students")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code) {
        return studentRepository.findByCode(code);
    }

    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId) {
        return studentRepository.findByProgramId(programId);
    }



}


/*
Le rôle de @PathVariable dans ton code est d'extraire une valeur de l'URL et de l'associer
à un paramètre de méthode dans un contrôleur Spring Boot.
 */

/*
@RequestParam
Extrait une valeur depuis les paramètres de requête (query parameters).
Utilisé pour les requêtes avec des paramètres dans l’URL après ?.
Exemple : /studentsByProgramId?programId=123
 */

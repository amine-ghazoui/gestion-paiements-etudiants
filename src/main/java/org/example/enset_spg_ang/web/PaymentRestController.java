package org.example.enset_spg_ang.web;

import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.example.enset_spg_ang.repositories.PaymentRepository;
import org.example.enset_spg_ang.repositories.StudentRepository;
import org.example.enset_spg_ang.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class PaymentRestController {

    StudentRepository studentRepository;
    PaymentRepository paymentRepository;
    PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
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

    @PostMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status,@PathVariable Long id) {
        return paymentService.updatePaymentStatus(status, id);
    }

    // Ce point de terminaison accepte les requêtes POST envoyées à l'URL "/payments".
    // Il est configuré pour consommer des données de type "multipart/form-data",
    // ce qui est généralement utilisé pour envoyer des formulaires contenant des fichiers (ex: fichiers PDF, images...).
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {
        return paymentService.savePayment(file, date, amount, type, studentCode);
    }


    // Cette méthode permet de récupérer le fichier PDF associé à un paiement spécifique.
    // Elle est appelée via une requête GET à l'URL "/paymentFile/{paymentId}".
    // Elle retourne un fichier PDF au format binaire (byte[]).
    @GetMapping(value = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {

       return paymentService.getPaymentFile(paymentId);
    }




}


/*
Le rôle de @PathVariable dans ton code est d'extraire une valeur de l'URL et de l'associer
à un paramètre de méthode dans un contrôleur Spring Boot.
 */

/*
@RequestParam
Extrait une valeur depuis les paramètres de requête (query parameters).
Utilisé pour les requêtes avec des "paramètres" dans l’URL après ?.
Exemple : /studentsByProgramId?programId=123
NB : Elle est utilisée pour récupérer une valeur placée dans les paramètres de la requête, après un ?.
 */

/*
MultipartFile est une interface de Spring utilisée pour gérer les fichiers
téléchargés via des requêtes HTTP multipart/form-data. Elle permet d'accéder
au contenu du fichier, à son nom, à sa taille, etc.

multipart/form-data : les donnée contient plusieurs parties (ex : des données structurées, des fichiers, etc.)
 */

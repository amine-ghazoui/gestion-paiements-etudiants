package org.example.enset_spg_ang.web;

import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.example.enset_spg_ang.repositories.PaymentRepository;
import org.example.enset_spg_ang.repositories.StudentRepository;
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

    @PostMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status,@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);

        return paymentRepository.save(payment);
    }

    // Ce point de terminaison accepte les requêtes POST envoyées à l'URL "/payments".
    // Il est configuré pour consommer des données de type "multipart/form-data",
    // ce qui est généralement utilisé pour envoyer des formulaires contenant des fichiers (ex: fichiers PDF, images...).
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public Payment savePayment(@RequestParam MultipartFile file, LocalDate date,
                               double amount, PaymentType type, String studentCode) throws IOException {
        // La méthode prend en entrée un fichier (MultipartFile), une date, un montant, un type de paiement,
        // et un code étudiant. Elle retourne un objet Payment après l'avoir sauvegardé.

        Path folderPath = Paths.get(System.getProperty("user.home"),  "enset-data", "payments");
        // Définit le chemin du dossier où les fichiers seront stockés, dans le répertoire utilisateur.

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
            // Si le dossier n'existe pas, il est créé.
        }

        String fileName = UUID.randomUUID().toString();
        // Génère un nom de fichier unique en utilisant un UUID.

        Path filePath = Paths.get(System.getProperty("user.home"),  "enset-data", "payments", fileName+".pdf");
        // Définit le chemin complet du fichier, avec l'extension ".pdf".

        Files.copy(file.getInputStream(), filePath);
        // Copie le contenu du fichier téléchargé dans le chemin défini.

        Student student = studentRepository.findByCode(studentCode);
        // Recherche l'étudiant correspondant au code fourni dans la base de données.

        Payment payment = Payment.builder()
                .date(date) // Définit la date du paiement.
                .type(type) // Définit le type de paiement.
                .student(student) // Associe le paiement à l'étudiant trouvé.
                .amount(amount) // Définit le montant du paiement.
                .file(filePath.toUri().toString()) // Enregistre le chemin du fichier en tant qu'URI.
                .status(PaymentStatus.CREATED) // Définit le statut du paiement comme "CREATED".
                .build();
        // Construit un objet Payment avec les informations fournies.

        return paymentRepository.save(payment);
        // Sauvegarde l'objet Payment dans la base de données et le retourne.
    }


    // Cette méthode permet de récupérer le fichier PDF associé à un paiement spécifique.
    // Elle est appelée via une requête GET à l'URL "/paymentFile/{paymentId}".
    // Elle retourne un fichier PDF au format binaire (byte[]).
    @GetMapping(value = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {

        // Recherche le paiement dans la base de données à partir de l'identifiant donné dans l'URL.
        // Attention : .get() suppose que le paiement existe. Sinon, cela lèvera une exception.
        Payment payment = paymentRepository.findById(paymentId).get();

        // Récupère le chemin du fichier PDF depuis l'objet Payment, puis lit tout son contenu en mémoire.
        // Le fichier est lu sous forme de tableau de bytes pour être retourné dans la réponse.
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
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

package org.example.enset_spg_ang.services;


import jakarta.transaction.Transactional;
import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.example.enset_spg_ang.repositories.PaymentRepository;
import org.example.enset_spg_ang.repositories.StudentRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    PaymentRepository paymentRepository;
    StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);

        return paymentRepository.save(payment);
    }


    public Payment savePayment(MultipartFile file, LocalDate date,
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



    public byte[] getPaymentFile(Long paymentId) throws IOException {

        // Recherche le paiement dans la base de données à partir de l'identifiant donné dans l'URL.
        // Attention : .get() suppose que le paiement existe. Sinon, cela lèvera une exception.
        Payment payment = paymentRepository.findById(paymentId).get();

        // Récupère le chemin du fichier PDF depuis l'objet Payment, puis lit tout son contenu en mémoire.
        // Le fichier est lu sous forme de tableau de bytes pour être retourné dans la réponse.
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}

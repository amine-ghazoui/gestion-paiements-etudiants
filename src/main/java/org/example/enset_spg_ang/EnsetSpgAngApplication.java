package org.example.enset_spg_ang;

import org.example.enset_spg_ang.entities.Payment;
import org.example.enset_spg_ang.entities.PaymentStatus;
import org.example.enset_spg_ang.entities.PaymentType;
import org.example.enset_spg_ang.entities.Student;
import org.example.enset_spg_ang.repositories.PaymentRepository;
import org.example.enset_spg_ang.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class EnsetSpgAngApplication {

    public static void main(String[] args) {

        SpringApplication.run(EnsetSpgAngApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        return args -> {

            // Créer des étudiants

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Amine").lastName("ghazoui").code("D137579059")
                    .programId("SDIA").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Mohamed").lastName("ghazoui").code("K123756728")
                    .programId("GL").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("IBtissame").lastName("ghazoui").code("D86579059")
                    .programId("GM").build());
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
                    .firstName("Fatima").lastName("Ait bahadou").code("S16089059")
                    .programId("EL").build());

            // Créer des paiements pour chaque étudiant

            // Créer un tableau de types de paiement(retourne une liste de tous les types de paiement)
            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            //chercher tous les étudiants
            studentRepository.findAll().forEach(etu -> {
                // pour chaque étudiant, on va faire une boucle pour créer les paiements (10 paiements par étudiant)
                for (int i = 0; i < 10; i++) {
                    //pour chaque i on va générer un index aléatoire (entre 0 et la taille du tableau de types de paiement)
                    int index = random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder()
                            //générer un montant aléatoire entre 1000 et 20000
                            .amount(1000+(int)(Math.random()+20000))
                            //choisir un type de paiement aléatoire
                            .type(paymentTypes[index])
                            //choisir un status (par défaut on va choisir le status CREATED)
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(etu)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }

}

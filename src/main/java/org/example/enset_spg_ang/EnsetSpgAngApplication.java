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

            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(etu -> {
                for (int i = 0; i < 10; i++) {
                    int index = random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder()
                            .amount(1000+(int)(Math.random()+20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(etu)
                            .build();
                }
            });
        };
    }

}

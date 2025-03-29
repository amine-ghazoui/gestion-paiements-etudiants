package org.example.enset_spg_ang.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class Student {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String code;
    private String programId;
    private String photo;
}

package com.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String rollNumber;

    @Column(nullable = false)
    private String email;

    private String phone;
    private String course;
    private String semester;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;

    // Academic summary fields
    private Double cgpa = 0.0;
    private Integer totalCredits = 0;
    private String profilePicUrl;

    // Health fields
    private Double heightCm;
    private Double weightKg;
    private String bloodGroup;
    private String fitnessGoal;  // e.g. WEIGHT_LOSS, MUSCLE_GAIN, MAINTENANCE
}

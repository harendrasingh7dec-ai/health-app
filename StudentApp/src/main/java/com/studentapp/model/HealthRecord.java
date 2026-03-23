package com.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "health_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private Double weightKg;
    private Double heightCm;
    private Integer restingHeartRate;   // bpm
    private Integer systolicBP;         // e.g. 120
    private Integer diastolicBP;        // e.g. 80
    private Integer sleepHours;
    private Double waterIntakeLitres;
    private Integer stressLevel;        // 1-10
    private String mood;                // GREAT, GOOD, OKAY, BAD, TERRIBLE
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // Computed BMI
    public Double getBmi() {
        if (heightCm == null || heightCm == 0 || weightKg == null) return null;
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }

    public String getBmiCategory() {
        Double bmi = getBmi();
        if (bmi == null) return "N/A";
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25.0) return "Normal";
        if (bmi < 30.0) return "Overweight";
        return "Obese";
    }
}

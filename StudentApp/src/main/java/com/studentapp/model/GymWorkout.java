package com.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "gym_workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String workoutType;     // CHEST, BACK, LEGS, ARMS, CARDIO, FULL_BODY, REST
    private Integer durationMinutes;
    private Integer caloriesBurned;
    private Integer setsCompleted;
    private String intensity;       // LOW, MEDIUM, HIGH
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}

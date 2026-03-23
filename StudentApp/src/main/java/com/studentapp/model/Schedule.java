package com.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;
    private String dayOfWeek;    // MONDAY, TUESDAY, etc.
    private String startTime;    // e.g. "08:00"
    private String endTime;      // e.g. "09:30"
    private String room;
    private String teacher;
    private String colorHex;     // for UI display

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}

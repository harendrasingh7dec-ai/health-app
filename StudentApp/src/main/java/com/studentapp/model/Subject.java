package com.studentapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String teacher;
    private String room;
    private Integer totalClasses;
    private Integer attendedClasses;
    private Double credits;

    // Marks
    private Double midtermMarks;
    private Double midtermTotal;
    private Double finalMarks;
    private Double finalTotal;
    private Double practicalMarks;
    private Double practicalTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // Computed fields
    public Double getTotalMarks() {
        return (midtermMarks != null ? midtermMarks : 0)
             + (finalMarks != null ? finalMarks : 0)
             + (practicalMarks != null ? practicalMarks : 0);
    }

    public Double getMaxMarks() {
        return (midtermTotal != null ? midtermTotal : 0)
             + (finalTotal != null ? finalTotal : 0)
             + (practicalTotal != null ? practicalTotal : 0);
    }

    public Double getPercentage() {
        if (getMaxMarks() == 0) return 0.0;
        return (getTotalMarks() / getMaxMarks()) * 100;
    }

    public Double getAttendancePercentage() {
        if (totalClasses == null || totalClasses == 0) return 0.0;
        return (attendedClasses != null ? (double) attendedClasses / totalClasses * 100 : 0.0);
    }

    public String getGrade() {
        double pct = getPercentage();
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 75) return "A-";
        if (pct >= 70) return "B+";
        if (pct >= 60) return "B";
        if (pct >= 50) return "C+";
        if (pct >= 40) return "C";
        return "F";
    }
}

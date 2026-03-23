package com.studentapp.service;

import com.studentapp.model.*;
import com.studentapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired private StudentRepository studentRepo;
    @Autowired private SubjectRepository subjectRepo;

    public Student save(Student student) {
        return studentRepo.save(student);
    }

    public Optional<Student> findById(Long id) {
        return studentRepo.findById(id);
    }

    public Optional<Student> findByRollNumber(String roll) {
        return studentRepo.findByRollNumber(roll);
    }

    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public void delete(Long id) {
        studentRepo.deleteById(id);
    }

    /** Recalculate and persist CGPA for a student */
    public void recalculateCgpa(Long studentId) {
        List<Subject> subjects = subjectRepo.findByStudentId(studentId);
        if (subjects.isEmpty()) return;

        double totalWeighted = 0;
        double totalCredits = 0;
        for (Subject s : subjects) {
            double gp = gradePointFrom(s.getPercentage());
            double credits = s.getCredits() != null ? s.getCredits() : 3.0;
            totalWeighted += gp * credits;
            totalCredits += credits;
        }

        double cgpa = totalCredits > 0 ? totalWeighted / totalCredits : 0;
        studentRepo.findById(studentId).ifPresent(st -> {
            st.setCgpa(Math.round(cgpa * 100.0) / 100.0);
            st.setTotalCredits((int) totalCredits);
            studentRepo.save(st);
        });
    }

    private double gradePointFrom(double percentage) {
        if (percentage >= 90) return 10.0;
        if (percentage >= 80) return 9.0;
        if (percentage >= 70) return 8.0;
        if (percentage >= 60) return 7.0;
        if (percentage >= 50) return 6.0;
        if (percentage >= 40) return 5.0;
        return 0.0;
    }
}

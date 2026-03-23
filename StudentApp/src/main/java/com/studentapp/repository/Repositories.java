package com.studentapp.repository;

import com.studentapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// ─── Student ───────────────────────────────────────────────────────────────
@Repository
interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    Optional<Student> findByEmail(String email);
}

// ─── Subject ───────────────────────────────────────────────────────────────
@Repository
interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByStudentId(Long studentId);

    @Query("SELECT AVG(s.midtermMarks + s.finalMarks + s.practicalMarks) FROM Subject s WHERE s.student.id = :studentId")
    Double getAverageMarks(Long studentId);
}

// ─── TodoTask ──────────────────────────────────────────────────────────────
@Repository
interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
    List<TodoTask> findByStudentId(Long studentId);
    List<TodoTask> findByStudentIdAndCompleted(Long studentId, boolean completed);
    List<TodoTask> findByStudentIdAndSubject(Long studentId, String subject);
}

// ─── Schedule ──────────────────────────────────────────────────────────────
@Repository
interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByStudentId(Long studentId);
    List<Schedule> findByStudentIdAndDayOfWeek(Long studentId, String dayOfWeek);
}

// ─── HealthRecord ──────────────────────────────────────────────────────────
@Repository
interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    List<HealthRecord> findByStudentIdOrderByDateDesc(Long studentId);
    List<HealthRecord> findByStudentIdAndDateBetween(Long studentId, LocalDate start, LocalDate end);
    Optional<HealthRecord> findTopByStudentIdOrderByDateDesc(Long studentId);
}

// ─── GymWorkout ────────────────────────────────────────────────────────────
@Repository
interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long> {
    List<GymWorkout> findByStudentIdOrderByDateDesc(Long studentId);
    List<GymWorkout> findByStudentIdAndDateBetween(Long studentId, LocalDate start, LocalDate end);

    @Query("SELECT SUM(g.caloriesBurned) FROM GymWorkout g WHERE g.student.id = :studentId AND g.date >= :since")
    Integer getTotalCaloriesBurned(Long studentId, LocalDate since);

    @Query("SELECT COUNT(g) FROM GymWorkout g WHERE g.student.id = :studentId AND g.workoutType != 'REST' AND g.date >= :since")
    Long getWorkoutCount(Long studentId, LocalDate since);
}

// ─── NutritionLog ──────────────────────────────────────────────────────────
@Repository
interface NutritionLogRepository extends JpaRepository<NutritionLog, Long> {
    List<NutritionLog> findByStudentIdAndDate(Long studentId, LocalDate date);
    List<NutritionLog> findByStudentIdOrderByDateDesc(Long studentId);

    @Query("SELECT COALESCE(SUM(n.calories), 0) FROM NutritionLog n WHERE n.student.id = :studentId AND n.date = :date")
    Integer getTotalCaloriesForDay(Long studentId, LocalDate date);
}

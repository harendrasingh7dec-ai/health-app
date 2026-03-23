package com.studentapp.service;

import com.studentapp.model.*;
import com.studentapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class HealthService {

    @Autowired private HealthRecordRepository healthRepo;
    @Autowired private GymWorkoutRepository gymRepo;
    @Autowired private NutritionLogRepository nutritionRepo;

    // ─── Health Records ──────────────────────────────────────────────────────

    public HealthRecord saveHealthRecord(HealthRecord record) {
        return healthRepo.save(record);
    }

    public List<HealthRecord> getHealthHistory(Long studentId) {
        return healthRepo.findByStudentIdOrderByDateDesc(studentId);
    }

    public Optional<HealthRecord> getLatestHealthRecord(Long studentId) {
        return healthRepo.findTopByStudentIdOrderByDateDesc(studentId);
    }

    public List<HealthRecord> getHealthInRange(Long studentId, LocalDate start, LocalDate end) {
        return healthRepo.findByStudentIdAndDateBetween(studentId, start, end);
    }

    // ─── Gym Workouts ────────────────────────────────────────────────────────

    public GymWorkout saveWorkout(GymWorkout workout) {
        return gymRepo.save(workout);
    }

    public List<GymWorkout> getWorkouts(Long studentId) {
        return gymRepo.findByStudentIdOrderByDateDesc(studentId);
    }

    public List<GymWorkout> getWorkoutsInRange(Long studentId, LocalDate start, LocalDate end) {
        return gymRepo.findByStudentIdAndDateBetween(studentId, start, end);
    }

    public GymStats getGymStats(Long studentId) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        Integer calories = gymRepo.getTotalCaloriesBurned(studentId, thirtyDaysAgo);
        Long workouts = gymRepo.getWorkoutCount(studentId, thirtyDaysAgo);
        List<GymWorkout> all = gymRepo.findByStudentIdOrderByDateDesc(studentId);

        int totalMinutes = all.stream()
                .filter(w -> w.getDurationMinutes() != null)
                .mapToInt(GymWorkout::getDurationMinutes)
                .sum();

        Map<String, Long> byType = new LinkedHashMap<>();
        all.forEach(w -> byType.merge(w.getWorkoutType(), 1L, Long::sum));

        return new GymStats(
                calories != null ? calories : 0,
                workouts != null ? workouts.intValue() : 0,
                totalMinutes,
                byType
        );
    }

    public void deleteWorkout(Long id) {
        gymRepo.deleteById(id);
    }

    // ─── Nutrition ───────────────────────────────────────────────────────────

    public NutritionLog saveNutrition(NutritionLog log) {
        return nutritionRepo.save(log);
    }

    public List<NutritionLog> getNutritionByDate(Long studentId, LocalDate date) {
        return nutritionRepo.findByStudentIdAndDate(studentId, date);
    }

    public Integer getTodayCalories(Long studentId) {
        return nutritionRepo.getTotalCaloriesForDay(studentId, LocalDate.now());
    }

    public void deleteNutrition(Long id) {
        nutritionRepo.deleteById(id);
    }

    // ─── Inner DTO ───────────────────────────────────────────────────────────

    public record GymStats(
        int totalCaloriesBurned,
        int workoutSessionsLast30Days,
        int totalMinutesWorkedOut,
        Map<String, Long> workoutsByType
    ) {}
}

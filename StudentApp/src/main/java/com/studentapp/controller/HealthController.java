package com.studentapp.controller;

import com.studentapp.model.*;
import com.studentapp.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = "*")
public class HealthController {

    @Autowired private HealthService healthService;

    // ─── Health Records ──────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/records")
    public List<HealthRecord> getHealthHistory(@PathVariable Long studentId) {
        return healthService.getHealthHistory(studentId);
    }

    @GetMapping("/students/{studentId}/records/latest")
    public ResponseEntity<HealthRecord> getLatestRecord(@PathVariable Long studentId) {
        return healthService.getLatestHealthRecord(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/students/{studentId}/records")
    public ResponseEntity<HealthRecord> logHealth(@PathVariable Long studentId,
                                                   @RequestBody HealthRecord record) {
        if (record.getDate() == null) record.setDate(LocalDate.now());
        return ResponseEntity.ok(healthService.saveHealthRecord(record));
    }

    // ─── Gym Workouts ────────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/workouts")
    public List<GymWorkout> getWorkouts(@PathVariable Long studentId) {
        return healthService.getWorkouts(studentId);
    }

    @GetMapping("/students/{studentId}/workouts/stats")
    public ResponseEntity<HealthService.GymStats> getGymStats(@PathVariable Long studentId) {
        return ResponseEntity.ok(healthService.getGymStats(studentId));
    }

    @GetMapping("/students/{studentId}/workouts/range")
    public List<GymWorkout> getWorkoutsInRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return healthService.getWorkoutsInRange(studentId, start, end);
    }

    @PostMapping("/students/{studentId}/workouts")
    public ResponseEntity<GymWorkout> logWorkout(@PathVariable Long studentId,
                                                  @RequestBody GymWorkout workout) {
        if (workout.getDate() == null) workout.setDate(LocalDate.now());
        return ResponseEntity.ok(healthService.saveWorkout(workout));
    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        healthService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Nutrition ───────────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/nutrition/today")
    public ResponseEntity<Map<String, Object>> getTodayNutrition(@PathVariable Long studentId) {
        List<NutritionLog> logs = healthService.getNutritionByDate(studentId, LocalDate.now());
        Integer totalCal = healthService.getTodayCalories(studentId);
        return ResponseEntity.ok(Map.of(
                "date", LocalDate.now().toString(),
                "meals", logs,
                "totalCalories", totalCal != null ? totalCal : 0
        ));
    }

    @PostMapping("/students/{studentId}/nutrition")
    public ResponseEntity<NutritionLog> logNutrition(@PathVariable Long studentId,
                                                      @RequestBody NutritionLog log) {
        if (log.getDate() == null) log.setDate(LocalDate.now());
        return ResponseEntity.ok(healthService.saveNutrition(log));
    }

    @DeleteMapping("/nutrition/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable Long id) {
        healthService.deleteNutrition(id);
        return ResponseEntity.noContent().build();
    }
}

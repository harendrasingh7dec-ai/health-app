package com.studentapp.controller;

import com.studentapp.model.*;
import com.studentapp.service.AcademicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AcademicController {

    @Autowired private AcademicService academicService;

    // ─── Subjects ────────────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/subjects")
    public List<Subject> getSubjects(@PathVariable Long studentId) {
        return academicService.getSubjectsByStudent(studentId);
    }

    @PostMapping("/students/{studentId}/subjects")
    public ResponseEntity<Subject> addSubject(@PathVariable Long studentId, @RequestBody Subject subject) {
        // Student will be set in service or here via lookup — simplified inline
        return ResponseEntity.ok(academicService.saveSubject(subject));
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject updated) {
        return academicService.findSubjectById(id).map(s -> {
            updated.setId(id);
            updated.setStudent(s.getStudent());
            return ResponseEntity.ok(academicService.saveSubject(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        academicService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Todo ─────────────────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/todos")
    public List<TodoTask> getTodos(@PathVariable Long studentId) {
        return academicService.getTodosByStudent(studentId);
    }

    @PostMapping("/students/{studentId}/todos")
    public ResponseEntity<TodoTask> addTodo(@PathVariable Long studentId, @RequestBody TodoTask task) {
        return ResponseEntity.ok(academicService.saveTodo(task));
    }

    @PatchMapping("/todos/{id}/toggle")
    public ResponseEntity<TodoTask> toggleTodo(@PathVariable Long id) {
        return ResponseEntity.ok(academicService.toggleTodo(id));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        academicService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    // ─── Schedule ─────────────────────────────────────────────────────────────

    @GetMapping("/students/{studentId}/schedule")
    public List<Schedule> getSchedule(@PathVariable Long studentId) {
        return academicService.getScheduleByStudent(studentId);
    }

    @GetMapping("/students/{studentId}/schedule/today")
    public List<Schedule> getTodaySchedule(@PathVariable Long studentId) {
        String today = LocalDate.now().getDayOfWeek().name(); // e.g. "MONDAY"
        return academicService.getTodaySchedule(studentId, today);
    }

    @PostMapping("/students/{studentId}/schedule")
    public ResponseEntity<Schedule> addSchedule(@PathVariable Long studentId, @RequestBody Schedule schedule) {
        return ResponseEntity.ok(academicService.saveSchedule(schedule));
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        academicService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}

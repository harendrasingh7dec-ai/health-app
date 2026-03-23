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
public class AcademicService {

    @Autowired private SubjectRepository subjectRepo;
    @Autowired private TodoTaskRepository todoRepo;
    @Autowired private ScheduleRepository scheduleRepo;
    @Autowired private StudentService studentService;

    // ─── Subjects ───────────────────────────────────────────────────────────

    public Subject saveSubject(Subject subject) {
        Subject saved = subjectRepo.save(subject);
        if (subject.getStudent() != null) {
            studentService.recalculateCgpa(subject.getStudent().getId());
        }
        return saved;
    }

    public List<Subject> getSubjectsByStudent(Long studentId) {
        return subjectRepo.findByStudentId(studentId);
    }

    public Optional<Subject> findSubjectById(Long id) {
        return subjectRepo.findById(id);
    }

    public void deleteSubject(Long id) {
        subjectRepo.findById(id).ifPresent(s -> {
            Long studentId = s.getStudent() != null ? s.getStudent().getId() : null;
            subjectRepo.deleteById(id);
            if (studentId != null) studentService.recalculateCgpa(studentId);
        });
    }

    public double getOverallAttendance(Long studentId) {
        List<Subject> subjects = subjectRepo.findByStudentId(studentId);
        if (subjects.isEmpty()) return 0;
        return subjects.stream()
                .mapToDouble(Subject::getAttendancePercentage)
                .average()
                .orElse(0);
    }

    public double getOverallPercentage(Long studentId) {
        List<Subject> subjects = subjectRepo.findByStudentId(studentId);
        if (subjects.isEmpty()) return 0;
        return subjects.stream()
                .mapToDouble(Subject::getPercentage)
                .average()
                .orElse(0);
    }

    // ─── Todo ────────────────────────────────────────────────────────────────

    public TodoTask saveTodo(TodoTask task) {
        return todoRepo.save(task);
    }

    public List<TodoTask> getTodosByStudent(Long studentId) {
        return todoRepo.findByStudentId(studentId);
    }

    public TodoTask toggleTodo(Long taskId) {
        return todoRepo.findById(taskId).map(t -> {
            t.setCompleted(!t.isCompleted());
            return todoRepo.save(t);
        }).orElseThrow(() -> new RuntimeException("Task not found: " + taskId));
    }

    public void deleteTodo(Long id) {
        todoRepo.deleteById(id);
    }

    // ─── Schedule ────────────────────────────────────────────────────────────

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getScheduleByStudent(Long studentId) {
        return scheduleRepo.findByStudentId(studentId);
    }

    public List<Schedule> getTodaySchedule(Long studentId, String dayOfWeek) {
        return scheduleRepo.findByStudentIdAndDayOfWeek(studentId, dayOfWeek.toUpperCase());
    }

    public void deleteSchedule(Long id) {
        scheduleRepo.deleteById(id);
    }
}

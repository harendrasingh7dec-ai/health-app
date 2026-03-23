package com.studentapp.controller;

import com.studentapp.model.Student;
import com.studentapp.service.AcademicService;
import com.studentapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private AcademicService academicService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updated) {
        return studentService.findById(id).map(s -> {
            updated.setId(id);
            return ResponseEntity.ok(studentService.save(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Dashboard summary: CGPA + attendance + overall marks */
    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Long id) {
        return studentService.findById(id).map(s -> {
            double attendance = academicService.getOverallAttendance(id);
            double overallPct = academicService.getOverallPercentage(id);
            var todos = academicService.getTodosByStudent(id);
            long doneTasks = todos.stream().filter(t -> t.isCompleted()).count();

            return ResponseEntity.ok(Map.of(
                    "student", s,
                    "cgpa", s.getCgpa(),
                    "attendancePercentage", Math.round(attendance * 10.0) / 10.0,
                    "overallPercentage", Math.round(overallPct * 10.0) / 10.0,
                    "todoTotal", todos.size(),
                    "todoDone", doneTasks
            ));
        }).orElse(ResponseEntity.notFound().build());
    }
}

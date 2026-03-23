package com.studentapp.config;

import com.studentapp.model.*;
import com.studentapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private StudentRepository studentRepo;
    @Autowired private SubjectRepository subjectRepo;
    @Autowired private TodoTaskRepository todoRepo;
    @Autowired private ScheduleRepository scheduleRepo;
    @Autowired private HealthRecordRepository healthRepo;
    @Autowired private GymWorkoutRepository gymRepo;
    @Autowired private NutritionLogRepository nutritionRepo;

    @Override
    public void run(String... args) {
        if (studentRepo.count() > 0) return; // don't re-seed

        // ── Student ──────────────────────────────────────────────────────────
        Student s = new Student();
        s.setName("Arjun Sharma");
        s.setRollNumber("CS2021001");
        s.setEmail("arjun.sharma@college.edu");
        s.setPhone("9876543210");
        s.setCourse("B.Tech Computer Science");
        s.setSemester("5th Semester");
        s.setDateOfBirth(LocalDate.of(2003, 4, 15));
        s.setEnrollmentDate(LocalDate.of(2021, 8, 1));
        s.setCgpa(8.2);
        s.setHeightCm(175.0);
        s.setWeightKg(70.0);
        s.setBloodGroup("B+");
        s.setFitnessGoal("MUSCLE_GAIN");
        s = studentRepo.save(s);

        // ── Subjects ──────────────────────────────────────────────────────────
        String[][] subData = {
            {"Mathematics",     "MA301", "Prof. Verma",   "204", "28", "24", "42","50","36","40","10","10", "4"},
            {"Physics",         "PH301", "Prof. Singh",   "Lab B","28","20","35","50","29","40","10","10", "3"},
            {"Chemistry",       "CH301", "Prof. Gupta",   "Lab A","28","25","38","50","33","40","10","10", "3"},
            {"Computer Science","CS301", "Prof. Mehta",   "CS Lab","28","27","44","50","38","40","10","10","4"},
            {"English",         "EN301", "Prof. Kapoor",  "101",  "28","19","32","50","25","40","10","10", "2"},
            {"Economics",       "EC301", "Prof. Malhotra","305",  "28","22","28","50","22","40","8", "10", "3"}
        };
        for (String[] d : subData) {
            Subject sub = new Subject();
            sub.setName(d[0]); sub.setCode(d[1]); sub.setTeacher(d[2]); sub.setRoom(d[3]);
            sub.setTotalClasses(Integer.parseInt(d[4]));
            sub.setAttendedClasses(Integer.parseInt(d[5]));
            sub.setMidtermMarks(Double.parseDouble(d[6]));   sub.setMidtermTotal(Double.parseDouble(d[7]));
            sub.setFinalMarks(Double.parseDouble(d[8]));     sub.setFinalTotal(Double.parseDouble(d[9]));
            sub.setPracticalMarks(Double.parseDouble(d[10]));sub.setPracticalTotal(Double.parseDouble(d[11]));
            sub.setCredits(Double.parseDouble(d[12]));
            sub.setStudent(s);
            subjectRepo.save(sub);
        }

        // ── Todo ──────────────────────────────────────────────────────────────
        String[][] todos = {
            {"Complete Physics assignment Ch.5", "Physics",  "HIGH",   "false"},
            {"Revise integration formulas",      "Maths",    "MEDIUM", "false"},
            {"Submit Economics project",         "Economics","HIGH",   "false"},
            {"Read Chapter 8 Organic Chemistry", "Chemistry","MEDIUM", "true"},
            {"Practice coding problems - arrays","CS",       "HIGH",   "false"},
            {"Prepare English presentation",     "English",  "LOW",    "true"}
        };
        for (String[] t : todos) {
            TodoTask task = new TodoTask();
            task.setTitle(t[0]); task.setSubject(t[1]);
            task.setPriority(t[2]); task.setCompleted(Boolean.parseBoolean(t[3]));
            task.setDueDate(LocalDate.now().plusDays(3));
            task.setStudent(s);
            todoRepo.save(task);
        }

        // ── Schedule ──────────────────────────────────────────────────────────
        Object[][] schData = {
            {"Mathematics",    "MONDAY",    "08:00","09:30","Room 204","Prof. Verma",   "#378ADD"},
            {"Physics",        "MONDAY",    "09:30","11:00","Lab B1",  "Prof. Singh",   "#639922"},
            {"Computer Science","MONDAY",   "13:00","14:30","CS Lab 1","Prof. Mehta",   "#7F77DD"},
            {"English",        "MONDAY",    "14:30","16:00","Room 101","Prof. Kapoor",  "#BA7517"},
            {"Chemistry",      "TUESDAY",   "08:00","09:30","Lab A3",  "Prof. Gupta",   "#1D9E75"},
            {"English",        "TUESDAY",   "09:30","11:00","Room 101","Prof. Kapoor",  "#BA7517"},
            {"Economics",      "TUESDAY",   "13:00","14:30","Room 305","Prof. Malhotra","#D85A30"},
            {"Mathematics",    "WEDNESDAY", "08:00","09:30","Room 204","Prof. Verma",   "#378ADD"},
            {"Economics",      "WEDNESDAY", "09:30","11:00","Room 305","Prof. Malhotra","#D85A30"},
            {"Physics",        "WEDNESDAY", "13:00","14:30","Lab B1",  "Prof. Singh",   "#639922"},
            {"Computer Science","THURSDAY", "08:00","09:30","CS Lab 1","Prof. Mehta",   "#7F77DD"},
            {"Chemistry",      "THURSDAY",  "09:30","11:00","Lab A3",  "Prof. Gupta",   "#1D9E75"},
            {"Mathematics",    "THURSDAY",  "13:00","14:30","Room 204","Prof. Verma",   "#378ADD"},
            {"Physics",        "FRIDAY",    "08:00","09:30","Lab B1",  "Prof. Singh",   "#639922"},
            {"English",        "FRIDAY",    "09:30","11:00","Room 101","Prof. Kapoor",  "#BA7517"},
            {"Economics",      "FRIDAY",    "13:00","14:30","Room 305","Prof. Malhotra","#D85A30"},
            {"Computer Science","FRIDAY",   "14:30","16:00","CS Lab 1","Prof. Mehta",   "#7F77DD"}
        };
        for (Object[] d : schData) {
            Schedule sc = new Schedule();
            sc.setSubjectName((String)d[0]); sc.setDayOfWeek((String)d[1]);
            sc.setStartTime((String)d[2]);   sc.setEndTime((String)d[3]);
            sc.setRoom((String)d[4]);        sc.setTeacher((String)d[5]);
            sc.setColorHex((String)d[6]);    sc.setStudent(s);
            scheduleRepo.save(sc);
        }

        // ── Health Records (last 10 days) ─────────────────────────────────────
        double[] weights = {70.5,70.3,70.1,70.2,70.0,69.9,69.8,69.7,69.8,69.6};
        int[] hr       = {72,70,74,68,71,69,73,70,72,68};
        int[] sleep    = {7,6,8,7,6,7,8,7,6,8};
        double[] water = {2.5,2.0,3.0,2.5,2.0,2.5,3.0,2.5,2.0,2.5};
        String[] moods = {"GOOD","OKAY","GREAT","GOOD","OKAY","GOOD","GREAT","GOOD","OKAY","GREAT"};
        for (int i = 9; i >= 0; i--) {
            HealthRecord hr2 = new HealthRecord();
            hr2.setDate(LocalDate.now().minusDays(i));
            hr2.setWeightKg(weights[9-i]);
            hr2.setHeightCm(175.0);
            hr2.setRestingHeartRate(hr[9-i]);
            hr2.setSystolicBP(118 + (int)(Math.random()*8));
            hr2.setDiastolicBP(76  + (int)(Math.random()*6));
            hr2.setSleepHours(sleep[9-i]);
            hr2.setWaterIntakeLitres(water[9-i]);
            hr2.setStressLevel(3 + (int)(Math.random()*4));
            hr2.setMood(moods[9-i]);
            hr2.setStudent(s);
            healthRepo.save(hr2);
        }

        // ── Gym Workouts (last 14 days) ───────────────────────────────────────
        String[][] gymData = {
            {"CHEST",    "75", "420", "18", "HIGH"},
            {"REST",     "0",  "0",   "0",  "LOW"},
            {"BACK",     "60", "380", "15", "MEDIUM"},
            {"CARDIO",   "45", "350", "0",  "HIGH"},
            {"ARMS",     "50", "300", "16", "MEDIUM"},
            {"LEGS",     "70", "480", "20", "HIGH"},
            {"REST",     "0",  "0",   "0",  "LOW"},
            {"FULL_BODY","80", "520", "24", "HIGH"},
            {"CARDIO",   "40", "320", "0",  "MEDIUM"},
            {"CHEST",    "65", "400", "18", "HIGH"},
            {"REST",     "0",  "0",   "0",  "LOW"},
            {"BACK",     "60", "370", "15", "MEDIUM"},
            {"ARMS",     "50", "290", "16", "MEDIUM"},
            {"LEGS",     "70", "460", "20", "HIGH"}
        };
        for (int i = 0; i < gymData.length; i++) {
            String[] d = gymData[i];
            GymWorkout w = new GymWorkout();
            w.setDate(LocalDate.now().minusDays(gymData.length - 1 - i));
            w.setWorkoutType(d[0]);
            w.setDurationMinutes(Integer.parseInt(d[1]));
            w.setCaloriesBurned(Integer.parseInt(d[2]));
            w.setSetsCompleted(Integer.parseInt(d[3]));
            w.setIntensity(d[4]);
            w.setStudent(s);
            gymRepo.save(w);
        }

        // ── Nutrition (today) ─────────────────────────────────────────────────
        String[][] nutData = {
            {"BREAKFAST","Oats + Milk + Banana",     "420","28","55","8", "6"},
            {"LUNCH",    "Dal Rice + Sabzi + Roti",  "650","22","90","12","8"},
            {"SNACK",    "Protein Bar + Apple",       "280","20","32","5", "3"},
            {"DINNER",   "Paneer + Roti + Salad",     "550","30","45","18","5"}
        };
        for (String[] d : nutData) {
            NutritionLog n = new NutritionLog();
            n.setDate(LocalDate.now());
            n.setMealType(d[0]); n.setFoodItem(d[1]);
            n.setCalories(Integer.parseInt(d[2]));
            n.setProteinGrams(Double.parseDouble(d[3]));
            n.setCarbsGrams(Double.parseDouble(d[4]));
            n.setFatsGrams(Double.parseDouble(d[5]));
            n.setFiberGrams(Double.parseDouble(d[6]));
            n.setStudent(s);
            nutritionRepo.save(n);
        }

        System.out.println("✅ Demo data seeded for student: " + s.getName() + " (ID=" + s.getId() + ")");
    }
}

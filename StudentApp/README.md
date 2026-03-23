# Student Academic & Health Dashboard
### Full-Stack Java Spring Boot Application

---

## Features
- Academic: Marks, Grades, CGPA, Attendance tracking
- To-Do: Task management with subject tagging
- Schedule: Daily & weekly class timetable
- Growth: Monthly progress charts & CGPA trend
- Health Status: BMI, weight, heart rate, sleep, water, mood logs
- Gym Tracker: Workout logging, calorie tracking, split history
- Nutrition: Meal logging, macro tracking, calorie goal progress

---

## Tech Stack
| Layer    | Technology                      |
|----------|---------------------------------|
| Backend  | Java 17 + Spring Boot 3.2       |
| ORM      | Spring Data JPA + Hibernate     |
| Database | H2 (in-memory) — swap for MySQL |
| Security | Spring Security (dev mode open) |
| Frontend | HTML5 + CSS3 + Chart.js         |

---

## Project Structure
```
StudentApp/
├── pom.xml
└── src/main/
    ├── java/com/studentapp/
    │   ├── StudentDashboardApplication.java   ← Main entry point
    │   ├── model/
    │   │   ├── Student.java
    │   │   ├── Subject.java
    │   │   ├── TodoTask.java
    │   │   ├── Schedule.java
    │   │   ├── HealthRecord.java
    │   │   ├── GymWorkout.java
    │   │   └── NutritionLog.java
    │   ├── repository/
    │   │   └── Repositories.java              ← All JPA repos
    │   ├── service/
    │   │   ├── StudentService.java
    │   │   ├── AcademicService.java
    │   │   └── HealthService.java
    │   ├── controller/
    │   │   ├── StudentController.java
    │   │   ├── AcademicController.java
    │   │   └── HealthController.java
    │   └── config/
    │       ├── SecurityConfig.java
    │       └── DataSeeder.java                ← Auto seeds demo data
    └── resources/
        ├── application.properties
        └── static/
            └── index.html                     ← Full frontend
```

---

## How to Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Steps
```bash
# 1. Go to project folder
cd StudentApp

# 2. Build
mvn clean install

# 3. Run
mvn spring-boot:run
```

### Open in browser
```
http://localhost:8080
```

### H2 Database Console (for dev)
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:studentdb
Username: sa
Password: password
```

---

## REST API Endpoints

### Students
| Method | Endpoint                          | Description            |
|--------|-----------------------------------|------------------------|
| GET    | /api/students                     | Get all students       |
| GET    | /api/students/{id}                | Get student by ID      |
| POST   | /api/students                     | Create student         |
| PUT    | /api/students/{id}                | Update student         |
| DELETE | /api/students/{id}                | Delete student         |
| GET    | /api/students/{id}/dashboard      | Dashboard summary      |

### Academics
| Method | Endpoint                              | Description             |
|--------|---------------------------------------|-------------------------|
| GET    | /api/students/{id}/subjects           | All subjects            |
| POST   | /api/students/{id}/subjects           | Add subject             |
| PUT    | /api/subjects/{id}                    | Update subject          |
| DELETE | /api/subjects/{id}                    | Delete subject          |
| GET    | /api/students/{id}/todos              | Get todos               |
| POST   | /api/students/{id}/todos              | Add todo                |
| PATCH  | /api/todos/{id}/toggle                | Toggle complete         |
| DELETE | /api/todos/{id}                       | Delete todo             |
| GET    | /api/students/{id}/schedule           | Full schedule           |
| GET    | /api/students/{id}/schedule/today     | Today's schedule        |
| POST   | /api/students/{id}/schedule           | Add schedule entry      |

### Health & Gym
| Method | Endpoint                                  | Description             |
|--------|-------------------------------------------|-------------------------|
| GET    | /api/health/students/{id}/records         | Health history          |
| GET    | /api/health/students/{id}/records/latest  | Latest health record    |
| POST   | /api/health/students/{id}/records         | Log health data         |
| GET    | /api/health/students/{id}/workouts        | Workout history         |
| GET    | /api/health/students/{id}/workouts/stats  | Gym stats               |
| POST   | /api/health/students/{id}/workouts        | Log workout             |
| DELETE | /api/health/workouts/{id}                 | Delete workout          |
| GET    | /api/health/students/{id}/nutrition/today | Today's nutrition       |
| POST   | /api/health/students/{id}/nutrition       | Log meal                |

---

## Switch to MySQL (Production)

In `application.properties`, replace H2 config with:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Add MySQL dependency to `pom.xml`:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Demo Data
On first startup, `DataSeeder.java` automatically creates:
- 1 student: Arjun Sharma (CS2021001)
- 6 subjects with marks and attendance
- 6 to-do tasks
- 17 schedule entries (Mon–Fri)
- 10 health records (last 10 days)
- 14 gym workout logs
- 4 nutrition entries for today

# Quick Start Guide

## 5-Minute Setup

### Step 1: Install Required Software
- Java 17: https://adoptium.net/
- Maven: https://maven.apache.org/download.cgi
- MySQL: https://dev.mysql.com/downloads/mysql/

### Step 2: Create Database
Open MySQL Workbench and run:
```sql
CREATE DATABASE lms_db;
```

### Step 3: Update Password
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Run Project
```bash
mvn spring-boot:run
```

Server runs at: http://localhost:8080

## Test the API

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "role": "STUDENT"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Get All Courses
```bash
curl http://localhost:8080/api/courses
```

## Frontend Integration

```javascript
// Login example
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    email: 'test@example.com',
    password: 'password123'
  })
})
.then(res => res.json())
.then(data => console.log(data));
```

## All Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | User login |
| GET | /api/courses | Get all courses |
| POST | /api/courses | Create course |
| POST | /api/enroll | Enroll in course |
| GET | /api/enrollments/{userId} | Get user enrollments |

## Troubleshooting

**Can't connect to database?**
- Check MySQL is running
- Verify password in application.properties

**Port 8080 in use?**
- Change port in application.properties: `server.port=8081`

**Maven command not found?**
- Add Maven to system PATH
- Restart terminal after installation

Read full documentation in README.md

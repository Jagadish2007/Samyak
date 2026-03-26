# Learning Management System (LMS) Backend

A simple Spring Boot REST API backend for a Learning Management System. This project is designed for college projects and can be easily downloaded, configured, and run in VS Code.

## Prerequisites

Before running this project, make sure you have the following installed:

1. **Java 17 or higher**
   - Download from: https://adoptium.net/
   - Verify installation: `java -version`

2. **Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **MySQL 8.0 or higher**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - MySQL Workbench recommended for database management

4. **VS Code** (or any IDE)
   - Download from: https://code.visualstudio.com/
   - Recommended extensions: Java Extension Pack, Spring Boot Extension Pack

## Database Setup

1. Open MySQL Workbench and connect to your local MySQL server

2. Create a new database:
   ```sql
   CREATE DATABASE lms_db;
   ```

3. Update the database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/lms_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
   Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password.

## Project Setup

1. **Extract the ZIP file** to your desired location

2. **Open the project in VS Code**
   - File > Open Folder > Select the extracted project folder

3. **Update MySQL Password**
   - Open `src/main/resources/application.properties`
   - Change the password to match your MySQL installation

4. **Install Dependencies**
   - Open terminal in VS Code (Terminal > New Terminal)
   - Run: `mvn clean install`

## Running the Application

### Option 1: Using Maven Command (Recommended)
```bash
mvn spring-boot:run
```

### Option 2: Using Java
```bash
mvn clean package
java -jar target/lms-1.0.0.jar
```

The server will start at: **http://localhost:8080**

## API Endpoints

### Authentication APIs

#### Register New User
- **URL**: `POST http://localhost:8080/api/auth/register`
- **Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "STUDENT"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "message": "Registration successful",
    "user": {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "STUDENT"
    }
  }
  ```

#### Login
- **URL**: `POST http://localhost:8080/api/auth/login`
- **Body**:
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "success": true,
    "message": "Login successful",
    "user": {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "role": "STUDENT"
    }
  }
  ```

### Course APIs

#### Get All Courses
- **URL**: `GET http://localhost:8080/api/courses`
- **Response**:
  ```json
  {
    "success": true,
    "courses": [
      {
        "id": 1,
        "title": "Introduction to Java Programming",
        "description": "Learn the fundamentals of Java programming..."
      }
    ]
  }
  ```

#### Create Course (Admin)
- **URL**: `POST http://localhost:8080/api/courses`
- **Body**:
  ```json
  {
    "title": "Advanced Java",
    "description": "Deep dive into advanced Java concepts"
  }
  ```

### Enrollment APIs

#### Enroll in Course
- **URL**: `POST http://localhost:8080/api/enroll`
- **Body**:
  ```json
  {
    "userId": 1,
    "courseId": 1
  }
  ```

#### Get User Enrollments
- **URL**: `GET http://localhost:8080/api/enrollments/{userId}`
- **Example**: `GET http://localhost:8080/api/enrollments/1`

## Database Tables

The application automatically creates these tables:

### users
| Column   | Type         | Constraints           |
|----------|--------------|-----------------------|
| id       | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| name     | VARCHAR(255) | NOT NULL              |
| email    | VARCHAR(255) | UNIQUE, NOT NULL      |
| password | VARCHAR(255) | NOT NULL              |
| role     | VARCHAR(255) | NOT NULL              |

### courses
| Column      | Type         | Constraints           |
|-------------|--------------|-----------------------|
| id          | BIGINT       | PRIMARY KEY, AUTO_INCREMENT |
| title       | VARCHAR(255) | NOT NULL              |
| description | VARCHAR(1000)| NULL                  |

### enrollments
| Column    | Type   | Constraints           |
|-----------|--------|-----------------------|
| id        | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| user_id   | BIGINT | NOT NULL              |
| course_id | BIGINT | NOT NULL              |

## Frontend Integration

To connect your frontend, use `fetch()` in JavaScript:

```javascript
// Example: Login Request
fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    email: 'john@example.com',
    password: 'password123'
  })
})
.then(response => response.json())
.then(data => {
  console.log('Login response:', data);
  if (data.success) {
    // Store user info
    localStorage.setItem('user', JSON.stringify(data.user));
  }
})
.catch(error => console.error('Error:', error));
```

## Project Structure

```
lms/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── lms/
│   │   │               ├── controller/
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── CourseController.java
│   │   │               │   └── EnrollmentController.java
│   │   │               ├── model/
│   │   │               │   ├── User.java
│   │   │               │   ├── Course.java
│   │   │               │   └── Enrollment.java
│   │   │               ├── repository/
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── CourseRepository.java
│   │   │               │   └── EnrollmentRepository.java
│   │   │               ├── service/
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── CourseService.java
│   │   │               │   └── EnrollmentService.java
│   │   │               ├── DataInitializer.java
│   │   │               └── LmsApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Sample Data

The application automatically adds 3 sample courses on first run:
1. Introduction to Java Programming
2. Web Development with Spring Boot
3. Database Management Systems

## Testing the API

You can test the API using:

1. **Postman** (Recommended)
   - Download from: https://www.postman.com/downloads/
   - Import the endpoints and test each one

2. **Browser** (for GET requests)
   - Visit: http://localhost:8080/api/courses

3. **cURL** (Command line)
   ```bash
   curl -X GET http://localhost:8080/api/courses
   ```

## Troubleshooting

### Issue: "Cannot connect to database"
- Make sure MySQL is running
- Verify database name is `lms_db`
- Check username and password in `application.properties`

### Issue: "Port 8080 already in use"
- Change port in `application.properties`:
  ```properties
  server.port=8081
  ```

### Issue: "Maven not found"
- Add Maven to your system PATH
- Or use Maven Wrapper: `./mvnw spring-boot:run` (on Mac/Linux) or `mvnw.cmd spring-boot:run` (on Windows)

### Issue: "Table doesn't exist"
- Make sure `spring.jpa.hibernate.ddl-auto=update` is set in `application.properties`
- Delete all tables and restart the application to recreate them

## Security Note

This is a simple educational project. For production use:
- Add password encryption (BCrypt)
- Implement JWT authentication
- Add input validation
- Enable Spring Security
- Use environment variables for credentials

## Support

For issues or questions:
1. Check the troubleshooting section
2. Verify all prerequisites are installed
3. Ensure MySQL is running and accessible

## License

This project is created for educational purposes.

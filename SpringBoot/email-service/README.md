# Email Service - Spring Boot Application

A Spring Boot email service application with user authentication, password management, and email functionality.

## Features

- **User Authentication**: JWT-based authentication with Spring Security
- **Password Management**: Change password functionality
- **Email Service**: Send emails using Thymeleaf templates
- **File Management**: Admin-only file upload/download/delete (requires ADMIN role)
- **Security**: CSRF protection, content security policy, and secure session management

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL database (or H2 for development)
- Gmail account (for email functionality)

## Getting Started

### 1. Clone and Navigate

```bash
cd email-service
```

### 2. Database Configuration

The application uses JPA with H2 (for development) or MySQL (for production). The default configuration uses H2 file-based database.

**Current Configuration (H2 - Development):**
```properties
# H2 Database Configuration (file-based)
spring.datasource.url=jdbc:h2:file:~/Downloads/H2DB/EmailService;AUTO_SERVER=TRUE;
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

**For MySQL (Production):**
Uncomment and configure MySQL settings in `src/main/resources/application.properties`:
```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/email_service?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

**Note**: The entity table name is `identities` (as configured in `IdentityDO`).

### 3. Email Configuration

Update email settings in `src/main/resources/application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**For Gmail**: You'll need to use an [App Password](https://support.google.com/accounts/answer/185833) instead of your regular password.

### 4. Build the Application

```bash
./buildMaven.sh
```

Or using Maven directly:

```bash
mvn clean package -DskipTests
```

### 5. Run the Application

```bash
./runMaven.sh
```

Or using Maven directly:

```bash
mvn spring-boot:run
```

The application will start on **port 8080** by default.

## Testing and UI Access

### Access the Application

1. **Open your browser** and navigate to:
   ```
   http://localhost:8080
   ```

2. **You will be redirected to the login page** (Spring Security protection)

3. **Login Page**: `http://localhost:8080/login`
4. **H2 Console**: `http://localhost:8080/h2` (for database management)

### Available Endpoints

| Endpoint                | Method   | Description                 | Authentication |
|-------------------------|----------|-----------------------------|----------------|
| `/`                     | GET      | Root - redirects to login   | Public         |
| `/login`                | GET      | Login page                  | Public         |
| `/authenticate`         | POST     | User authentication         | Public         |
| `/home`                 | GET      | Home page                   | Required       |
| `/user/change-password` | GET/POST | Change password page        | Required       |
| `/file`                 | GET/POST | File management             | ADMIN only     |
| `/delete-file`          | GET/POST | Delete file                 | ADMIN only     |
| `/logout`               | GET      | Logout                      | Required       |
| `/error`                | GET      | Error page                  | Public         |
| `/h2`                   | GET      | H2 Database Console         | Public         |

### Public Resources (No Authentication Required)

- `/webjars/**` - WebJars resources
- `/css/**` - CSS files
- `/js/**` - JavaScript files
- `/images/**` - Image files
- `/signup` - User registration
- `/forgotPassword**` - Password reset
- `/reset-password**` - Password reset confirmation
- `/h2/**` - H2 Database Console (development only)

### Testing the Application

#### 1. Check if Server is Running

```bash
# Check if port 8080 is listening
lsof -i :8080
pkill -f "spring-boot:run"

# Or test with curl
curl -I http://localhost:8080
# Should return HTTP 302 (redirect to login)
```

#### 2. Test Login Page

```bash
curl http://localhost:8080/login
```

#### 3. Browser Testing

1. Open `http://localhost:8080` in your browser
2. You should see the login page
3. **Note**: You need valid user credentials in the database to log in

### Database Setup for Testing

#### H2 Console (Development)

The H2 console is configured and accessible at `/h2`. To access it:

1. **Navigate to**: `http://localhost:8080/h2`
   - The page will automatically redirect to the login page
   - If you see a blank page, ensure JavaScript is enabled in your browser

2. **Connection Settings**:
   - **JDBC URL**: `jdbc:h2:file:~/Downloads/H2DB/EmailService;AUTO_SERVER=TRUE;`
     - For file-based database (current configuration)
     - The database file will be created at `~/Downloads/H2DB/EmailService.mv.db`
   - **Username**: `sa`
   - **Password**: (leave empty)
   - Click **Connect**

3. **Note**: The H2 console uses JavaScript for navigation. If the page appears blank:
   - Ensure JavaScript is enabled in your browser
   - Check browser console for any errors
   - Try a different browser
   - Clear browser cache and cookies

#### Create a Test User

You can insert a test user directly into the database:

```sql
INSERT INTO identities (id, user_name, password, first_name, last_name, email, phone_number, status, created_at, updated_at)
VALUES (
    'test-user-001',
    'testuser',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- password: "password"
    'Test',
    'User',
    'test@example.com',
    '1234567890',
    'ACTIVE',
    NOW(),
    NOW()
);
```

**Default Test Credentials** (if using the above SQL):
- Username: `testuser`
- Password: `password`

**To generate a BCrypt password**, you can use an online tool or add this to a test class:

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String encodedPassword = encoder.encode("password");
System.out.println(encodedPassword);
```

## Configuration

### Application Properties

Key configuration files:
- `src/main/resources/application.properties` - Main configuration
- `src/main/resources/logback.xml` - Logging configuration

### Security Configuration

The application uses a merged security configuration in `WebSecurityConfig.java`:
- JWT token authentication
- Form-based login
- CSRF protection (disabled for H2 console)
- Content Security Policy (allows inline scripts for H2 console compatibility)
- Session management (IF_REQUIRED - allows sessions for H2 console)
- Frame options set to SAMEORIGIN (allows H2 console to load in frames)

**Security Features:**
- Public endpoints: `/`, `/login`, `/signup`, `/h2/**`, static resources
- Authentication required for: `/home`, `/user/change-password`, etc.
- ADMIN role required for: `/file`, `/delete-file`
- HTTPS requirement disabled for development (can be enabled for production)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/rslakra/springbootsamples/emailservice/
│   │       ├── config/security/     # Security configuration
│   │       ├── controller/web/      # Web controllers
│   │       ├── domain/              # JPA entities
│   │       ├── dto/                 # Data transfer objects
│   │       ├── repository/          # JPA repositories
│   │       ├── service/             # Business logic
│   │       └── utils/               # Utility classes
│   └── resources/
│       ├── application.properties   # Application configuration
│       ├── logback.xml              # Logging configuration
│       └── templates/               # Thymeleaf templates
└── test/
    └── java/                        # Test classes
```

## Troubleshooting

### Application Won't Start

1. **Check Java version**:
   ```bash
   java -version  # Should be Java 21+
   ```

2. **Check port availability**:
   ```bash
   lsof -i :8080
   # If port is in use, stop the process or change port in application.properties
   ```

3. **Check database connection**:
   - Ensure MySQL is running
   - Verify database credentials in `application.properties`
   - Check if database exists

4. **Check logs**:
   ```bash
   tail -f /tmp/app-startup.log  # If using runMaven.sh
   ```

### Common Issues

#### "Not a managed type" Error
- Ensure `IdentityDO` has `@Entity` annotation
- Check that the entity is in the correct package
- Verify JPA entity scanning is enabled

#### "Bean not found" Errors
- Ensure all service implementations have `@Service` annotation
- Check that security components have `@Component` annotation
- Verify all required beans are defined

#### "Policy directives cannot be null" Error
- This is fixed in `WebSecurityConfig` with a default CSP policy
- Check `SCPHelper.getAllowList()` returns a valid value

#### "connect ECONNREFUSED ::1:8443" Error
- This error occurs when Spring Security requires HTTPS but the application runs on HTTP
- **Fixed**: HTTPS requirement is disabled for development in `WebSecurityConfig.java`
- For production, uncomment the HTTPS requirement and configure SSL/TLS certificates

#### Login Not Working
- Verify user exists in database
- Check password is BCrypt encoded
- Ensure user status is `ACTIVE`
- Check application logs for authentication errors

#### H2 Console Shows Blank Page
- **Ensure JavaScript is enabled** in your browser (H2 console requires JavaScript)
- Check browser console (F12) for JavaScript errors
- Verify you're accessing `http://localhost:8080/h2` (not `/h2-console`)
- The page should automatically redirect to `login.jsp` - if it doesn't, check browser console
- Try clearing browser cache and cookies
- Try a different browser (Chrome, Firefox, Safari)
- Verify the H2 console is enabled in `application.properties`: `spring.h2.console.enabled=true`

### Stopping the Application

```bash
# Find and kill the process
pkill -f "spring-boot:run"

# Or find the PID and kill it
ps aux | grep "spring-boot:run"
kill <PID>
```

## Development

### Running Tests

```bash
mvn test
```

### Building JAR

```bash
mvn clean package
```

The JAR file will be created in `target/email-service.jar`

### Running JAR

```bash
java -jar target/email-service.jar
```

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.18/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.18/maven-plugin/reference/html/#build-image)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.7.18/reference/htmlsingle/#boot-features-security)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.7.18/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.18/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.18/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Mail](https://docs.spring.io/spring-boot/docs/2.7.18/reference/htmlsingle/#boot-features-email)

### Guides

The following guides illustrate how to use some features concretely:

* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

## License

This is a sample Spring Boot application for educational purposes.

## Author

Rohtash Lakra

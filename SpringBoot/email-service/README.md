# Email Service - Spring Boot Application

A Spring Boot email service application with user authentication, password management, and email functionality.

## Features

- **User Authentication**: Custom session-based authentication with Spring Security and BCrypt password encoding
- **User Registration**: Signup functionality with input validation
- **User Dashboard**: Personalized dashboard page with user information and quick actions
- **Password Management**: Change password functionality for logged-in users
- **Email Service**: Send emails using Thymeleaf templates
- **File Management**: Admin-only file upload/download/delete (requires ADMIN role)
- **UI Components**: 
  - Persistent navbar and footer across all pages using Thymeleaf fragments
  - Responsive design with modern CSS
  - User-friendly dropdown menus
- **Security**: 
  - CSRF protection
  - Content Security Policy
  - Secure session management
  - XSS protection (HTML encoding using Apache Commons Text)
  - Input validation (username, email, integer validation)
  - Session-based authentication (controllers check session validity)

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

2. **Home Page**: Displays features and login/signup options if not logged in
3. **Login Page**: `http://localhost:8080/login`
4. **Dashboard**: `http://localhost:8080/dashboard` (requires login)
5. **H2 Console**: `http://localhost:8080/h2` (for database management)

### Available Endpoints

| Endpoint                | Method   | Description                 | Authentication |
|-------------------------|----------|-----------------------------|----------------|
| `/`                     | GET      | Home page                   | Public         |
| `/login`                | GET      | Login page                  | Public         |
| `/signup`               | GET/POST | User registration           | Public         |
| `/authenticate`         | POST     | User authentication         | Public         |
| `/dashboard`            | GET      | User dashboard              | Session-based* |
| `/change-password`       | GET/POST | Change password page        | Session-based* |
| `/forgotPassword`       | GET/POST | Forgot password page        | Public         |
| `/file`                 | GET/POST | File management             | ADMIN only     |
| `/delete-file`          | GET/POST | Delete file                 | ADMIN only     |
| `/logout`               | GET      | Logout                      | Session-based* |
| `/error`                | GET      | Error page                  | Public         |
| `/h2`                   | GET      | H2 Database Console         | Public         |

\* **Session-based authentication**: These endpoints are public at Spring Security level but require a valid session. Controllers check session and redirect to login if not authenticated.

### Public Resources (No Authentication Required)

**Static Resources:**
- `/webjars/**` - WebJars resources
- `/css/**` - CSS files
- `/js/**` - JavaScript files
- `/images/**` - Image files

**Public Pages:**
- `/` - Home page
- `/about/**` - About pages
- `/contact/**` - Contact pages
- `/error/**` - Error pages

**Authentication Endpoints:**
- `/login` - Login page
- `/signup`, `/signup/**` - User registration
- `/authenticate` - User authentication (POST)
- `/forgotPassword`, `/forgotPassword/**` - Password reset
- `/reset-password/**` - Password reset confirmation

**Development Tools:**
- `/h2/**`, `/h2-console/**` - H2 Database Console (development only)
- `/swagger-ui.html/**` - Swagger UI (if enabled)

**Note**: `/dashboard` and `/change-password` are public at Spring Security level but require session-based authentication (controllers check session and redirect to login if not authenticated).

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

```
BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
String encodedPassword = passwordEncoder.encode("password");
System.out.println(encodedPassword);
```

## Configuration

### Application Properties

Key configuration files:
- `src/main/resources/application.properties` - Main configuration
- `src/main/resources/logback.xml` - Logging configuration

**Note**: `ESAPI.properties` is no longer used (ESAPI has been replaced with SecurityUtils)

### Security Configuration

The application uses a security configuration in `WebSecurityConfig.java`:
- **Custom Authentication**: Custom `/authenticate` endpoint (Spring Security form login disabled)
- **BCrypt Password Encoding**: Passwords are hashed using BCrypt
- **JWT Token Filter**: JWT token support (optional)
- **CSRF Protection**: Enabled with cookie-based token repository (disabled for H2 console)
- **Content Security Policy**: Allows inline scripts for H2 console compatibility
- **Session Management**: IF_REQUIRED policy (allows sessions for H2 console)
- **Frame Options**: SAMEORIGIN (allows H2 console to load in frames)

**Security Features:**
- **Input Validation**: Uses `SecurityUtils` for username, email, and XSS pattern validation
- **Output Encoding**: HTML encoding using Apache Commons Text (replaces ESAPI)
- **Session Management**: Session-based authentication with session validation in controllers
- **Public endpoints**: Organized by category in `PUBLIC_MATCHERS` (static resources, public pages, auth endpoints, dev tools)
- **Session-protected endpoints**: `/dashboard`, `/change-password` (public at Spring Security level, but controllers check session)
- **ADMIN role required**: `/file`, `/delete-file`
- **HTTPS requirement**: Disabled for development (can be enabled for production)
- **Display Name**: Uses first name for user display (falls back to username if first name not available)

**Security Utilities:**
- `SecurityUtils.java`: Provides HTML encoding, input validation, and XSS protection
- Replaces ESAPI with simpler, more maintainable Apache Commons Text-based solution
- See `ESAPI_USAGE.md` for migration details

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/rslakra/springbootsamples/emailservice/
│   │       ├── config/              # Configuration
│   │       │   ├── security/         # Security configuration
│   │       │   │   ├── WebSecurityConfig.java    # Main security config
│   │       │   │   ├── JwtTokenFilter.java        # JWT token filter
│   │       │   │   └── JwtAuthenticationEntryPoint.java
│   │       │   └── SCPHelper.java    # Security helper
│   │       ├── controller/           # Web controllers
│   │       │   ├── HomeController.java           # Home page & dashboard
│   │       │   ├── LoginController.java          # Custom authentication
│   │       │   ├── SignupController.java         # User registration
│   │       │   ├── PasswordController.java       # Password management
│   │       │   └── FileController.java           # File management (admin)
│   │       ├── domain/               # JPA entities
│   │       │   └── user/
│   │       │       ├── IdentityDO.java            # User entity (JPA)
│   │       │       └── UserInfo.java              # User DTO
│   │       ├── dto/                  # Data transfer objects
│   │       │   ├── PasswordChangeRequest.java
│   │       │   └── UserRequest.java
│   │       ├── repository/           # JPA repositories
│   │       │   └── IdentityRepository.java
│   │       ├── service/              # Business logic
│   │       │   ├── AuthUserDetailsService.java
│   │       │   ├── UserService.java
│   │       │   └── UserInfoService.java
│   │       └── utils/                # Utility classes
│   │           ├── SecurityUtils.java            # Security utilities (replaces ESAPI)
│   │           └── AppUtils.java                 # General utilities
│   └── resources/
│       ├── application.properties    # Application configuration
│       ├── logback.xml               # Logging configuration
│       ├── static/                   # Static resources
│       │   └── css/
│       │       ├── navbar-styles.css # Navbar styles
│       │       └── styles.css        # General styles
│       └── templates/                # Thymeleaf templates
│           ├── index.html           # Home page
│           ├── login.html           # Login page
│           ├── signup.html          # Signup page
│           ├── dashboard.html        # User dashboard
│           ├── change-password.html # Change password page
│           ├── forgot-password.html # Forgot password page
│           ├── layout.html          # Base layout template
│           ├── fragments/            # Reusable fragments
│           │   ├── navbar.html      # Navigation bar
│           │   └── footer.html      # Footer
│           └── email/                # Email templates
│               └── resetPassword.html
└── test/
    └── java/                         # Test classes
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
- Check password is BCrypt encoded (passwords are encoded during signup)
- Ensure user status is `ACTIVE`
- Check application logs for authentication errors
- Verify `/authenticate` endpoint is accessible (public endpoint)
- Check that username/email and password are being submitted correctly
- Verify session is being created after successful login (check logs for session ID)

#### Dashboard Shows Blank Page
- Check application logs for template rendering errors
- Verify session attributes are set correctly (check logs for `isValidUser`, `displayName`, etc.)
- Ensure user is logged in (session contains `isValidUser=true`)
- Check browser console (F12) for JavaScript errors
- Verify Thymeleaf template `dashboard.html` exists in `src/main/resources/templates/`

#### Change Password Not Working
- Verify user is logged in (check session)
- Ensure current password is correct
- Check that new password and confirmation match
- Verify `/change-password` endpoint is accessible
- Check application logs for password update errors
- Ensure `UserService.updatePassword()` method is working correctly

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

## Dependencies

### Key Dependencies
- **Spring Boot 3.5.0**: Core framework
- **Spring Security 6.5.0**: Authentication and authorization
- **Spring Data JPA**: Database access
- **Thymeleaf**: Template engine
- **H2 Database**: Development database
- **MySQL Connector**: Production database support
- **Apache Commons Text 1.13.0**: HTML encoding and text utilities (replaces ESAPI)
- **BCrypt**: Password hashing
- **Lombok**: Reduces boilerplate code

### Security Dependencies
- **Spring Security**: Authentication and authorization
- **Apache Commons Text**: HTML encoding for XSS protection
- **BCryptPasswordEncoder**: Password hashing

**Note**: ESAPI has been removed and replaced with `SecurityUtils` (using Apache Commons Text). See `ESAPI_USAGE.md` for details.

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.5.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.5.0/maven-plugin/reference/html/#build-image)
* [Spring Security](https://docs.spring.io/spring-security/reference/)
* [Thymeleaf](https://www.thymeleaf.org/documentation.html)
* [Spring Web](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
* [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [Spring Mail](https://docs.spring.io/spring-framework/reference/integration/email.html)
* [Apache Commons Text](https://commons.apache.org/proper/commons-text/)

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

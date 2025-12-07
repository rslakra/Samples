# JWT Authentication Service

A Spring Boot 3.5.7 application demonstrating JWT (JSON Web Token) based authentication with role-based access control (RBAC).

## Features

- JWT-based authentication
- User registration and login
- Role-based access control (USER, ADMIN, MANAGER, GUEST)
- Spring Security 6.x integration
- H2 in-memory database (configurable to MySQL)
- Swagger/OpenAPI documentation
- RESTful API endpoints

## Prerequisites

- Java 21
- Maven 3.6+
- (Optional) MySQL 8.0+ if using MySQL instead of H2

## Getting Started

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/jwt-authentication`

### Access Points

- **Application**: `http://localhost:8080/jwt-authentication`
- **Swagger UI**: `http://localhost:8080/jwt-authentication/swagger.html`
- **H2 Console**: `http://localhost:8080/jwt-authentication/h2`
- **Health Check**: `http://localhost:8080/jwt-authentication/health`

## Quick Start

### 1. Register a User

```bash
curl -X POST http://localhost:8080/jwt-authentication/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "userName": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "roles": ["user"]
  }'
```

**Expected Response:**
```
IdentityDO registered successfully!
```

### 2. Login and Get JWT Token

```bash
curl -X POST http://localhost:8080/jwt-authentication/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser",
    "password": "password123"
  }'
```

**Expected Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTY...",
  "tokenType": "Bearer"
}
```

**Save the token:**
```bash
TOKEN="your-jwt-token-here"
```

### 3. Access Protected Endpoint

```bash
curl -X GET http://localhost:8080/jwt-authentication/rest/v1/user \
  -H "Authorization: Bearer $TOKEN"
```

**Expected Response:**
```
>>> IdentityDO Contents!
```

## API Endpoints

### Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Welcome page with service information |
| GET | `/health` | Health check endpoint |
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Login and get JWT token |

### Protected Endpoints (require JWT token)

| Method | Endpoint | Required Role | Description |
|--------|----------|---------------|-------------|
| GET | `/rest/v1/user` | USER, ADMIN | Access user content |
| GET | `/rest/v1/manager` | MANAGER, ADMIN | Access manager content |
| GET | `/rest/v1/admin` | ADMIN | Access admin content |
| GET | `/rest/v1/guest` | GUEST | Access guest content |

## Available Roles

- `user` - Regular user role
- `admin` - Administrator role
- `manager` - Manager role
- `guest` - Guest role

## Testing

### Automated Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AuthenticationControllerTest
mvn test -Dtest=ApiAuthenticationControllerTest

# Run tests with coverage
mvn test jacoco:report
```

### Manual Testing with cURL

#### Register User with Admin Role

```bash
curl -X POST http://localhost:8080/jwt-authentication/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Admin User",
    "userName": "admin",
    "email": "admin@example.com",
    "password": "admin123",
    "roles": ["admin"]
  }'
```

#### Test Unauthorized Access

```bash
# Access without token
curl -X GET http://localhost:8080/jwt-authentication/rest/v1/user
# Expected: 401 Unauthorized

# Access with invalid token
curl -X GET http://localhost:8080/jwt-authentication/rest/v1/user \
  -H "Authorization: Bearer invalid-token"
# Expected: 401 Unauthorized

# Access admin endpoint with user role
curl -X GET http://localhost:8080/jwt-authentication/rest/v1/admin \
  -H "Authorization: Bearer $USER_TOKEN"
# Expected: 403 Forbidden
```

### Manual Testing with Postman

1. **Create a Collection** named "JWT Authentication"

2. **Register User Request**
   - Method: POST
   - URL: `http://localhost:8080/jwt-authentication/api/v1/auth/register`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "name": "John Doe",
       "userName": "johndoe",
       "email": "john@example.com",
       "password": "password123",
       "roles": ["user"]
     }
     ```

3. **Login Request**
   - Method: POST
   - URL: `http://localhost:8080/jwt-authentication/api/v1/auth/login`
   - Headers: `Content-Type: application/json`
   - Body (raw JSON):
     ```json
     {
       "userName": "johndoe",
       "password": "password123"
     }
     ```
   - **Postman Script (Tests tab)** - Save token:
     ```javascript
     if (pm.response.code === 200) {
         var jsonData = pm.response.json();
         pm.environment.set("jwt_token", jsonData.accessToken);
         console.log("Token saved:", jsonData.accessToken);
     }
     ```

4. **Access Protected Endpoints**
   - Method: GET
   - URL: `http://localhost:8080/jwt-authentication/rest/v1/user`
   - Headers: `Authorization: Bearer {{jwt_token}}`

## Configuration

### Application Properties

Key configuration in `src/main/resources/application.properties`:

- **Server**: Port 8080, context path `/jwt-authentication`
- **Database**: H2 (file-based) by default, MySQL option available
- **JWT**: Secret key and expiration time (86400 seconds = 24 hours)
- **Swagger**: Available at `/swagger.html`

### Database Configuration

**H2 Database (Default):**
- JDBC URL: `jdbc:h2:file:~/Downloads/H2DB/Authentication`
- Username: `sa`
- Password: (empty)
- Console: `http://localhost:8080/jwt-authentication/h2`

**MySQL (Optional):**
Uncomment and configure MySQL settings in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost/Authentication?createDatabaseIfNotExist=true&serverTimezone=UTC
```

## Test Scenarios

### Complete User Flow
1. Register a new user with role "user"
2. Login with credentials
3. Use JWT token to access protected endpoints
4. Verify role-based access control

### Error Handling
- Duplicate username/email registration
- Invalid login credentials
- Unauthorized access (missing/invalid token)
- Forbidden access (insufficient role)

## Troubleshooting

### Common Issues

1. **401 Unauthorized on Login**
   - Verify user exists in database
   - Check password is correct
   - Ensure roles are properly initialized

2. **403 Forbidden on Protected Endpoints**
   - Verify user has the required role
   - Check JWT token is valid and not expired
   - Ensure token is sent in Authorization header with "Bearer " prefix

3. **Roles Not Found Error**
   - Ensure roles are initialized in database
   - Check RoleType enum matches role names in registration request

4. **Port Already in Use**
   - Change port in `application.properties`: `server.port=8081`
   - Or stop the process using port 8080

5. **Swagger UI Not Accessible**
   - Verify SpringDoc dependency is included in `pom.xml`
   - Check security configuration allows `/swagger.html` path
   - Rebuild the project: `mvn clean install`

## Technology Stack

- **Spring Boot**: 3.5.7
- **Spring Security**: 6.x
- **JWT**: jjwt 0.9.1
- **Database**: H2 (default), MySQL (optional)
- **Documentation**: SpringDoc OpenAPI 2.6.0
- **Java**: 21

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/rslakra/springbootsamples/jwtauthentication/
│   │       ├── controller/          # REST controllers
│   │       ├── security/            # Security configuration
│   │       │   ├── jwt/            # JWT token handling
│   │       │   └── services/       # User details service
│   │       ├── persistence/         # Data layer
│   │       │   ├── model/          # Entity classes
│   │       │   └── repository/    # JPA repositories
│   │       └── payload/            # Request/Response DTOs
│   └── resources/
│       └── application.properties  # Configuration
└── test/
    └── java/                       # Test classes
```

## Additional Resources

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [JWT.io - JWT Debugger](https://jwt.io/) - Decode and verify JWT tokens
- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [Postman Documentation](https://learning.postman.com/docs/)

## License

This is a sample project for educational purposes.


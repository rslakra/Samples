# Spring Boot JSP Service

A Spring Boot 3.5.7 application demonstrating JavaServer Pages (JSP) integration with H2 database, Spring Data JPA, and book management functionality.

## Features

- Spring Boot 3.5.7
- JavaServer Pages (JSP) support with JSTL
- Reusable JSP components (navbar)
- Responsive navigation bar with dropdown menus
- Unified form for adding and editing books
- Modal dialogs for confirmations and notifications
- Toast notifications for success messages
- Icon-based action buttons in tables
- RESTful API endpoints (via JSP controllers)
- H2 file-based database integration
- Spring Data JPA
- H2 Console for database management
- Lombok for cleaner code
- Book management system with JSP views
- Organized CSS architecture (base styles, navbar, table, and modal styles)
- Profile menu with Settings and Logout

## Prerequisites

- Java 21
- Maven 3.6+

## Getting Started

### Build the Project

```bash
mvn clean install
```

Or use the convenience script:
```bash
./buildMaven.sh
```

### Run the Application

```bash
mvn spring-boot:run
```

Or use the convenience script:
```bash
./runMaven.sh
```

The application will start on `http://localhost:8080/springboot-service-jsp`

**Note**: The application uses a context path `/springboot-service-jsp` as configured in `application.properties`.

## Web Pages (JSP)

### Navigation

The application features a top navigation bar available on all pages with:
- **Home**: Link to the home page
- **Book** (dropdown menu):
  - **List Books**: View all books in the library
  - **Add Book**: Add a new book to the library
- **Profile** (dropdown menu, right-aligned):
  - **Settings**: Application and user preferences
  - **Logout**: Return to home page

### Home Page

- **URL**: `http://localhost:8080/springboot-service-jsp/`
- Displays a welcome page with application overview and quick access buttons
- Features section highlighting key capabilities

### Book Management

| Page | URL | Description |
|------|-----|-------------|
| List Books | `/book/viewBooks` | Display all books in a table with edit/delete actions |
| Add/Edit Book | `/book/addBook` or `/book/editBook?isbn={isbn}` | Unified form for adding new books or editing existing ones |
| Error Page | (automatic) | Displays error messages with detailed information |

### Book Operations

- **View All Books**: Navigate to `/book/viewBooks` to see all books in a table format
  - Click ✏️ icon to edit a book
  - Click 🗑️ icon to delete a book (with confirmation modal)
  - Click "+ Add Book" button to add a new book
- **Add Book**: Navigate to `/book/addBook` to add a new book with ISBN, name, and author
- **Edit Book**: Click the edit icon on any book in the list to modify its details
- **Delete Book**: Click the delete icon to remove a book (requires confirmation via modal dialog)
- All pages include the top navigation bar for easy navigation

### Profile Management

- **Settings**: Navigate to `/profile/settings` to view and configure application settings
  - Shows application information (name, version, database)
  - User preferences (notifications, display options)
  - Save button displays success modal on save
- **Logout**: Navigates back to the home page

## User Interface Features

### Modal Dialogs

- **Delete Confirmation**: When deleting a book, a modal dialog appears asking for confirmation
  - Shows book name in the confirmation message
  - Includes "Cancel" and "Delete" buttons
  - Can be closed by clicking outside the modal or the close button
- **Success Notifications**: Settings page shows a success modal when settings are saved

### Toast Notifications

- Success messages appear as toast notifications in the top-right corner
- Auto-dismiss after 3 seconds
- Used for:
  - Book added successfully
  - Book updated successfully
  - Book deleted successfully

### Action Buttons

- Table action buttons use icon-only design for compact display:
  - ✏️ Edit button (blue)
  - 🗑️ Delete button (red)
- Tooltips show full action names on hover
- Buttons are aligned horizontally in a single row

## H2 Database

### Access H2 Console

- **URL**: `http://localhost:8080/springboot-service-jsp/h2`
- **JDBC URL**: `jdbc:h2:file:~/Downloads/H2DB/SpringBootServiceJsp`
- **Username**: `sa`
- **Password**: (empty)

### Database Configuration

The application uses H2 file-based database. The database file is stored at:
```
~/Downloads/H2DB/SpringBootServiceJsp.mv.db
```

The database schema is automatically created/dropped on application startup (`create-drop` strategy). The application will start with an empty database; you can add books through the JSP interface.

## Usage Examples

### Access the Application

1. Start the application using `mvn spring-boot:run`
2. Open your browser and navigate to `http://localhost:8080/springboot-service-jsp/`
3. Use the top navigation bar to:
   - Navigate to the home page
   - Access the Book menu (dropdown) to:
     - View all books
     - Add a new book
   - Access the Profile menu (dropdown) to:
     - View settings
     - Logout

### Add a Book via JSP

1. Navigate to `http://localhost:8080/springboot-service-jsp/book/addBook`
2. Fill in the form:
   - **ISBN**: Unique identifier for the book (e.g., `ISBN-001`)
   - **Name**: Book title (e.g., `Spring Boot in Action`)
   - **Author**: Author name (e.g., `Craig Walls`)
3. Click "Add Book"
4. You'll be redirected to the book list page with a success toast notification

### Edit a Book

1. Navigate to the book list page
2. Click the ✏️ (edit) icon next to the book you want to edit
3. Modify the book details (ISBN is readonly in edit mode)
4. Click "Update Book"
5. You'll be redirected to the book list page with a success toast notification

### Delete a Book

1. Navigate to the book list page
2. Click the 🗑️ (delete) icon next to the book you want to delete
3. A confirmation modal will appear showing the book name
4. Click "Delete" to confirm or "Cancel" to abort
5. Upon deletion, you'll see a success toast notification

### View Books

1. Navigate to `http://localhost:8080/springboot-service-jsp/book/viewBooks`
2. You'll see a table listing all books in the database with their ISBN, name, and author
3. Use the action icons to edit or delete books
4. Click "+ Add Book" button to add a new book

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/rslakra/springbootjsp/
│   │       ├── config/              # Configuration classes
│   │       ├── controller/          # MVC controllers
│   │       ├── dto/                 # Data Transfer Objects
│   │       ├── exception/           # Exception handlers
│   │       ├── repository/          # JPA repositories
│   │       │   └── model/           # Entity classes
│   │       ├── service/             # Business logic
│   │       │   └── impl/            # Service implementations
│   │       └── SpringBootJspApplication.java
│   ├── resources/
│   │   ├── application.properties   # Configuration
│   │   └── static/
│   │       └── css/                  # CSS stylesheets
│   │           ├── styles.css        # Base styles (tables, typography, containers, buttons)
│   │           ├── navbar-styles.css # Navbar, forms, and page content styles
│   │           ├── table-styles.css  # Table-specific styles and action buttons
│   │           └── modal-styles.css  # Modal dialog styles
│   └── webapp/
│       └── WEB-INF/
│           └── jsp/                  # JSP view files
│               ├── navbar.jsp        # Reusable navigation bar component
│               ├── index.jsp         # Home page
│               ├── book/
│               │   ├── listBooks.jsp # Book listing page with actions
│               │   └── editBook.jsp  # Unified form for add/edit
│               ├── profile/
│               │   └── settings.jsp  # Settings page
│               └── errorMessage.jsp  # Generic error page
└── test/
    ├── java/                         # Test classes
    └── resources/
        └── application-test.properties  # Test configuration
```

## Configuration

Key settings in `application.properties`:

- **Server Port**: 8080
- **Context Path**: `/springboot-service-jsp`
- **JSP View Prefix**: `/WEB-INF/jsp/`
- **JSP View Suffix**: `.jsp`
- **Database**: H2 file-based (`~/Downloads/H2DB/SpringBootServiceJsp`)
- **JPA**: Hibernate with `create-drop` strategy
- **H2 Console**: Enabled at `/h2`

**Test Configuration**: Tests use an in-memory H2 database (`jdbc:h2:mem:SpringBootServiceJspTestDB`) configured in `application-test.properties` to avoid file system dependencies during testing.

## CSS Architecture

The application uses a modular CSS architecture:

- **`styles.css`**: Base styles including typography, containers, navigation buttons, features section, and standardized button styles (btn-add, btn-edit, btn-delete)
- **`navbar-styles.css`**: Navbar, dropdown menus, form styles, success/error messages, and page content styles
- **`table-styles.css`**: Table-specific styles, page headers, and compact action buttons for table rows
- **`modal-styles.css`**: Modal overlay, dialog, header, body, footer, and button styles

## Technology Stack

- **Spring Boot**: 3.5.7
- **Java**: 21
- **View Technology**: JavaServer Pages (JSP) with JSTL
- **Database**: H2 (file-based)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Code Generation**: Lombok

## Testing

The project includes comprehensive test coverage:

- **Unit Tests**: `BookServiceImplUnitTest` - Tests service layer with mocked repository
- **Integration Tests**: 
  - `BookServiceIntegrationTest` - Tests service layer with real database
  - `BookControllerIntegrationTest` - Tests controller layer with real database
  - `BookControllerUnitTest` - Tests controller layer with mocked service

Run tests:
```bash
mvn test
```

## Build Scripts

The project includes convenience scripts for easier build and run operations:

- **`buildMaven.sh`**: Builds the project with Maven, automatically calculating version from Git commit count
- **`runMaven.sh`**: Runs the application using Maven (skips tests)

## Reference Documentation

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#data.sql.jpa-and-spring-data)
- [H2 Database](https://www.h2database.com/html/main.html)

## Guides

- [Building a Web Application with Spring Boot](https://spring.io/guides/gs/serving-web-content/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)

## Troubleshooting

### JSP Pages Not Rendering

- Ensure `tomcat-embed-jasper` dependency is included in `pom.xml`
- Verify JSP view prefix and suffix are correctly configured in `application.properties`
- Check that JSP files are located in `src/main/webapp/WEB-INF/jsp/`
- Ensure `navbar.jsp` is included in all pages using `<jsp:include page="navbar.jsp"/>`

### CSS Not Loading

- Verify CSS files are located in `src/main/resources/static/css/`
- Check that all CSS files (`styles.css`, `navbar-styles.css`, `table-styles.css`, `modal-styles.css`) are referenced in JSP files
- Ensure the context path is correctly configured in `application.properties`
- Clear browser cache if styles appear outdated

### Database Connection Issues

- Ensure the H2 database directory exists: `~/Downloads/H2DB/`
- Check that the database file is not locked by another process
- Verify H2 console is accessible at `/h2` endpoint

### Port Already in Use

- Change the server port in `application.properties`: `server.port=8081`
- Or stop the process using port 8080

### Modal Dialogs Not Appearing

- Ensure `modal-styles.css` is included in the JSP page
- Check browser console for JavaScript errors
- Verify modal HTML structure is present in the JSP file

# Author

---

- Rohtash Lakra

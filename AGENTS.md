# AGENTS.md - AI Agent Guidance for untitled5 Project

## Project Overview

**untitled5** is a Java 20 Maven project with REST API integration testing capabilities. It combines a basic application entry point (`Main.java`) with API test automation using RestAssured and TestNG. The project follows a dual-namespace architecture with separate test package structures.

### Technology Stack
- **Build System**: Maven (Java 20 target)
- **Testing Framework**: TestNG 7.10.2 with RestAssured 5.5.6
- **Package Structure**: 
  - Main code: `org.example.*` 
  - Tests: `com.planner.*` (separate namespace)

## Build & Execution Workflows

### Maven Build Commands
```bash
# Clean build
mvn clean compile

# Run tests
mvn test

# Full build with tests
mvn clean package

# Skip tests during build
mvn clean compile -DskipTests
```

### Key Directories
- `src/main/java/org/example/` - Application source code
- `src/test/java/com/planner/` - Test cases (TestNG-based)
- `target/classes/` - Compiled application classes
- `target/test-classes/` - Compiled test classes

## Critical Codebase Patterns

### 1. Test Architecture Pattern
Tests use **RestAssured with TestNG annotations**:
- Locate tests in `src/test/java/com/planner/` 
- Use `@Test` annotation from `org.testng.annotations`
- Import RestAssured statically: `import static io.restassured.RestAssured.*`
- Typical pattern: `given().baseUri(...).auth().oauth2(...).when().get(...).then().assertThat().statusCode(...)`

**Example** (from `TestCase.java`):
```java
@Test
public void test() {
    given().baseUri("http://...")
        .auth().oauth2("JWT_TOKEN")
        .when().get("endpoint")
        .then().log().all().assertThat().statusCode(200);
}
```

### 2. Package Naming Convention
- **Production code**: `org.example.*` namespace
- **Test code**: `com.planner.*` namespace (intentionally different for separation)
- This split suggests potential multi-team contribution or legacy integration

### 3. Main Entry Point
`Main.java` in `org.example` is a simple console application. Any new production features should follow this namespace pattern.

## Integration Points & Dependencies

### External API Integration
- **OAuth2 Token-based Authentication**: Tests authenticate against external APIs using bearer tokens
- **REST Endpoint Structure**: Project targets an external service at `http://69.10.56.98:84/api/services/app/`
- **Token Format**: JWT tokens with custom claims (tenant, user, role information embedded)

### Dependency Management
All dependencies are test-scoped:
- `io.rest-assured:rest-assured:5.5.6` - HTTP API testing
- `org.testng:testng:7.10.2` - Test execution and assertions

## Important Conventions for AI Agents

1. **Java Version**: Target Java 20 (`<maven.compiler.source>20</maven.compiler.source>`)
2. **Encoding**: UTF-8 project encoding specified in pom.xml
3. **Test Execution**: Always run via Maven (`mvn test`) or IDE test runners recognizing TestNG
4. **Class Discovery**: Tests must follow naming pattern `**/src/test/java/**` with `@Test` annotations
5. **Cross-Namespace References**: Production code in `org.example` should not reference `com.planner` classes
6. **Build Artifacts**: Target directory auto-generated; don't edit directly - rebuild with Maven

## Common Development Tasks

| Task | Command | Notes |
|------|---------|-------|
| Compile only | `mvn clean compile` | Fast syntax check |
| Run all tests | `mvn test` | TestNG will discover `@Test` methods |
| Debug tests | Run via IDE debugger with TestNG runner | Set breakpoints in test methods |
| Add dependency | Edit `pom.xml` in `<dependencies>` section, then IDE will refresh | Specify scope (test/compile) |
| Create new test | Add to `src/test/java/com/planner/` with `@Test` method | Use existing `TestCase.java` as template |

## When Adding New Code

- **Production Features**: Place in `org.example.*` under `src/main/java/org/example/`
- **New Tests**: Create in `src/test/java/com/planner/` with TestNG `@Test` methods
- **Verify**: Always run `mvn clean package` before committing
- **API Tests**: Follow RestAssured pattern from `TestCase.java` - given/when/then assertions


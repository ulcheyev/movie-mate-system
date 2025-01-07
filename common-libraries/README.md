# MovieMate Common Libraries

The **MovieMate Common Libraries** provide shared utilities, components, and configurations that can be reused across
all MovieMate services.
These libraries are designed to promote code reusability, reduce duplication, and maintain consistency across the
application ecosystem.

---

## Features

- **Exception Handling**: Common exception classes and error-handling utilities.
- **Security Utilities**: Tools for handling authentication, token validation, and user context.
- **Common Models**: Shared data models used across multiple services.
- **Configurations**: Shared configurations for external integrations and service communication.

---

## How to Include in Your Project

### Maven Dependency

To use the common libraries in project, add the following dependency to  `pom.xml`:

```xml
<dependency>
    <groupId>cz.cvut.moviemate</groupId>
    <artifactId>common-lib</artifactId>
    <version>{specified-version}</version>
</dependency>
```
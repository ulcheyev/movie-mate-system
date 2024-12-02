# MovieMate User Service

The User Service is a core component of the MovieMate application, responsible for 
managing user-related functionality such as authentication, registration, and profile 
management. It communicates with other services through the Discovery Server and API Gateway.

---

## Features

- **User Authentication**: Handles user login and token validation.
- **User Registration**: Manages user sign-up and account creation.
- **Profile Management**: Allows users to update their personal information.
- **Token Validation API**: Provides an endpoint for verifying authentication tokens.

---

## Prerequisites

Before running the User Service, ensure the following prerequisites are met:

1. **Eureka Discovery Server**: Running at the URL specified in the environment variables.
2. **Database**: The service requires a database for storing user data.

---

## Environment Variables
The User Service configuration is controlled using the following environment variables:
```bash
MOVIE_MATE_USER_SERVICE_PORT= # optional (default 7030)
MOVIE_MATE_USER_SERVICE_CONTEXT_PATH= # optional (default /api/v1)
MOVIE_MATE_DISCOVERY_SERVER_URL=

# POSTGRE 
MOVIE_MATE_POSTGRE_URL=
MOVIE_MATE_POSTGRE_USERNAME=
MOVIE_MATE_POSTGRE_PASSWORD=

# REDIS
MOVIE_MATE_REDIS_HOST=
MOVIE_MATE_REDIS_PORT=
MOVIE_MATE_REDIS_PASSWORD=
MOVIE_MATE_USER_SERVICE_DATABASE_INDEX=  # default is 0
MOVIE_MATE_USER_SERVICE_REDIS_TTL= # default is -1

# JWT
MOVIE_MATE_JWT_SECRET_KEY=
MOVIE_MATE_JWT_ACCESS_EXPIRATION_TIME=
MOVIE_MATE_JWT_REFRESH_EXPIRATION_TIME=

# ROOT USER
MOVIE_MATE_USER_SERVICE_ROOT_USERNAME=
MOVIE_MATE_USER_SERVICE_ROOT_EMAIL=
MOVIE_MATE_USER_SERVICE_ROOT_PASSWORD=
```
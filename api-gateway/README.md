# MovieMate API Gateway Service

The API Gateway service is a central point for routing and request validation for the MovieMate application.
It handles dynamic routing services, authentication validation, and load balancing using Eureka.

---

## Features

- **Dynamic Routing**: Routes user and watchlist service requests based on defined base paths.
- **Authentication Validation**: Validates all incoming requests using a custom authentication filter.
- **Load Balancing**: Leverages Eureka to distribute requests across service instances.
- **Custom Path Rewrites**: Supports rewriting original base paths to target paths for flexible routing.

---

## Prerequisites

Before running the API Gateway, ensure the following prerequisites are met:

1. **Eureka Server**: Running at the URL specified in the environment variables.
2. **Backend Services**: Other services are up and registered with Eureka.

---

## Environment Variables

The API Gateway configuration is controlled through the following environment variables (examples included):

```bash
# Gateway server variables
MOVIE_MATE_API_GATEWAY_PORT= #default is 7080

# Discovery server
MOVIE_MATE_DISCOVERY_SERVER_URL=http://moviemate:60600/eureka

# Load balancer: services URI
MOVIE_MATE_USER_SERVICE_URI=lb://USER-SERVICE
MOVIE_MATE_WATCHLIST_SERVICE_URI=lb://WATCHLIST-SERVICE
MOVIE_MATE_ACTIVITY_SERVICE_URI=lb://ACTIVITY

# Necessary services
MOVIE_MATE_USER_SERVICE_AUTH_API_PATH=/users/auth
MOVIE_MATE_USER_SERVICE_VALIDATE_TOKEN_API_PATH=/users/validate-token

# Base path --> target path
MOVIE_MATE_GATEWAY_USERS_ORIGINAL_BASE_PATH=/users
MOVIE_MATE_GATEWAY_USERS_TARGET_BASE_PATH=/users/api

MOVIE_MATE_GATEWAY_WATCHLISTS_ORIGINAL_BASE_PATH=/watchlists
MOVIE_MATE_GATEWAY_WATCHLISTS_TARGET_BASE_PATH=/watchlists/api

MOVIE_MATE_GATEWAY_ACTIVITY_ORIGINAL_BASE_PATH=/activities
MOVIE_MATE_GATEWAY_ACTIVITY_TARGET_BASE_PATH=/activity/api
```


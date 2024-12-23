# MovieMate Movie Service

The Movie Service is a central component of the application, managing both movies and genres.
It provides a comprehensive set of RESTful APIs for creating, updating, deleting, and retrieving movie and genre
information.

---

## Features

- **Caching**: Utilizes Redis caching to optimize performance.

### Genre Management

- **Create New Genres**: Supports adding genres individually or in bulk.
- **Retrieve All Genres**: Fetches a list of all existing genres.
- **Delete Specific Genres**: Allows deletion of genres by their unique ID.

### Movie Management

- **Save New Movies**: Enables saving detailed movie information.
- **Update Existing Movies**: Updates movie details by their unique ID.
- **Retrieve Movies**:
    - Fetch a specific movie by its ID.
    - Retrieve a list of movies with pagination, sorting, and ordering options.
- **Delete Movies**: Deletes movies by their unique ID.

### Bulk Operations

- Supports bulk saving of both movies and genres.

---

## Prerequisites

Before running the Watchlist Service, ensure the following prerequisites are met:

1. **Eureka Discovery Server**: Running at the URL specified in the environment variables.
2. **Databases**: A databases Redis and Mongo instances are required for storing movie data.

## Environment Variables

The Movie Service configuration is controlled using the following environment variables:

```dotenv
MOVIE_MATE_MOVIE_SERVICE_PORT= optional (default 7060)
MOVIE_MATE_MOVIE_SERVICE_CONTEXT_PATH= optional (default /api/v1/movie)

# DATASOURCE
MOVIE_MATE_MONGO_AUTH_DB=
MOVIE_MATE_MONGO_USERNAME=
MOVIE_MATE_MONGO_PASSWORD=
MOVIE_MATE_MOVIE_SERVICE_MONGO_DB=
MOVIE_MATE_MONGO_HOST=
MOVIE_MATE_MONGO_PORT=

# REDIS
MOVIE_MATE_REDIS_HOST=
MOVIE_MATE_REDIS_PORT= optional (default 6379)
MOVIE_MATE_MOVIE_SERVICE_DATABASE_INDEX= optional (default 1)
MOVIE_MATE_REDIS_PASSWORD=
MOVIE_MATE_MOVIE_SERVICE_REDIS_TTL=
```
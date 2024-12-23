# MovieMate Watchlist Service

The Watchlist Service is a core component of the MovieMate application,
responsible for managing user watchlists. It provides APIs for creating, updating,
deleting, and retrieving watchlists, and integrates with other services through the Discovery Server and API Gateway.

---

## Features

- **Watchlist Management**: APIs to create, update, retrieve, and delete user watchlists.
- **Caching**: Utilizes Redis caching to optimize performance.

---

## Prerequisites

Before running the Watchlist Service, ensure the following prerequisites are met:

1. **Eureka Discovery Server**: Running at the URL specified in the environment variables.
2. **Databases**: A databases Redis and Mongo instances are required for storing watchlist data.

## Environment Variables

The Watchlist Service configuration is controlled using the following environment variables:

```bash
# Server properties
MOVIE_MATE_WATCHLIST_SERVICE_PORT= #default is 7020
MOVIE_MATE_DISCOVERY_SERVER_URL=
MOVIE_MATE_WATCHLIST_SERVICE_CONTEXT_PATH=

# REDIS
MOVIE_MATE_REDIS_HOST=
MOVIE_MATE_REDIS_PORT=
MOVIE_MATE_REDIS_PASSWORD=
MOVIE_MATE_WATCHLIST_SERVICE_MONGO_DB_NAME=
MOVIE_MATE_WATCHLIST_SERVICE_DATABASE_INDEX=  # default is 0
MOVIE_MATE_WATCHLIST_SERVICE_CACHE_TTL_IN_HOURS= # default is -1

# MONGO
MOVIE_MATE_MONGO_HOST=
MOVIE_MATE_MONGO_PORT=
MOVIE_MATE_WATCHLIST_SERVICE_MONGO_DB_NAME=
MOVIE_MATE_MONGO_USERNAME=
MOVIE_MATE_MONGO_PASSWORD=

```
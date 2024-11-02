# USER SERVICE

1. Before running the service, create and .env file and add the following values:
```
MOVIE_MATE_USER_SERVICE_PORT= # optional (default 7030)
USER_SERVICE_CONTEXT_PATH= # optional (default /api/v1)

# DATASOURCE
USER_SERVICE_DATASOURCE_URL=
USER_SERVICE_DATASOURCE_USERNAME=
USER_SERVICE_DATASOURCE_PASSWORD=

# REDIS
USER_SERVICE_REDIS_HOST= # optional (default 6379)
USER_SERVICE_REDIS_PORT=
USER_SERVICE_DATABASE_INDEX=  # optional (default 0)
USER_SERVICE_REDIS_PASSWORD=
USER_SERVICE_REDIS_TTL=

# JWT
JWT_SECRET_KEY=
JWT_ACCESS_EXPIRATION_TIME=
JWT_REFRESH_EXPIRATION_TIME=

# ROOT USER
USER_SERVICE_ROOT_USERNAME=
USER_SERVICE_ROOT_EMAIL=
USER_SERVICE_ROOT_PASSWORD=
```
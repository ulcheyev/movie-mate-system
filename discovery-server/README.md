# MovieMate Discovery Server

The Discovery Server is a central registry for managing service 
instances in the MovieMate application. It enables dynamic service discovery, load balancing, and fault tolerance using Eureka.

---

## Features

- **Service Registration**: Backend services register with the Discovery Server for service discovery.
- **Dynamic Service Discovery**: Enables API Gateway and other services to discover backend services dynamically.
- **Load Balancing**: Provides the foundation for load-balanced routing to available service instances.
- **Fault Tolerance**: Ensures resilience by routing requests to healthy service instances.

---

## Environment Variables
The Discovery Server can be configured using the following environment variables:
```bash
MOVIE_MATE_DISCOVERY_SERVER_PORT= #default is 7070
```
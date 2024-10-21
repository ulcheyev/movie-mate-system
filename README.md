# MovieMate
Enhance your movie-watching experience with personalized film suggestions tailored to your individual preferences. This system not only recommends movies but also fosters a community for movie enthusiasts seeking personalized recommendations and social interaction.

## Features
* **Personalized recommendations** - get movie suggestions based on your viewing history and preferences.
* **User Profiles** - create and customize your profile to enhance recommendation accuracy.
* **Social Interaction** - connect with other movie enthusiasts, share reviews, and discuss your favorite films.
* **Administrative Oversight** - administrators monitor user-generated content to ensure compliance with community guidelines.

## Technology Stack
* Programming Language: Java
* Framework: Spring Boot
* Event handling: Kafka
* Build Tool: Maven
* | **Component**                       | **Database System** | **Justification**                                                                                                                                         |
  |-------------------------------------|---------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
  | User accounts and authentication    | PostgreSQL          | Utilizes ACID transactions to ensure secure and consistent handling of user data and authentication processes.                                            |
  | Movie recommendations and social connections | Neo4j           | Allows faster traversal of relationships between users, movies, and interactions, ideal for generating personalized recommendations.                          |
  | Movie metadata                      | MongoDB             | Movie metadata is often semi-structured with flexible and varied attributes like cast, genres, synopses, and reviews.                                        |
  | Ratings, reviews, activity logs     | Cassandra           | Provides high write throughput, enabling efficient real-time storage of user ratings, reviews, and activity logs.                                           |
  | Watchlist management                | Redis + MongoDB     | Ensures fast updates to watchlists for frequent access, while MongoDB handles long-term, persistent storage of watchlist data.                                 |
  | Caching frequently accessed data    | Redis               | Used for caching to enhance performance by reducing access time to frequently used data.                                                                      |

## Getting Started
### Prerequisites
// TODO 

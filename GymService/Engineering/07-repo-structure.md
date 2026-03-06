# 07-repo-structure.md

## Fitlocity — Project Repository Structure (GymService Module)

---

# 1. Overview

This document describes the **repository structure of the GymService backend module** in the Fitlocity platform.

The project is built using:

| Technology      | Purpose                   |
|---------------|----------------------------|
| Java            | Core programming language |
| Spring Boot     | Backend framework         |
| Spring Data JPA | ORM layer                 |
| JWT             | Authentication            |
| Flyway          | Database migration        |
| Maven           | Build tool                |

The project follows a **layered architecture**, separating responsibilities into multiple packages.

### Main Design Goals

- maintainable code
- clear separation of concerns
- scalable project structure
- easy testing and debugging

---

# 2. Root Project Structure

High-level project layout:

```
fitlocity-gymservice
│
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   │
│   └── test
│
├── docs
├── pom.xml
└── README.md
```

| Directory          | Purpose                    |
|-------------------|----------------------------|
| src/main/java      | Application source code    |
| src/main/resources | Configuration files        |
| src/test           | Unit and integration tests |
| docs               | Project documentation      |
| pom.xml            | Maven build configuration  |

---

# 3. Source Code Structure

Main source code location:

```
src/main/java/com/example/Fitlocity/GymService
```

Package structure:

```
GymService
│
├── config
├── controller
├── domain
├── dto
│   ├── request
│   └── response
├── event
├── exception
├── mapper
├── repository
├── security
└── service
```

Each package serves a specific purpose.

---

# 4. Application Entry Point

### File

```
GymServiceApplication.java
```

### Purpose

Main Spring Boot application class.

Responsibilities:

- start Spring Boot application
- enable component scanning
- initialize application context

Example:

```java
@SpringBootApplication
public class GymServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymServiceApplication.class, args);
    }
}
```

---

# 5. Config Package

Directory:

```
config
```

Contains configuration classes.

| File                | Purpose                         |
|---------------------|---------------------------------|
| CorsConfig.java     | Configure CORS policies         |
| JpaConfig.java      | Configure JPA settings          |
| OpenApiConfig.java  | Configure Swagger documentation |
| SecurityConfig.java | Security configuration          |

Responsibilities:

- configure database behavior
- allow frontend API access
- configure OpenAPI documentation

---

# 6. Controller Package

Directory:

```
controller
```

Controllers expose REST APIs.

| Controller                | Responsibility         |
|---------------------------|------------------------|
| GymController             | Manage gym profiles    |
| GymEquipmentController    | Equipment operations   |
| GymOwnerController        | Gym owners             |
| MembershipTierController  | Membership plans       |
| OfferController           | Promotional offers     |
| GymAnalyticsController    | Analytics metrics      |
| GymDashboardController    | Dashboard preferences  |
| GymSubscriptionController | Gym SaaS subscriptions |
| TestController            | Test endpoints         |

Example endpoint:

```java
@PostMapping("/gyms")
public ResponseEntity<GymResponse> createGym(@RequestBody CreateGymRequest request)
```

Controllers:

- receive HTTP requests
- validate input
- call services

---

# 7. Domain Package

Directory:

```
domain
```

Contains JPA entity classes.

| Entity                 | Database Table            |
|------------------------|---------------------------|
| Gym                    | gyms                      |
| GymOwner               | gym_owners                |
| GymEquipment           | gym_equipment             |
| GymMembershipTier      | gym_membership_tiers      |
| GymOffer               | gym_offers                |
| GymLeadAnalytics       | gym_lead_analytics        |
| GymDashboardPreference | gym_dashboard_preferences |
| GymOsSubscription      | gym_os_subscriptions      |

Example:

```java
@Entity
@Table(name="gyms")
public class Gym {

    @Id
    private UUID id;

    private String name;
}
```

---

# 8. DTO Package

Directory:

```
dto
```

Structure:

```
dto
 ├── request
 └── response
```

---

## Request DTOs

Location:

```
dto/request
```

| DTO                              | Purpose                   |
|-----------------------------------|---------------------------|
| CreateGymRequest                 | Create gym                |
| UpdateGymRequest                 | Update gym                |
| CreateEquipmentRequest           | Add equipment             |
| CreateMembershipTierRequest      | Create plan               |
| CreateOfferRequest               | Create offer              |
| CreateSubscriptionRequest        | Create subscription       |
| UpdateDashboardPreferenceRequest | Update dashboard settings |

---

## Response DTOs

Location:

```
dto/response
```

| DTO                         | Purpose            |
|------------------------------|-------------------|
| GymResponse                 | Gym details        |
| EquipmentResponse           | Equipment data     |
| MembershipTierResponse      | Membership info    |
| OfferResponse               | Offer details      |
| AnalyticsResponse           | Gym analytics      |
| SubscriptionResponse        | Subscription data  |
| DashboardPreferenceResponse | Dashboard settings |

---

# 9. Mapper Package

Directory:

```
mapper
```

Converts DTOs to entities and vice versa.

| Mapper               | Purpose                  |
|----------------------|--------------------------|
| GymMapper            | Convert Gym DTOs         |
| EquipmentMapper      | Convert Equipment DTOs   |
| MembershipTierMapper | Convert Membership DTOs  |
| OfferMapper          | Convert Offer DTOs       |
| AnalyticsMapper      | Convert Analytics DTOs   |
| SubscriptionMapper   | Convert Subscription DTOs|

Example mapping:

```
CreateGymRequest → Gym Entity
Gym Entity → GymResponse
```

Benefits:

- separation of API and database models
- better maintainability

---

# 10. Repository Package

Directory:

```
repository
```

Repositories provide database access.

| Repository                | Entity                 |
|----------------------------|------------------------|
| GymRepository             | Gym                    |
| GymOwnerRepository        | GymOwner               |
| GymEquipmentRepository    | GymEquipment           |
| MembershipTierRepository  | GymMembershipTier      |
| OfferRepository           | GymOffer               |
| GymAnalyticsRepository    | GymLeadAnalytics       |
| GymSubscriptionRepository | GymOsSubscription      |
| GymDashboardRepository    | GymDashboardPreference |

Example:

```java
public interface GymRepository extends JpaRepository<Gym, UUID> {
}
```

Responsibilities:

- CRUD operations
- custom queries

---

# 11. Service Package

Directory:

```
service
```

Contains business logic.

| Service                | Purpose                      |
|------------------------|------------------------------|
| GymService             | Manage gyms                  |
| GymEquipmentService    | Manage equipment             |
| MembershipTierService  | Manage plans                 |
| OfferService           | Manage offers                |
| GymAnalyticsService    | Handle analytics             |
| GymSubscriptionService | Manage subscriptions         |
| GymDashboardService    | Manage dashboard preferences |
| GymOwnerService        | Manage gym owners            |

Responsibilities:

- validate business rules
- coordinate repositories
- manage transactions

---

# 12. Event Package

Directory:

```
event
```

Supports event-driven architecture.

| File            | Purpose                  |
|----------------|--------------------------|
| EventPublisher  | Publish domain events    |
| GymCreatedEvent | Trigger when gym created |
| GymUpdatedEvent | Trigger when gym updated |

Example flow:

```
Gym created
 → EventPublisher
 → Analytics service updated
```

---

# 13. Exception Package

Directory:

```
exception
```

Handles error management.

| File                      | Purpose              |
|----------------------------|----------------------|
| BadRequestException       | Invalid request       |
| ResourceNotFoundException | Resource missing      |
| UnauthorizedException     | Authentication error  |
| GlobalExceptionHandler    | Central error handler |

Example:

```java
throw new ResourceNotFoundException("Gym not found");
```

---

# 14. Security Package

Directory:

```
security
```

Handles authentication and authorization.

| File                    | Purpose                      |
|-------------------------|------------------------------|
| JwtAuthenticationFilter | Validate JWT tokens          |
| JwtUtil                 | Generate & validate tokens   |
| SecurityConfig          | Configure Spring Security    |

Security flow:

```
Client request
 → JWT token
 → JwtAuthenticationFilter
 → Access granted
```

---

# 15. Resources Directory

Directory:

```
src/main/resources
```

Structure:

```
resources
│
├── application.yml
├── application.properties
├── db
│   └── migration
├── static
└── templates
```

| File            | Purpose                    |
|----------------|----------------------------|
| application.yml | Application configuration  |
| db/migration    | Flyway database migrations |
| static          | Static files               |
| templates       | HTML templates             |

---

# 16. Database Migration Folder

Location:

```
resources/db/migration
```

Example migration files:

```
V1__create_gym_tables.sql
V2__add_membership_table.sql
```

Flyway ensures:

- version-controlled schema
- repeatable database setup

---

# 17. Build Configuration

File:

```
pom.xml
```

Purpose:

- define dependencies
- manage build lifecycle
- configure plugins

| Dependency              | Purpose               |
|-------------------------|-----------------------|
| Spring Boot Starter Web | REST APIs             |
| Spring Data JPA         | Database ORM          |
| PostgreSQL Driver       | Database connectivity |
| Spring Security         | Security              |
| JWT                     | Authentication        |
| Flyway                  | Database migration    |

---

# 18. Summary

The GymService repository structure follows **clean architecture principles** with clear separation between layers.

Structure highlights:

- controllers manage API endpoints
- services implement business logic
- repositories interact with the database
- DTOs handle API data exchange
- mappers convert data models
- events enable asynchronous communication
- security ensures protected access

This structure ensures the project is:

- maintainable
- scalable
- modular
- production-ready  
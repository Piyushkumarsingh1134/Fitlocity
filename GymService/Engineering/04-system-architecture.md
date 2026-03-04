# 04-system-architecture.md

# Fitlocity Platform — System Architecture

---

# 1. Overview

The system architecture describes the **technical structure of the Fitlocity platform**, including how different services, databases, APIs, and infrastructure components interact.

Fitlocity is designed as a **scalable platform for urban fitness ecosystems** where users can discover gyms, trainers, memberships, and events through structured intelligence.

The architecture follows these core principles:

| Principle        | Description                                    |
| ---------------- | ---------------------------------------------- |
| Modular design   | Each feature domain is separated into services |
| Scalability      | Services can scale independently               |
| Security         | Secure API authentication                      |
| Data consistency | Structured relational database                 |
| Extensibility    | New modules can be added easily                |

The current system uses a **Spring Boot backend architecture** with domain separation.

---

# 2. High-Level System Architecture

The Fitlocity system consists of several layers.

```
Clients
(Web / Mobile Apps)
        │
        ▼
API Gateway
        │
        ▼
Backend Services
        │
        ▼
Database Layer
        │
        ▼
Infrastructure Services
```

### Layer Description

| Layer                | Responsibility               |
| -------------------- | ---------------------------- |
| Client Layer         | Web or mobile applications   |
| API Layer            | Exposes REST APIs            |
| Service Layer        | Business logic               |
| Data Layer           | Database storage             |
| Infrastructure Layer | Security, monitoring, events |

---

# 3. Client Layer

The client layer contains the **user-facing interfaces**.

### Types of Clients

| Client              | Description         |
| ------------------- | ------------------- |
| User Mobile App     | Used by gym users   |
| Gym Owner Dashboard | Used by gym owners  |
| Admin Panel         | Platform management |

### Responsibilities

- Send API requests
- Display data from backend
- Handle user authentication
- Provide user interaction

Example workflow:

```
User searches gym
    │
Mobile App sends request
    │
Backend returns gym list
    │
UI displays results
```

---

# 4. API Gateway Layer

The API Gateway acts as the **entry point to the backend services**.

### Responsibilities

| Responsibility  | Description                        |
| --------------- | ---------------------------------- |
| Request routing | Direct requests to correct service |
| Authentication  | Validate tokens                    |
| Rate limiting   | Prevent abuse                      |
| Logging         | Track API activity                 |

Example request flow:

```
Client Request
     │
API Gateway
     │
Route to GymService
```

Example endpoints:

```
GET /gyms
POST /gyms
GET /gyms/{id}
```

---

# 5. Backend Services Layer

Fitlocity uses **domain-oriented backend services**.

Each service handles a specific domain.

### Core Services

| Service           | Purpose               |
| ----------------- | --------------------- |
| UserService       | User management       |
| GymService        | Gym infrastructure    |
| TrainerService    | Trainer profiles      |
| BookingService    | Trial bookings        |
| MembershipService | Membership management |
| EventService      | Fitness events        |
| CommerceService   | Product marketplace   |
| PaymentService    | Payment processing    |

The **GymService** module belongs to this layer.

---

# 6. GymService Architecture

GymService manages **all gym-related infrastructure**.

### Responsibilities

| Responsibility      | Description                  |
| ------------------- | ---------------------------- |
| Gym profiles        | Manage gym listings          |
| Equipment inventory | Store equipment data         |
| Membership tiers    | Define membership plans      |
| Promotional offers  | Manage discounts             |
| Gym analytics       | Track performance            |
| Gym subscriptions   | SaaS subscription management |
| Dashboard settings  | Owner preferences            |

### Internal Structure

GymService follows a **layered architecture**.

```
Controller Layer
        │
Service Layer
        │
Repository Layer
        │
Database
```

---

# 7. Controller Layer

Controllers expose REST APIs.

### Responsibilities

- Receive HTTP requests
- Validate input
- Call service layer
- Return responses

Example controllers:

| Controller                | Responsibility       |
| ------------------------- | -------------------- |
| GymController             | Manage gym profiles  |
| GymEquipmentController    | Equipment management |
| MembershipTierController  | Membership plans     |
| OfferController           | Promotional offers   |
| GymOwnerController        | Gym owner data       |
| GymAnalyticsController    | Analytics data       |
| GymDashboardController    | Dashboard settings   |
| GymSubscriptionController | SaaS subscriptions   |

Example flow:

```
POST /gyms
      │
GymController
      │
GymService
```

---

# 8. Service Layer

The service layer contains **business logic**.

### Responsibilities

| Responsibility         | Description                 |
| ---------------------- | --------------------------- |
| Data validation        | Verify request data         |
| Business rules         | Enforce platform logic      |
| Transaction management | Ensure database consistency |
| Event publishing       | Trigger system events       |

Example services:

| Service                | Role                 |
| ---------------------- | -------------------- |
| GymService             | Manage gyms          |
| GymEquipmentService    | Equipment operations |
| MembershipTierService  | Membership plans     |
| OfferService           | Promotions           |
| GymAnalyticsService    | Analytics metrics    |
| GymSubscriptionService | SaaS subscriptions   |

---

# 9. Repository Layer

Repositories handle **database operations**.

### Responsibilities

- Query database
- Save entities
- Update records
- Delete records

Repositories extend Spring Data interfaces:

```
JpaRepository<Entity, UUID>
```

Example repositories:

| Repository                | Entity            |
| ------------------------- | ----------------- |
| GymRepository             | Gym               |
| GymEquipmentRepository    | GymEquipment      |
| MembershipTierRepository  | GymMembershipTier |
| OfferRepository           | GymOffer          |
| GymSubscriptionRepository | GymOsSubscription |
| GymAnalyticsRepository    | GymLeadAnalytics  |

---

# 10. DTO Layer

DTOs define **API data structures**.

### Purpose

- Separate API models from database entities
- Prevent overexposing internal data
- Improve API stability

Structure:

```
dto
 ├── request
 └── response
```

Example request DTOs:

| DTO                         | Purpose        |
| --------------------------- | -------------- |
| CreateGymRequest            | Create gym     |
| CreateEquipmentRequest      | Add equipment  |
| CreateMembershipTierRequest | Add membership |

Example response DTOs:

| DTO               | Purpose        |
| ----------------- | -------------- |
| GymResponse       | Gym details    |
| EquipmentResponse | Equipment info |
| AnalyticsResponse | Analytics data |

---

# 11. Mapper Layer

Mappers convert **DTO objects into entities**.

Example:

```
CreateGymRequest → Gym entity
Gym entity → GymResponse
```

Benefits:

- Clean architecture
- Consistent transformations
- Easier maintenance

---

# 12. Domain Layer

The domain layer represents **database entities**.

Example entities:

| Entity                 | Description         |
| ---------------------- | ------------------- |
| Gym                    | Gym information     |
| GymOwner               | Owner business data |
| GymEquipment           | Equipment inventory |
| GymMembershipTier      | Membership plans    |
| GymOffer               | Promotional offers  |
| GymLeadAnalytics       | Analytics data      |
| GymDashboardPreference | Dashboard settings  |
| GymOsSubscription      | SaaS subscriptions  |

---

# 13. Security Architecture

Fitlocity uses **JWT authentication**.

### Security Flow

```
User Login
     │
Generate JWT Token
     │
Client stores token
     │
API request includes token
     │
JwtAuthenticationFilter validates
     │
Access granted
```

Security components:

| Component               | Purpose         |
| ----------------------- | --------------- |
| JwtUtil                 | Generate tokens |
| JwtAuthenticationFilter | Validate token  |
| SecurityConfig          | Access control  |

---

# 14. Event System

Fitlocity uses **event-driven communication**.

Example events:

| Event           | Trigger       |
| --------------- | ------------- |
| GymCreatedEvent | New gym added |
| GymUpdatedEvent | Gym updated   |

Flow:

```
Gym created
     │
EventPublisher
     │
Event listeners update analytics
```

Benefits:

- Loose coupling
- Scalability
- Easier integration

---

# 15. Database Layer

Fitlocity uses a **relational database architecture**.

Key characteristics:

| Feature           | Description                |
| ----------------- | -------------------------- |
| UUID identifiers  | Unique record IDs          |
| Normalized schema | Avoid redundancy           |
| Indexed fields    | Improve search performance |
| JSON columns      | Flexible attributes        |

Example tables:

```
gyms
gym_equipment
gym_membership_tiers
gym_offers
gym_lead_analytics
gym_os_subscriptions
```

---

# 16. Data Flow Examples

### Gym Creation Flow

```
Gym Owner
   │
POST /gyms
   │
GymController
   │
GymService
   │
GymRepository
   │
Database
   │
Publish GymCreatedEvent
```

### Equipment Creation Flow

```
Gym Owner
   │
POST /gyms/{id}/equipment
   │
GymEquipmentController
   │
GymEquipmentService
   │
GymEquipmentRepository
   │
Database
```

---

# 17. Scalability Strategy

| Strategy                  | Description                |
| ------------------------- | -------------------------- |
| Microservices             | Independent services       |
| Stateless APIs            | Horizontal scaling         |
| Database indexing         | Faster queries             |
| Event-driven architecture | Asynchronous communication |

---

# 18. Deployment Architecture

```
Client Apps
      │
Load Balancer
      │
Backend Services
      │
Database Server
```

Infrastructure components:

| Component  | Role                  |
| ---------- | --------------------- |
| Docker     | Containerization      |
| Kubernetes | Orchestration         |
| Redis      | Caching               |
| PostgreSQL | Relational database   |

---

# 19. Monitoring and Observability

| Tool               | Purpose                |
| ------------------ | ---------------------- |
| Application logs   | Debugging              |
| Health endpoints   | Service monitoring     |
| Metrics dashboards | Performance monitoring |

---

# 20. Future System Enhancements

| Feature                  | Description                |
| ------------------------ | -------------------------- |
| AI recommendation engine | Personalized gym matching  |
| Crowd prediction system  | Estimate gym density       |
| Real-time booking system | Instant scheduling         |
| Multi-region deployment  | Global scaling             |
| GraphQL APIs             | Flexible queries           |

---

# 21. Summary

The Fitlocity system architecture provides:

- Scalable service structure
- Modular backend services
- Secure authentication
- Event-driven communication
- Structured data management

The **GymService module acts as the core infrastructure for gym operations**, enabling transparent gym discovery and operational analytics across the platform.
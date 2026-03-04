# 07-sequence-diagrams.md

## Fitlocity Platform — Sequence Diagrams (GymService Focus)

---

# 1. Overview

Sequence diagrams describe **how system components interact over time to complete a specific process**.

In Fitlocity, sequence diagrams illustrate the flow between:

- Client (Frontend)
- API Gateway
- Controllers
- Services
- Repositories
- Database
- Event system

These diagrams help developers understand:

- request flow
- system behavior
- data movement
- service interaction

This document focuses on **core operations in the GymService module**.

---

# 2. System Components

The following components appear in most sequences.

| Component      | Description               |
| -------------- | ------------------------- |
| Client         | Web or mobile frontend    |
| API Gateway    | Entry point for APIs      |
| Controller     | Handles HTTP requests     |
| Service        | Business logic layer      |
| Repository     | Database access layer     |
| Database       | Persistent storage        |
| EventPublisher | Event broadcasting system |

---

# 3. Gym Creation Flow

## Purpose

Allows gym owners to register a new gym on the platform.

## Actors

- Gym Owner
- GymController
- GymService
- GymRepository
- Database
- EventPublisher

---

## Sequence Diagram

```
Gym Owner
   │
   │ POST /gyms
   ▼
API Gateway
   │
   ▼
GymController
   │ validate request
   ▼
GymService
   │ apply business rules
   ▼
GymRepository
   │ save gym
   ▼
Database
   │ record created
   ▼
GymService
   │ publish GymCreatedEvent
   ▼
EventPublisher
   │ notify other services
   ▼
Response returned to client
```

---

## Explanation

| Step | Description                    |
| ---- | ------------------------------ |
| 1    | Gym owner sends create request |
| 2    | API gateway routes request     |
| 3    | Controller validates input     |
| 4    | Service applies business logic |
| 5    | Repository stores gym          |
| 6    | Database inserts record        |
| 7    | Event system publishes event   |
| 8    | Response sent to frontend      |

---

# 4. Equipment Creation Flow

## Purpose

Allows gym owners to add equipment inventory.

## Actors

- Gym Owner
- GymEquipmentController
- GymEquipmentService
- GymEquipmentRepository
- Database

---

## Sequence Diagram

```
Gym Owner
   │
POST /gyms/{id}/equipment
   │
   ▼
GymEquipmentController
   │ validate request
   ▼
GymEquipmentService
   │ business validation
   ▼
GymEquipmentRepository
   │ save equipment
   ▼
Database
   │ equipment stored
   ▼
Response returned
```

---

## Explanation

| Step | Description                 |
| ---- | --------------------------- |
| 1    | Owner sends equipment data  |
| 2    | Controller receives request |
| 3    | Service validates equipment |
| 4    | Repository saves equipment  |
| 5    | Database stores record      |
| 6    | Response sent to client     |

---

# 5. Membership Tier Creation Flow

## Purpose

Allows gyms to create membership plans.

## Actors

- Gym Owner
- MembershipTierController
- MembershipTierService
- MembershipTierRepository
- Database

---

## Sequence Diagram

```
Gym Owner
   │
POST /gyms/{id}/membership-tiers
   │
   ▼
MembershipTierController
   │ validate request
   ▼
MembershipTierService
   │ check pricing rules
   ▼
MembershipTierRepository
   │ save tier
   ▼
Database
   │ tier stored
   ▼
Response returned
```

---

## Explanation

| Step | Description                     |
| ---- | ------------------------------- |
| 1    | Owner submits membership plan   |
| 2    | Controller validates input      |
| 3    | Service verifies business logic |
| 4    | Repository stores tier          |
| 5    | Database saves record           |
| 6    | Client receives confirmation    |

---

# 6. Gym Offer Creation Flow

## Purpose

Allows gyms to create promotional offers.

---

## Sequence Diagram

```
Gym Owner
   │
POST /gyms/{id}/offers
   │
   ▼
OfferController
   │ validate data
   ▼
OfferService
   │ check offer validity
   ▼
OfferRepository
   │ store offer
   ▼
Database
   │ offer saved
   ▼
Response returned
```

---

## Explanation

| Step | Description                     |
| ---- | ------------------------------- |
| 1    | Owner creates promotional offer |
| 2    | Controller validates request    |
| 3    | Service verifies business rules |
| 4    | Repository stores data          |
| 5    | Database saves offer            |

---

# 7. Gym Analytics Retrieval Flow

## Purpose

Allows gym owners to view analytics metrics.

---

## Sequence Diagram

```
Gym Owner
   │
GET /gyms/{id}/analytics
   │
   ▼
GymAnalyticsController
   │
   ▼
GymAnalyticsService
   │
   ▼
GymAnalyticsRepository
   │
   ▼
Database
   │ fetch analytics
   ▼
Service aggregates data
   ▼
Response returned
```

---

## Explanation

| Step | Description                  |
| ---- | ---------------------------- |
| 1    | Owner requests analytics     |
| 2    | Controller processes request |
| 3    | Service aggregates metrics   |
| 4    | Repository queries database  |
| 5    | Data returned to frontend    |

---

# 8. Gym Search Flow

## Purpose

Allows users to search for gyms.

---

## Sequence Diagram

```
User
   │
GET /gyms?city=Chandigarh
   │
   ▼
GymController
   │
   ▼
GymService
   │
   ▼
GymRepository
   │ query database
   ▼
Database
   │ return gym records
   ▼
Service maps results
   ▼
Response returned
```

---

## Explanation

| Step | Description                |
| ---- | -------------------------- |
| 1    | User searches gyms         |
| 2    | Controller handles request |
| 3    | Service processes search   |
| 4    | Repository executes query  |
| 5    | Database returns results   |

---

# 9. Gym Subscription Flow

## Purpose

Allows gym owners to subscribe to Fitlocity Gym OS.

---

## Sequence Diagram

```
Gym Owner
   │
POST /subscriptions
   │
   ▼
GymSubscriptionController
   │
   ▼
GymSubscriptionService
   │
   ▼
GymSubscriptionRepository
   │
   ▼
Database
   │ subscription saved
   ▼
Response returned
```

---

## Explanation

| Step | Description                    |
| ---- | ------------------------------ |
| 1    | Owner selects plan             |
| 2    | Controller receives request    |
| 3    | Service processes subscription |
| 4    | Repository stores data         |
| 5    | Database saves record          |

---

# 10. Dashboard Preferences Update Flow

## Purpose

Allows gym owners to configure dashboard notifications.

---

## Sequence Diagram

```
Gym Owner
   │
PUT /dashboard/preferences
   │
   ▼
GymDashboardController
   │
   ▼
GymDashboardService
   │
   ▼
GymDashboardRepository
   │
   ▼
Database
   │ update preferences
   ▼
Response returned
```

---

# 11. Error Handling Flow

## Example: Gym Not Found

```
Client
  │
GET /gyms/{id}
  │
  ▼
GymController
  │
GymService
  │
GymRepository
  │
Database
  │ returns null
  ▼
Service throws ResourceNotFoundException
  ▼
GlobalExceptionHandler
  ▼
Error response returned
```

Example response:

```json
{
  "success": false,
  "message": "Gym not found"
}
```

---

# 12. Security Flow (JWT)

## Authentication Process

```
Client
   │
Login request
   │
AuthService
   │
Generate JWT
   │
Client stores token
   │
API requests include token
   │
JwtAuthenticationFilter
   │
Token validated
   │
Request processed
```

---

# 13. Event Flow

## Example: Gym Created Event

```
GymService
   │
Create gym
   │
Publish GymCreatedEvent
   │
EventPublisher
   │
Other services receive event
   │
Analytics updated
```

---

# 14. Benefits of Sequence Diagrams

| Benefit              | Description                 |
| -------------------- | --------------------------- |
| Better understanding | Visualize request flow      |
| Debugging            | Identify system bottlenecks |
| Documentation        | Clarify architecture        |
| Development planning | Understand interactions     |

---

# 15. Summary

The sequence diagrams demonstrate how Fitlocity handles:

- gym creation
- equipment management
- membership plans
- promotional offers
- analytics retrieval
- SaaS subscriptions
- dashboard configuration

These flows show how requests move through:

```
Client
 → Controller
 → Service
 → Repository
 → Database
 → Response
```

This layered architecture ensures **clean separation of concerns, maintainability, and scalability**.
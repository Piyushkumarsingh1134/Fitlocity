# 03-information-architecture.md

# Fitlocity Platform — Information Architecture

---

# 1. Overview

Information Architecture (IA) defines how **data, services, modules, and user interactions are structured inside the Fitlocity platform**.

Fitlocity organizes information across multiple domains:

- Users
- Gyms
- Trainers
- Memberships
- Events
- Commerce
- Analytics

The GymService module is part of the **supply infrastructure layer** responsible for gym operations.

The architecture ensures:

- scalable system design
- structured data flow
- modular services
- easy expansion into microservices

---

# 2. Architecture Philosophy

Fitlocity follows these architectural principles:

| Principle                  | Explanation                                    |
| -------------------------- | ---------------------------------------------- |
| Modular architecture       | Each domain is separated into modules          |
| Domain-driven design       | Services are organized around business domains |
| Microservice readiness     | Services can operate independently             |
| API-first approach         | All features exposed through APIs              |
| Event-driven communication | Services communicate using events              |
| Structured data layer      | All entities follow strict schemas             |

---

# 3. High-Level Platform Structure

Fitlocity platform consists of several layers.

| Layer                | Responsibility               |
| -------------------- | ---------------------------- |
| Presentation Layer   | Web and mobile interfaces    |
| API Layer            | REST APIs                    |
| Service Layer        | Business logic               |
| Data Layer           | Database entities            |
| Infrastructure Layer | Security, events, monitoring |

High-level structure:

```
Users / Gym Owners
        │
        ▼
Frontend Applications
        │
        ▼
API Gateway
        │
        ▼
Microservices
        │
        ▼
Database Layer
```

---

# 4. Domain Structure

Fitlocity organizes functionality into **domain modules**.

| Domain            | Description                     |
| ----------------- | ------------------------------- |
| User Domain       | User accounts and preferences   |
| Gym Domain        | Gym listings and infrastructure |
| Trainer Domain    | Trainer profiles                |
| Booking Domain    | Trial and session bookings      |
| Membership Domain | Gym memberships                 |
| Event Domain      | Fitness events                  |
| Commerce Domain   | Supplements and products        |
| Analytics Domain  | Insights and metrics            |

The GymService module belongs to the **Gym Domain**.

---

# 5. Core Domain Modules

---

# 5.1 User Domain

Handles all user-related data.

## Key Entities

| Entity          | Description         |
| --------------- | ------------------- |
| Users           | Platform users      |
| UserPreferences | Fitness preferences |
| Cities          | Available cities    |
| Neighborhoods   | Geographic zones    |

## Responsibilities

- user registration
- profile management
- location tracking
- personalization

## Key Data

```
User
UserPreferences
City
Neighborhood
```

---

# 5.2 Gym Domain

This is the **GymService module you are implementing**.

It manages the supply-side infrastructure.

## Entities

| Entity                 | Description              |
| ---------------------- | ------------------------ |
| Gym                    | Gym profile              |
| GymOwner               | Gym business owner       |
| GymEquipment           | Equipment inventory      |
| GymMembershipTier      | Membership plans         |
| GymOffer               | Promotional offers       |
| GymOsSubscription      | SaaS subscription        |
| GymDashboardPreference | Owner dashboard settings |
| GymLeadAnalytics       | Gym performance metrics  |

## Responsibilities

- gym listing management
- equipment transparency
- pricing transparency
- analytics tracking
- SaaS infrastructure

---

# 5.3 Trainer Domain

Manages personal trainers.

## Entities

| Entity                | Description               |
| --------------------- | ------------------------- |
| Trainer               | Trainer profile           |
| TrainerGymAffiliation | Gym-trainer relationships |
| TrainerSpecialSession | Specialized programs      |

## Responsibilities

- trainer verification
- specialization tagging
- trainer ratings

---

# 5.4 Booking Domain

Handles scheduling.

## Entities

| Entity         | Description                |
| -------------- | -------------------------- |
| TrialBooking   | Gym trial visits           |
| TrainerSession | Personal training sessions |

## Responsibilities

- schedule trials
- track attendance
- convert trials to memberships

---

# 5.5 Membership Domain

Manages memberships.

## Entities

| Entity         | Description            |
| -------------- | ---------------------- |
| UserMembership | Active gym memberships |

## Responsibilities

- membership lifecycle
- renewal management
- conversion tracking

---

# 5.6 Event Domain

Manages fitness events.

## Entities

| Entity            | Description         |
| ----------------- | ------------------- |
| Events            | Fitness events      |
| EventParticipants | Event registrations |

## Responsibilities

- event discovery
- ticket booking
- event management

---

# 5.7 Commerce Domain

Handles e-commerce.

## Entities

| Entity    | Description      |
| --------- | ---------------- |
| Brand     | Product brand    |
| Product   | Fitness products |
| Order     | Orders           |
| OrderItem | Order details    |
| Payment   | Payment records  |

## Responsibilities

- supplement sales
- product inventory
- payment processing

---

# 5.8 Analytics Domain

Tracks platform metrics.

## Entities

| Entity           | Description             |
| ---------------- | ----------------------- |
| GymLeadAnalytics | Gym performance metrics |

## Responsibilities

- traffic analysis
- conversion tracking
- revenue analytics

---

# 6. Data Relationships

Key relationships between domains:

```
User
 ├── UserPreferences
 ├── TrialBooking
 ├── UserMembership
 └── EventParticipation

Gym
 ├── Equipment
 ├── MembershipTiers
 ├── Offers
 ├── Trainers
 ├── Analytics
 └── Events
```

This relationship structure ensures **normalized database design**.

---

# 7. Data Flow

Example: **Gym Discovery Flow**

```
User searches gym
       │
       ▼
API Gateway
       │
       ▼
GymService
       │
       ▼
GymRepository
       │
       ▼
Database
       │
       ▼
Response returned to frontend
```

---

Example: **Gym Creation Flow**

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
GymCreatedEvent
```

---

# 8. API Layer Structure

Each module exposes REST APIs.

| API Group     | Example Endpoint            |
| ------------- | --------------------------- |
| Gyms          | /gyms                       |
| Equipment     | /gyms/{id}/equipment        |
| Membership    | /gyms/{id}/membership-tiers |
| Offers        | /gyms/{id}/offers           |
| Analytics     | /gyms/{id}/analytics        |
| Subscriptions | /subscriptions              |

These APIs follow **RESTful design principles**.

---

# 9. Security Architecture

Authentication is implemented using **JWT tokens**.

Security flow:

```
User Login
   │
Generate JWT
   │
Client sends token
   │
JWT Filter validates
   │
API access granted
```

Security components:

| Component               | Role            |
| ----------------------- | --------------- |
| JwtAuthenticationFilter | Validate tokens |
| JwtUtil                 | Generate tokens |
| SecurityConfig          | Security rules  |

---

# 10. Event Architecture

Fitlocity uses **event-driven architecture**.

Events allow services to communicate asynchronously.

| Event           | Trigger     |
| --------------- | ----------- |
| GymCreatedEvent | Gym created |
| GymUpdatedEvent | Gym updated |

Flow:

```
Gym Created
     │
EventPublisher
     │
Other services consume event
```

This supports **future microservice scaling**.

---

# 11. Scalability Design

Fitlocity is designed to scale using microservices.

| Service           | Responsibility     |
| ----------------- | ------------------ |
| UserService       | User management    |
| GymService        | Gym infrastructure |
| TrainerService    | Trainer ecosystem  |
| BookingService    | Trial bookings     |
| MembershipService | Memberships        |
| EventService      | Events             |
| CommerceService   | Products           |
| PaymentService    | Payments           |

Each service can scale independently.

---

# 12. Data Storage Strategy

Fitlocity uses relational database storage.

| Strategy          | Explanation               |
| ----------------- | ------------------------- |
| Normalized schema | Reduce redundancy         |
| UUID identifiers  | Avoid collisions          |
| JSON fields       | Flexible data storage     |
| Indexing          | Improve query performance |

---

# 13. Search Architecture

Gym search is location-based.

Filters include:

- city
- neighborhood
- equipment
- pricing
- ratings
- trainer availability

Future enhancements:

- AI gym compatibility scoring
- crowd density prediction
- commute time optimization

---

# 14. Monitoring and Observability

Monitoring tools track system performance.

| Component         | Purpose              |
| ----------------- | -------------------- |
| Application logs  | Debugging            |
| Health checks     | Service monitoring   |
| Analytics metrics | Platform performance |

---

# 15. Future Architecture Enhancements

Future improvements include:

| Feature                  | Description               |
| ------------------------ | ------------------------- |
| AI recommendation engine | Match users with gyms     |
| Crowd density prediction | Estimate gym crowd levels |
| Real-time booking system | Live scheduling           |
| Microservice deployment  | Independent services      |
| GraphQL API              | Flexible queries          |

---

# 16. Summary

Fitlocity’s information architecture provides:

- modular domain design
- scalable service structure
- clear data relationships
- event-driven integration
- microservice readiness

The GymService module plays a **critical role in managing the gym ecosystem**, enabling structured discovery, transparency, and operational analytics.
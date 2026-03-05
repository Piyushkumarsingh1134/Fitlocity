# 03-information-architecture.md

## Fitlocity Platform — Information Architecture (Structure)

---

# 1. Overview

Information Architecture (IA) defines how information, data, modules, and services are organized inside the Fitlocity platform.

It provides a structured way to manage:

- system data
- user interactions
- service communication
- platform modules

The information architecture ensures that the platform is:

| Goal         | Description                    |
|--------------|--------------------------------|
| Organized    | Data is structured logically   |
| Scalable     | System can grow easily         |
| Maintainable | Modules remain independent     |
| Accessible   | APIs provide clear data access |

The IA of Fitlocity is domain-driven, meaning the platform is organized around functional domains such as gyms, trainers, bookings, and memberships.

---

# 2. Information Architecture Layers

Fitlocity is structured using multiple architectural layers.

```
Client Layer
        │
API Layer
        │
Service Layer
        │
Domain Layer
        │
Data Layer
```

Each layer has a specific role.

| Layer         | Purpose               |
|--------------|-----------------------|
| Client Layer  | User interfaces       |
| API Layer     | Exposes platform APIs |
| Service Layer | Business logic        |
| Domain Layer  | Entities and models   |
| Data Layer    | Database storage      |

---

# 3. Client Layer Structure

The client layer represents applications interacting with the platform.

## Client Types

| Client              | Description                 |
|--------------------|-----------------------------|
| User Mobile App     | Fitness users discover gyms |
| Gym Owner Dashboard | Gym management interface    |
| Admin Panel         | Platform administration     |

## Responsibilities

The client layer handles:

- user input
- displaying gym listings
- viewing membership plans
- managing gym data

Example user interaction:

```
User searches gyms
        │
Frontend sends API request
        │
Backend returns gym data
```

---

# 4. API Layer Structure

The API layer exposes REST endpoints for platform functionality.

## Responsibilities

| Responsibility         | Description             |
|------------------------|-------------------------|
| Handle client requests | Receive HTTP requests   |
| Route requests         | Send to correct service |
| Format responses       | Return structured data  |
| Enforce security       | Validate authentication |

## Example API Groups

| API Group       | Example Endpoint            |
|----------------|-----------------------------|
| Gym APIs        | /gyms                       |
| Equipment APIs  | /gyms/{id}/equipment        |
| Membership APIs | /gyms/{id}/membership-tiers |
| Offer APIs      | /gyms/{id}/offers           |
| Analytics APIs  | /gyms/{id}/analytics        |

---

# 5. Domain Architecture

The Fitlocity platform organizes information into functional domains.

Domains represent major business areas.

| Domain            | Description             |
|------------------|-------------------------|
| User Domain       | Manage platform users   |
| Gym Domain        | Manage gyms             |
| Trainer Domain    | Manage trainers         |
| Booking Domain    | Manage trials           |
| Membership Domain | Manage memberships      |
| Event Domain      | Manage fitness events   |
| Commerce Domain   | Manage fitness products |
| Analytics Domain  | Platform analytics      |

Your work focuses mainly on the Gym Domain.

---

# 6. Gym Domain Information Structure

The Gym domain stores all gym-related information.

## Core Entities

| Entity                 | Description         |
|------------------------|---------------------|
| GymOwner               | Business owners     |
| Gym                    | Gym profiles        |
| GymEquipment           | Equipment inventory |
| GymMembershipTier      | Membership plans    |
| GymOffer               | Promotional offers  |
| GymLeadAnalytics       | Performance metrics |
| GymDashboardPreference | Owner settings      |
| GymOsSubscription      | SaaS subscriptions  |

These entities form the data backbone of GymService.

---

# 7. Gym Information Hierarchy

The structure of gym-related information follows a hierarchical model.

```
GymOwner
   │
   └── Gyms
           │
           ├── Equipment
           ├── Membership Tiers
           ├── Offers
           ├── Analytics
           ├── Dashboard Preferences
           └── Subscriptions
```

Explanation:

- One gym owner can manage multiple gyms.
- Each gym contains operational data such as equipment and membership plans.

---

# 8. Data Flow Structure

The platform follows a structured information flow.

## Example Gym Search Flow

```
User Search Request
        │
API Gateway
        │
GymService
        │
Database Query
        │
Return Gym List
```

## Example Gym Creation Flow

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
```

---

# 9. Information Storage Structure

Fitlocity stores data in a relational database.

## Key Tables for GymService

| Table                     | Description         |
|--------------------------|---------------------|
| gym_owners                | Gym business owners |
| gyms                      | Gym profiles        |
| gym_equipment             | Equipment inventory |
| gym_membership_tiers      | Membership plans    |
| gym_offers                | Promotional offers  |
| gym_lead_analytics        | Analytics metrics   |
| gym_dashboard_preferences | Dashboard settings  |
| gym_os_subscriptions      | SaaS subscriptions  |

Each table stores a specific type of information.

---

# 10. Content Organization

The system organizes information into logical content groups.

## Gym Information

| Data      | Description  |
|-----------|--------------|
| Name      | Gym name     |
| Address   | Gym location |
| Amenities | Facilities   |
| Rating    | User rating  |

---

## Equipment Information

| Data           | Description      |
|---------------|------------------|
| Equipment name | Machine name     |
| Brand          | Manufacturer     |
| Quantity       | Units available  |
| Condition      | Equipment status |

---

## Membership Information

| Data          | Description       |
|--------------|-------------------|
| Plan name     | Membership type   |
| Duration      | Plan length       |
| Monthly price | Cost              |
| Joining fee   | Registration cost |

---

# 11. Search Information Architecture

The platform supports structured gym search.

## Search Filters

| Filter      | Description        |
|------------|--------------------|
| City        | User location      |
| Gym type    | Strength, CrossFit |
| Price range | Membership budget  |
| Equipment   | Required machines  |
| Amenities   | Sauna, parking     |

Search results are ranked by:

- proximity
- rating
- popularity
- equipment availability

---

# 12. Navigation Structure

Navigation defines how users move through the platform.

## Example User Navigation

```
Home
   │
Gym Search
   │
Gym Profile
   │
Membership Plans
   │
Book Trial
```

## Example Gym Owner Navigation

```
Owner Dashboard
      │
Manage Gyms
      │
Equipment
      │
Membership Plans
      │
Analytics
```

---

# 13. Metadata Structure

Metadata improves data organization.

## Examples

| Metadata   | Description          |
|-----------|----------------------|
| created_at | Record creation time |
| updated_at | Last modification    |
| status     | Active / inactive    |
| tags       | Categorization       |

Metadata helps with:

- analytics
- filtering
- auditing

---

# 14. Event Information Flow

Fitlocity uses event-driven information flow.

Example event:

```
GymCreatedEvent
```

Flow:

```
Gym created
     │
EventPublisher
     │
Analytics system updates
```

Benefits:

- decoupled services
- asynchronous processing
- better scalability

---

# 15. Security Information Architecture

Security controls access to information.

## Security Components

| Component          | Role               |
|-------------------|--------------------|
| JWT authentication | User verification  |
| Role-based access  | Permission control |
| API security       | Protect endpoints  |

## Security Flow

```
User Login
    │
Generate JWT
    │
Client sends token
    │
API validates token
```

---

# 16. Scalability of Information Architecture

The architecture supports future system growth.

## Scalability Strategies

| Strategy            | Description          |
|--------------------|----------------------|
| Microservices       | Independent services |
| Caching             | Reduce database load |
| Database indexing   | Faster queries       |
| Event-driven design | Async processing     |

---

# 17. Future Information Architecture Enhancements

Future improvements include:

| Feature                  | Description              |
|--------------------------|--------------------------|
| AI recommendation engine | Personalized gym ranking |
| Crowd prediction         | Real-time gym density    |
| Trainer discovery        | Trainer marketplace      |
| Fitness event platform   | Event management         |

---

# 18. Summary

The Fitlocity information architecture defines how data, services, and modules are structured across the platform.

The GymService module forms the core supply-side infrastructure, managing all gym-related information including:

- gym profiles
- equipment inventory
- membership plans
- promotional offers
- analytics metrics

This structured architecture ensures the platform is scalable, organized, and ready for large-scale deployment.
# product-requirements.md

# Fitlocity — Gym Service Module

---

# 1. Overview

## Product Name

Fitlocity Gym Service

## Module Purpose

The Gym Service module manages all **gym-related infrastructure within the Fitlocity platform**, including:

- Gym listings
- Equipment transparency
- Membership plans
- Promotional offers
- Gym analytics
- Gym SaaS subscriptions
- Gym owner management
- Gym dashboard preferences

This module serves as the **core supply-side infrastructure** of the Fitlocity ecosystem, enabling gyms to digitize their presence and operations.

The Gym Service acts as the **bridge between gyms and platform users**, providing structured data for gym discovery and engagement.

---

# 2. Objectives

## Primary Goals

| Goal                         | Description                                                        |
| ---------------------------- | ------------------------------------------------------------------ |
| Digitize gym listings        | Enable gyms to create structured profiles                          |
| Improve transparency         | Provide clear information about facilities, equipment, and pricing |
| Enable membership management | Define membership tiers and pricing                                |
| Enable gym promotions        | Manage promotional offers                                          |
| Provide analytics insights   | Show gyms performance data                                         |
| Enable SaaS infrastructure   | Allow gyms to subscribe to Fitlocity Gym OS                        |

---

# 3. Stakeholders

| Stakeholder        | Role                                      |
| ------------------ | ----------------------------------------- |
| Platform Users     | Discover gyms and memberships             |
| Gym Owners         | Manage gyms, offers, equipment            |
| Fitlocity Admin    | Monitor analytics and platform operations |
| Trainers           | Associate with gyms                       |
| Corporate Partners | Use gym data for wellness programs        |

---

# 4. User Roles

## 1. Gym Owner

Gym owners manage gym operations.

### Capabilities

- Create gym profile
- Manage equipment
- Configure memberships
- Create offers
- Track analytics
- Manage subscriptions

---

## 2. Platform Admin

### Capabilities

- Verify gyms
- Monitor platform analytics
- Approve subscriptions
- Manage listings

---

## 3. Platform User

### Capabilities

- View gyms
- Compare memberships
- View equipment transparency

---

# 5. System Architecture

The Gym Service is implemented using **Spring Boot microservice architecture**.

```
Controller Layer
↓
Service Layer
↓
Repository Layer
↓
Database
```

Supporting layers include:

- DTO layer
- Mapper layer
- Security layer
- Exception handling
- Event publishing

---

# 6. Core Modules

---

## 6.1 Gym Management

### Description

Allows gym owners to create and manage gyms.

### Entity

Gym

### Key Fields

| Field            | Description         |
| ---------------- | ------------------- |
| id               | Unique identifier   |
| name             | Gym name            |
| description      | Gym description     |
| neighborhood_id  | Gym location        |
| address          | Full address        |
| contact_phone    | Contact number      |
| contact_email    | Contact email       |
| established_year | Gym founding year   |
| gym_type         | Gym category        |
| total_sqft       | Gym size            |
| floors_count     | Number of floors    |
| amenities        | Available amenities |
| images           | Gym images          |
| rating           | Average rating      |

### APIs

| Method | Endpoint   | Description |
| ------ | ---------- | ----------- |
| POST   | /gyms      | Create gym  |
| GET    | /gyms/{id} | Get gym     |
| PUT    | /gyms/{id} | Update gym  |
| DELETE | /gyms/{id} | Delete gym  |
| GET    | /gyms      | List gyms   |

---

## 6.2 Gym Owner Management

### Description

Manages gym owners and business details.

### Entity

GymOwner

### Key Fields

| Field         | Description         |
| ------------- | ------------------- |
| business_name | Registered business |
| owner_name    | Owner name          |
| owner_phone   | Phone number        |
| owner_email   | Email               |
| gst_number    | GST number          |
| pan_number    | PAN number          |

### APIs

| Method | Endpoint     |
| ------ | ------------ |
| POST   | /owners      |
| GET    | /owners/{id} |
| PUT    | /owners/{id} |

---

## 6.3 Equipment Management

### Description

Allows gyms to publish detailed equipment inventory.

### Entity

GymEquipment

### Key Fields

| Field                 | Description          |
| --------------------- | -------------------- |
| equipment_name        | Equipment name       |
| brand                 | Equipment brand      |
| model                 | Model                |
| quantity              | Quantity             |
| condition             | Equipment condition  |
| last_maintenance_date | Last maintenance     |
| next_maintenance_due  | Maintenance schedule |

### APIs

| Method | Endpoint                |
| ------ | ----------------------- |
| POST   | /gyms/{gymId}/equipment |
| GET    | /gyms/{gymId}/equipment |
| PUT    | /equipment/{id}         |
| DELETE | /equipment/{id}         |

---

## 6.4 Membership Tier Management

### Description

Defines membership plans offered by gyms.

### Entity

GymMembershipTier

### Key Fields

| Field              | Description        |
| ------------------ | ------------------ |
| name               | Plan name          |
| duration_months    | Duration           |
| price_monthly      | Monthly cost       |
| price_total        | Total price        |
| joining_fee        | Registration fee   |
| freeze_policy_days | Freeze policy      |
| amenities_included | Included amenities |

### APIs

| Method | Endpoint                       |
| ------ | ------------------------------ |
| POST   | /gyms/{gymId}/membership-tiers |
| GET    | /gyms/{gymId}/membership-tiers |
| PUT    | /membership-tiers/{id}         |
| DELETE | /membership-tiers/{id}         |

---

## 6.5 Gym Offers

### Description

Promotional offers for memberships.

### Entity

GymOffer

### Key Fields

| Field           | Description         |
| --------------- | ------------------- |
| title           | Offer title         |
| discount_type   | Percentage or fixed |
| discount_value  | Discount amount     |
| valid_from      | Start date          |
| valid_to        | End date            |
| max_redemptions | Maximum uses        |

### APIs

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | /gyms/{gymId}/offers |
| GET    | /gyms/{gymId}/offers |
| PUT    | /offers/{id}         |
| DELETE | /offers/{id}         |

---

## 6.6 Gym OS Subscription

### Description

Gym owners subscribe to Fitlocity SaaS platform.

### Entity

GymOsSubscription

### Key Fields

| Field            | Description        |
| ---------------- | ------------------ |
| plan_type        | Plan name          |
| features_enabled | Enabled features   |
| price_monthly    | Monthly price      |
| start_date       | Subscription start |
| end_date         | Subscription end   |
| status           | Active or expired  |

### APIs

| Method | Endpoint               |
| ------ | ---------------------- |
| POST   | /subscriptions         |
| GET    | /subscriptions/{gymId} |

---

## 6.7 Gym Analytics

### Description

Provides performance analytics to gym owners.

### Entity

GymLeadAnalytics

### Metrics

| Metric                 | Description            |
| ---------------------- | ---------------------- |
| profile_views          | Gym profile views      |
| trial_bookings         | Trial bookings         |
| trial_conversions      | Membership conversions |
| chat_queries           | Customer inquiries     |
| revenue_from_fitlocity | Revenue generated      |

### APIs

| Method | Endpoint                |
| ------ | ----------------------- |
| GET    | /gyms/{gymId}/analytics |

---

## 6.8 Gym Dashboard Preferences

### Description

Allows gym owners to customize dashboard notifications.

### Entity

GymDashboardPreference

### Settings

| Field                  | Description          |
| ---------------------- | -------------------- |
| notification_email     | Email alerts         |
| notification_whatsapp  | WhatsApp alerts      |
| alert_on_new_lead      | Lead alerts          |
| alert_on_trial_booking | Trial booking alerts |
| weekly_report          | Weekly analytics     |

### APIs

| Method | Endpoint               |
| ------ | ---------------------- |
| GET    | /dashboard/preferences |
| PUT    | /dashboard/preferences |

---

# 7. Security

Authentication uses **JWT-based authentication**.

Security components include:

- JwtAuthenticationFilter
- JwtUtil
- SecurityConfig

Protected endpoints require authentication.

---

# 8. Events

The system uses event-driven architecture.

## Events

| Event           | Trigger          |
| --------------- | ---------------- |
| GymCreatedEvent | When gym created |
| GymUpdatedEvent | When gym updated |

These events are published using:

```
EventPublisher
```

---

# 9. Non-Functional Requirements

## Performance

- API response time < 200ms
- Support 1000 concurrent requests

## Security

- JWT authentication
- Input validation
- Role-based access

## Scalability

- Microservice ready
- Stateless API design

---

# 10. Future Enhancements

| Feature                    | Description                    |
| -------------------------- | ------------------------------ |
| AI gym compatibility score | Match users with gyms          |
| Crowd density prediction   | Real-time gym crowd estimation |
| Trainer booking            | Book personal training         |
| Trial booking              | Schedule trial sessions        |
| Gym event hosting          | Fitness event management       |

---

# 11. Success Metrics

| Metric                   | Target            |
| ------------------------ | ----------------- |
| Gym onboarding           | 100 gyms per city |
| Trial booking conversion | >20%              |
| Membership purchase      | >15%              |
| Average gym revenue      | Increasing monthly |

---
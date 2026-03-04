# 05-database-architecture.md

## Fitlocity Platform — Database Architecture (GymService Focus)

---

# 1. Overview

The database architecture defines how **data is structured, stored, and accessed in the Fitlocity platform**.

The platform uses a **relational database architecture** designed for:

- structured data management
- strong relational integrity
- scalable queries
- efficient analytics

This document focuses primarily on the **GymService database structure**, which manages the gym ecosystem.

The GymService database schema includes entities related to:

- gyms
- gym owners
- equipment
- membership plans
- promotional offers
- analytics
- SaaS subscriptions
- dashboard preferences

These tables form the **core supply-side infrastructure of the platform**.

---

# 2. Database Technology

Fitlocity uses a relational database.

Recommended stack:

| Component      | Technology      |
| -------------- | --------------- |
| Database       | PostgreSQL      |
| ORM            | Spring Data JPA |
| Migration Tool | Flyway          |
| ID Type        | UUID            |

Reasons for PostgreSQL:

- strong relational support
- JSON column support
- advanced indexing
- geolocation queries

---

# 3. Database Design Principles

The schema follows these design principles.

| Principle             | Description                       |
| --------------------- | --------------------------------- |
| Normalization         | Reduce redundancy                 |
| Referential integrity | Foreign keys maintain consistency |
| Scalability           | Support millions of records       |
| Extensibility         | Allow future features             |
| Performance           | Optimized indexing                |

---

# 4. GymService Database Overview

GymService includes the following core tables.

| Table                     | Purpose                   |
| ------------------------- | ------------------------- |
| gym_owners                | Business owners           |
| gyms                      | Gym listings              |
| gym_equipment             | Equipment inventory       |
| gym_membership_tiers      | Membership plans          |
| gym_offers                | Promotional offers        |
| gym_lead_analytics        | Gym performance analytics |
| gym_dashboard_preferences | Owner dashboard settings  |
| gym_os_subscriptions      | SaaS subscriptions        |

These tables are interconnected through **foreign key relationships**.

---

# 5. Entity Relationship Overview

High-level relationship structure:

```
GymOwner
│
└── Gyms
     │
     ├── GymEquipment
     ├── GymMembershipTiers
     ├── GymOffers
     ├── GymLeadAnalytics
     ├── GymDashboardPreferences
     └── GymOsSubscriptions
```

This design ensures that all operational data is linked to a specific gym.

---

# 6. Gym Owner Table

### Table: gym_owners

This table stores business owners who manage gyms.

### Fields

| Field                | Type      | Description         |
| -------------------- | --------- | ------------------- |
| id                   | UUID      | Owner ID            |
| user_id              | UUID      | Linked user account |
| business_name        | VARCHAR   | Registered business |
| owner_name           | VARCHAR   | Owner name          |
| owner_phone          | VARCHAR   | Phone number        |
| owner_email          | VARCHAR   | Email               |
| gst_number           | VARCHAR   | Tax registration    |
| pan_number           | VARCHAR   | PAN number          |
| bank_account_details | JSONB     | Payment info        |
| commission_rate      | DECIMAL   | Platform commission |
| created_at           | TIMESTAMP | Record creation     |
| updated_at           | TIMESTAMP | Last update         |

### Relationships

```
GymOwner → Gyms (1:N)
```

One owner can manage multiple gyms.

---

# 7. Gym Table

### Table: gyms

This is the **central table of the GymService module**.

### Fields

| Field            | Type      | Description         |
| ---------------- | --------- | ------------------- |
| id               | UUID      | Gym identifier      |
| owner_id         | UUID      | Gym owner           |
| name             | VARCHAR   | Gym name            |
| slug             | VARCHAR   | SEO identifier      |
| description      | TEXT      | Gym description     |
| neighborhood_id  | UUID      | Location            |
| address          | TEXT      | Full address        |
| contact_phone    | VARCHAR   | Contact number      |
| contact_email    | VARCHAR   | Email               |
| established_year | INTEGER   | Year founded        |
| gym_type         | VARCHAR   | Gym category        |
| chain_name       | VARCHAR   | Chain name          |
| total_sqft       | INTEGER   | Gym size            |
| floors_count     | INTEGER   | Floors              |
| amenities        | JSONB     | Available amenities |
| images           | TEXT[]    | Gym photos          |
| rating           | DECIMAL   | Average rating      |
| review_count     | INTEGER   | Total reviews       |
| created_at       | TIMESTAMP | Created date        |

### Relationships

```
Gym → Equipment
Gym → MembershipTiers
Gym → Offers
Gym → Analytics
Gym → DashboardPreferences
Gym → Subscriptions
```

---

# 8. Equipment Table

### Table: gym_equipment

Stores detailed equipment inventory.

### Fields

| Field                 | Type      | Description         |
| --------------------- | --------- | ------------------- |
| id                    | UUID      | Equipment ID        |
| gym_id                | UUID      | Gym reference       |
| category              | VARCHAR   | Equipment category  |
| equipment_name        | VARCHAR   | Equipment name      |
| brand                 | VARCHAR   | Manufacturer        |
| model                 | VARCHAR   | Model               |
| quantity              | INTEGER   | Units               |
| condition             | VARCHAR   | Equipment condition |
| last_maintenance_date | DATE      | Maintenance date    |
| next_maintenance_due  | DATE      | Next maintenance    |
| is_functional         | BOOLEAN   | Operational status  |
| created_at            | TIMESTAMP | Creation timestamp  |

### Relationship

```
Gym → Equipment (1:N)
```

One gym can have many equipment records.

---

# 9. Membership Tier Table

### Table: gym_membership_tiers

Defines gym membership plans.

### Fields

| Field                      | Type      | Description       |
| -------------------------- | --------- | ----------------- |
| id                         | UUID      | Tier ID           |
| gym_id                     | UUID      | Gym reference     |
| name                       | VARCHAR   | Plan name         |
| description                | TEXT      | Plan description  |
| duration_months            | INTEGER   | Duration          |
| price_monthly              | DECIMAL   | Monthly price     |
| price_total                | DECIMAL   | Total price       |
| joining_fee                | DECIMAL   | Registration fee  |
| freeze_policy_days         | INTEGER   | Freeze policy     |
| amenities_included         | JSONB     | Included services |
| class_access               | BOOLEAN   | Class access      |
| personal_training_included | BOOLEAN   | PT included       |
| created_at                 | TIMESTAMP | Created timestamp |

### Relationship

```
Gym → MembershipTiers (1:N)
```

---

# 10. Gym Offers Table

### Table: gym_offers

Stores promotional offers.

### Fields

| Field               | Type      | Description         |
| ------------------- | --------- | ------------------- |
| id                  | UUID      | Offer ID            |
| gym_id              | UUID      | Gym reference       |
| membership_tier_id  | UUID      | Associated plan     |
| title               | VARCHAR   | Offer title         |
| description         | TEXT      | Offer description   |
| discount_type       | VARCHAR   | Percentage or fixed |
| discount_value      | DECIMAL   | Discount amount     |
| valid_from          | TIMESTAMP | Start date          |
| valid_to            | TIMESTAMP | End date            |
| max_redemptions     | INTEGER   | Maximum uses        |
| current_redemptions | INTEGER   | Current usage       |
| created_at          | TIMESTAMP | Creation timestamp  |

### Relationship

```
Gym → Offers (1:N)
MembershipTier → Offers (1:N)
```

---

# 11. Gym Analytics Table

### Table: gym_lead_analytics

Tracks gym performance metrics.

### Fields

| Field                  | Type      | Description           |
| ---------------------- | --------- | --------------------- |
| id                     | UUID      | Record ID             |
| gym_id                 | UUID      | Gym reference         |
| date                   | DATE      | Analytics date        |
| profile_views          | INTEGER   | Gym page views        |
| trial_bookings         | INTEGER   | Trial requests        |
| trial_conversions      | INTEGER   | Converted memberships |
| chat_queries           | INTEGER   | Customer inquiries    |
| revenue_from_fitlocity | DECIMAL   | Platform revenue      |
| created_at             | TIMESTAMP | Record timestamp      |

---

# 12. Gym Dashboard Preferences

### Table: gym_dashboard_preferences

Stores dashboard settings.

### Fields

| Field                  | Type      | Description       |
| ---------------------- | --------- | ----------------- |
| id                     | UUID      | Preference ID     |
| gym_id                 | UUID      | Gym reference     |
| notification_email     | BOOLEAN   | Email alerts      |
| notification_whatsapp  | BOOLEAN   | WhatsApp alerts   |
| alert_on_new_lead      | BOOLEAN   | Lead alerts       |
| alert_on_trial_booking | BOOLEAN   | Trial alerts      |
| weekly_report          | BOOLEAN   | Weekly summary    |
| created_at             | TIMESTAMP | Created timestamp |

---

# 13. Gym OS Subscription Table

### Table: gym_os_subscriptions

Manages SaaS subscriptions.

### Fields

| Field            | Type      | Description        |
| ---------------- | --------- | ------------------ |
| id               | UUID      | Subscription ID    |
| gym_id           | UUID      | Gym reference      |
| plan_type        | VARCHAR   | Plan name          |
| features_enabled | JSONB     | Enabled features   |
| start_date       | DATE      | Subscription start |
| end_date         | DATE      | Subscription end   |
| price_monthly    | DECIMAL   | Subscription cost  |
| auto_renew       | BOOLEAN   | Auto renewal       |
| status           | VARCHAR   | Active or expired  |
| created_at       | TIMESTAMP | Created timestamp  |

---

# 14. Indexing Strategy

Indexes improve performance.

Key indexes:

| Table                | Indexed Field   |
| -------------------- | --------------- |
| gyms                 | neighborhood_id |
| gyms                 | owner_id        |
| gym_equipment        | gym_id          |
| gym_membership_tiers | gym_id          |
| gym_offers           | gym_id          |
| gym_lead_analytics   | gym_id          |

Example SQL:

```
CREATE INDEX idx_gym_owner ON gyms(owner_id);
```

---

# 15. Data Integrity Constraints

The database enforces constraints.

| Constraint      | Purpose                |
| --------------- | ---------------------- |
| Primary keys    | Unique records         |
| Foreign keys    | Maintain relationships |
| Not null fields | Ensure required data   |
| Unique indexes  | Prevent duplicates     |

Example:

```
FOREIGN KEY (gym_id)
REFERENCES gyms(id)
```

---

# 16. Query Optimization

Common queries include:

| Query                 | Purpose                |
| --------------------- | ---------------------- |
| Find gyms by location | Search results         |
| Get equipment for gym | Equipment transparency |
| Get membership tiers  | Pricing comparison     |
| Fetch gym offers      | Promotions             |
| Retrieve analytics    | Performance dashboards |

Indexes ensure fast query execution.

---

# 17. Migration Strategy

Database schema changes are managed with **Flyway migrations**.

Migration folder:

```
resources/db/migration
```

Example migration file:

```
V1__create_gym_tables.sql
```

Benefits:

- version control for schema
- repeatable migrations
- consistent deployments

---

# 18. Data Security

Sensitive data is protected through:

| Method                | Description        |
| --------------------- | ------------------ |
| Access control        | Role-based access  |
| Encrypted credentials | Secure storage     |
| Secure APIs           | JWT authentication |

---

# 19. Future Database Enhancements

Future improvements may include:

| Feature                 | Description               |
| ----------------------- | ------------------------- |
| Crowd density tracking  | Real-time gym traffic     |
| AI compatibility scores | Gym recommendation engine |
| Trainer integration     | Trainer management        |
| Event data              | Fitness events            |

---

# 20. Summary

The GymService database architecture provides:

- structured gym infrastructure
- strong relational integrity
- scalable data storage
- optimized performance

This schema enables Fitlocity to deliver **transparent gym discovery, structured pricing, equipment intelligence, and analytics insights**.
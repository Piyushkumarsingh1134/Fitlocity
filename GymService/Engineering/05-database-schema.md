# 05-database-schema.md

## Fitlocity Platform — Database Schema (GymService Focus)

---

# 1. Overview

The database schema defines the **structure of all tables used in the GymService module** of the Fitlocity platform.

The GymService schema manages the following core data:

- gym owners
- gyms
- equipment inventory
- membership tiers
- promotional offers
- gym analytics
- dashboard preferences
- SaaS subscriptions

The schema is designed for:

- strong relational integrity
- efficient querying
- scalable data storage
- clear relationships between gym entities

The database follows **normalized relational design principles**.

---

# 2. Database Technology

The recommended database technology stack is:

| Component      | Technology      |
|---------------|-----------------|
| Database       | PostgreSQL      |
| ORM            | Spring Data JPA |
| Migration Tool | Flyway          |
| Primary Key    | UUID            |

Reasons for using PostgreSQL:

- advanced indexing
- JSON support
- strong relational constraints
- high scalability

---

# 3. GymService Tables Overview

The GymService module includes the following tables.

| Table                     | Purpose                          |
|---------------------------|----------------------------------|
| gym_owners                | Stores business owners           |
| gyms                      | Stores gym information           |
| gym_equipment             | Stores equipment inventory       |
| gym_membership_tiers      | Stores membership plans          |
| gym_offers                | Stores promotional offers        |
| gym_lead_analytics        | Stores gym performance metrics   |
| gym_dashboard_preferences | Stores owner dashboard settings  |
| gym_os_subscriptions      | Stores SaaS subscription details |

---

# 4. Entity Relationship Overview

High-level relationships:

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

Explanation:

- One **GymOwner** can manage multiple gyms.
- Each **Gym** can have multiple equipment records.
- Each **Gym** can define multiple membership tiers and offers.

---

# 5. gym_owners Table

Stores **business owners who manage gyms on the platform**.

### Table Name
```
gym_owners
```

### Fields

| Field                | Type         | Description              |
|----------------------|-------------|--------------------------|
| id                   | UUID         | Unique owner identifier  |
| user_id              | UUID         | Linked platform user     |
| business_name        | VARCHAR(255) | Registered business name |
| owner_name           | VARCHAR(255) | Full name of owner       |
| owner_phone          | VARCHAR(20)  | Contact phone            |
| owner_email          | VARCHAR(255) | Email address            |
| gst_number           | VARCHAR(50)  | GST registration         |
| pan_number           | VARCHAR(50)  | PAN number               |
| bank_account_details | JSONB        | Payment account details  |
| commission_rate      | DECIMAL(5,2) | Platform commission      |
| created_at           | TIMESTAMP    | Record creation time     |
| updated_at           | TIMESTAMP    | Last update time         |

### Example Record

```json
{
  "id": "d92b1b6c",
  "business_name": "Iron Strength Fitness Pvt Ltd",
  "owner_name": "Ajay Dangoriya",
  "owner_phone": "9876543210",
  "owner_email": "ajay@fitlocity.com",
  "commission_rate": 12.5
}
```

---

# 6. gyms Table

Central table of the GymService module.

### Table Name
```
gyms
```

### Fields

| Field            | Type         | Description           |
|------------------|-------------|-----------------------|
| id               | UUID         | Unique gym identifier |
| owner_id         | UUID         | Gym owner reference   |
| name             | VARCHAR(255) | Gym name              |
| slug             | VARCHAR(255) | SEO-friendly URL name |
| description      | TEXT         | Gym description       |
| neighborhood_id  | UUID         | Location reference    |
| address          | TEXT         | Full address          |
| contact_phone    | VARCHAR(20)  | Contact number        |
| contact_email    | VARCHAR(255) | Email                 |
| established_year | INTEGER      | Year gym started      |
| gym_type         | VARCHAR(100) | Gym category          |
| chain_name       | VARCHAR(255) | Gym chain name        |
| total_sqft       | INTEGER      | Gym size              |
| floors_count     | INTEGER      | Number of floors      |
| amenities        | JSONB        | Gym amenities         |
| images           | TEXT[]       | Gym images            |
| rating           | DECIMAL(3,2) | Average rating        |
| review_count     | INTEGER      | Number of reviews     |
| created_at       | TIMESTAMP    | Creation date         |

### Example Record

```json
{
  "id": "gym123",
  "name": "Iron Strength Gym",
  "address": "Sector 21, Chandigarh",
  "gym_type": "Strength Training",
  "total_sqft": 3500,
  "rating": 4.6
}
```

---

# 7. gym_equipment Table

Stores equipment inventory.

### Table Name
```
gym_equipment
```

### Fields

| Field                 | Type         | Description          |
|-----------------------|-------------|----------------------|
| id                    | UUID         | Equipment identifier |
| gym_id                | UUID         | Reference to gym     |
| category              | VARCHAR(100) | Equipment category   |
| equipment_name        | VARCHAR(255) | Equipment name       |
| brand                 | VARCHAR(255) | Manufacturer brand   |
| model                 | VARCHAR(255) | Model name           |
| quantity              | INTEGER      | Number of units      |
| condition             | VARCHAR(100) | Equipment condition  |
| last_maintenance_date | DATE         | Last maintenance     |
| next_maintenance_due  | DATE         | Maintenance schedule |
| is_functional         | BOOLEAN      | Working status       |
| created_at            | TIMESTAMP    | Creation time        |

---

# 8. gym_membership_tiers Table

Defines membership plans.

### Table Name
```
gym_membership_tiers
```

### Fields

| Field                      | Type         | Description             |
|----------------------------|-------------|-------------------------|
| id                         | UUID         | Tier ID                 |
| gym_id                     | UUID         | Gym reference           |
| name                       | VARCHAR(255) | Plan name               |
| description                | TEXT         | Plan details            |
| duration_months            | INTEGER      | Duration                |
| price_monthly              | DECIMAL      | Monthly cost            |
| price_total                | DECIMAL      | Total cost              |
| joining_fee                | DECIMAL      | Registration fee        |
| freeze_policy_days         | INTEGER      | Freeze duration         |
| amenities_included         | JSONB        | Included services       |
| class_access               | BOOLEAN      | Access to group classes |
| personal_training_included | BOOLEAN      | PT sessions             |
| created_at                 | TIMESTAMP    | Creation timestamp      |

---

# 9. gym_offers Table

Stores discount offers.

### Table Name
```
gym_offers
```

### Fields

| Field               | Type         | Description         |
|---------------------|-------------|---------------------|
| id                  | UUID         | Offer identifier    |
| gym_id              | UUID         | Gym reference       |
| membership_tier_id  | UUID         | Linked membership   |
| title               | VARCHAR(255) | Offer title         |
| description         | TEXT         | Offer details       |
| discount_type       | VARCHAR(50)  | Percentage or fixed |
| discount_value      | DECIMAL      | Discount amount     |
| valid_from          | DATE         | Offer start         |
| valid_to            | DATE         | Offer end           |
| max_redemptions     | INTEGER      | Maximum uses        |
| current_redemptions | INTEGER      | Current usage       |
| created_at          | TIMESTAMP    | Creation time       |

---

# 10. gym_lead_analytics Table

Tracks gym performance metrics.

### Table Name
```
gym_lead_analytics
```

### Fields

| Field                  | Type      | Description           |
|------------------------|----------|-----------------------|
| id                     | UUID      | Record ID             |
| gym_id                 | UUID      | Gym reference         |
| date                   | DATE      | Analytics date        |
| profile_views          | INTEGER   | Gym profile views     |
| trial_bookings         | INTEGER   | Trial bookings        |
| trial_conversions      | INTEGER   | Converted memberships |
| chat_queries           | INTEGER   | Customer inquiries    |
| revenue_from_fitlocity | DECIMAL   | Platform revenue      |
| created_at             | TIMESTAMP | Record creation       |

---

# 11. gym_dashboard_preferences Table

Stores owner dashboard settings.

### Table Name
```
gym_dashboard_preferences
```

### Fields

| Field                  | Type      | Description      |
|------------------------|----------|------------------|
| id                     | UUID      | Preference ID    |
| gym_id                 | UUID      | Gym reference    |
| notification_email     | BOOLEAN   | Email alerts     |
| notification_whatsapp  | BOOLEAN   | WhatsApp alerts  |
| alert_on_new_lead      | BOOLEAN   | Lead alerts      |
| alert_on_trial_booking | BOOLEAN   | Trial alerts     |
| weekly_report          | BOOLEAN   | Weekly analytics |
| created_at             | TIMESTAMP | Creation date    |

---

# 12. gym_os_subscriptions Table

Stores Gym OS SaaS subscriptions.

### Table Name
```
gym_os_subscriptions
```

### Fields

| Field            | Type         | Description         |
|------------------|-------------|---------------------|
| id               | UUID         | Subscription ID     |
| gym_id           | UUID         | Gym reference       |
| plan_type        | VARCHAR(100) | Subscription plan   |
| features_enabled | JSONB        | Enabled features    |
| start_date       | DATE         | Subscription start  |
| end_date         | DATE         | Subscription expiry |
| price_monthly    | DECIMAL      | Monthly price       |
| auto_renew       | BOOLEAN      | Auto renewal        |
| status           | VARCHAR(50)  | Subscription status |
| created_at       | TIMESTAMP    | Creation time       |

---

# 13. Indexing Strategy

Important indexes:

| Table                | Indexed Field   |
|----------------------|----------------|
| gyms                 | owner_id        |
| gyms                 | neighborhood_id |
| gym_equipment        | gym_id          |
| gym_membership_tiers | gym_id          |
| gym_offers           | gym_id          |
| gym_lead_analytics   | gym_id          |

Example SQL:

```
CREATE INDEX idx_gym_owner ON gyms(owner_id);
```

---

# 14. Data Integrity Constraints

| Constraint        | Purpose                |
|------------------|------------------------|
| Primary Key       | Unique record          |
| Foreign Key       | Maintain relationships |
| Unique Constraint | Prevent duplicates     |
| Not Null          | Required fields        |

Example:

```
FOREIGN KEY (gym_id)
REFERENCES gyms(id)
```

---

# 15. Query Examples

### Get All Gyms
```
SELECT * FROM gyms;
```

### Get Equipment of Gym
```
SELECT * FROM gym_equipment
WHERE gym_id = 'gym123';
```

### Get Membership Plans
```
SELECT * FROM gym_membership_tiers
WHERE gym_id = 'gym123';
```

---

# 16. Summary

The GymService database schema provides a **structured relational data model** for managing the gym ecosystem.

It enables:

- gym discovery
- equipment transparency
- membership pricing
- promotional offers
- analytics insights
- SaaS infrastructure

This schema forms the **data foundation of the Fitlocity platform**.
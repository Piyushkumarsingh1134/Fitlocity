# 06-api-design-specification.md

# Fitlocity — GymService API Design Specification

---

# 1. Overview

This document defines the **REST API design for the GymService module** of the Fitlocity platform.

The GymService APIs allow gym owners and users to interact with gym-related data such as:

- gym profiles
- equipment inventories
- membership tiers
- promotional offers
- gym analytics
- dashboard settings
- SaaS subscriptions

The APIs follow **RESTful architecture principles**.

---

# 2. API Base URL

Example base URL:

```
https://api.fitlocity.com/gym-service/v1
```

Local development:

```
http://localhost:8080/api/v1
```

---

# 3. Authentication

Most endpoints require **JWT authentication**.

Example header:

```
Authorization: Bearer <jwt-token>
```

## Public Endpoints

- View gyms
- View equipment
- View offers
- View membership plans

## Protected Endpoints

- Create gym
- Update gym
- Manage equipment
- Manage memberships
- Analytics access

---

# 4. Standard API Response Format

All APIs follow a consistent response structure.

## Success Response

```json
{
  "success": true,
  "message": "Gym created successfully",
  "data": {}
}
```

## Error Response

```json
{
  "success": false,
  "message": "Gym not found",
  "error": "RESOURCE_NOT_FOUND"
}
```

---

# 5. Gym APIs

---

## 5.1 Create Gym

Creates a new gym profile.

### Endpoint

```
POST /gyms
```

### Authorization

Gym Owner required.

### Request Body

```json
{
  "name": "Iron Strength Gym",
  "description": "Premium strength training gym",
  "address": "Sector 21, Chandigarh",
  "contactPhone": "9876543210",
  "contactEmail": "ironstrength@gmail.com",
  "establishedYear": 2018,
  "gymType": "Strength",
  "totalSqft": 3500,
  "floorsCount": 2,
  "amenities": ["Parking", "Locker Room", "Shower"]
}
```

### Validation Rules

| Field           | Rule             |
| --------------- | ---------------- |
| name            | required         |
| contactPhone    | valid phone      |
| contactEmail    | valid email      |
| establishedYear | positive integer |

### Response

```json
{
  "success": true,
  "message": "Gym created successfully",
  "data": {
    "id": "b2a3c45e",
    "name": "Iron Strength Gym"
  }
}
```

---

## 5.2 Get Gym Details

Retrieve gym profile.

### Endpoint

```
GET /gyms/{gymId}
```

### Example

```
GET /gyms/45fd32
```

### Response

```json
{
  "id": "45fd32",
  "name": "Iron Strength Gym",
  "rating": 4.5,
  "amenities": ["Parking", "Shower"],
  "equipmentCount": 45
}
```

---

## 5.3 Update Gym

Update gym profile.

### Endpoint

```
PUT /gyms/{gymId}
```

### Request

```json
{
  "description": "Updated gym description",
  "totalSqft": 4000
}
```

### Response

```
Gym updated successfully
```

---

## 5.4 List Gyms

Retrieve all gyms.

### Endpoint

```
GET /gyms
```

### Query Parameters

| Parameter    | Description        |
| ------------ | ------------------ |
| page         | page number        |
| size         | page size          |
| city         | filter by city     |
| neighborhood | filter by location |

Example:

```
GET /gyms?page=0&size=10
```

---

# 6. Equipment APIs

---

## 6.1 Add Equipment

Adds equipment to gym inventory.

### Endpoint

```
POST /gyms/{gymId}/equipment
```

### Request

```json
{
  "category": "Strength",
  "equipmentName": "Squat Rack",
  "brand": "Rogue",
  "quantity": 4,
  "condition": "Excellent"
}
```

### Response

```json
{
  "message": "Equipment added successfully"
}
```

---

## 6.2 Get Equipment List

### Endpoint

```
GET /gyms/{gymId}/equipment
```

### Response

```json
[
  {
    "equipmentName": "Bench Press",
    "brand": "Life Fitness",
    "quantity": 3
  }
]
```

---

## 6.3 Update Equipment

### Endpoint

```
PUT /equipment/{equipmentId}
```

### Request

```json
{
  "quantity": 5,
  "condition": "Good"
}
```

---

## 6.4 Delete Equipment

### Endpoint

```
DELETE /equipment/{equipmentId}
```

---

# 7. Membership Tier APIs

---

## 7.1 Create Membership Tier

### Endpoint

```
POST /gyms/{gymId}/membership-tiers
```

### Request

```json
{
  "name": "Premium Plan",
  "durationMonths": 12,
  "priceMonthly": 2000,
  "joiningFee": 500,
  "freezePolicyDays": 30
}
```

### Response

```json
{
  "message": "Membership tier created"
}
```

---

## 7.2 List Membership Plans

### Endpoint

```
GET /gyms/{gymId}/membership-tiers
```

### Response

```json
[
  {
    "name": "Premium Plan",
    "priceMonthly": 2000,
    "durationMonths": 12
  }
]
```

---

## 7.3 Update Membership Tier

### Endpoint

```
PUT /membership-tiers/{tierId}
```

---

## 7.4 Delete Membership Tier

### Endpoint

```
DELETE /membership-tiers/{tierId}
```

---

# 8. Gym Offer APIs

---

## 8.1 Create Offer

### Endpoint

```
POST /gyms/{gymId}/offers
```

### Request

```json
{
  "title": "New Year Discount",
  "discountType": "PERCENTAGE",
  "discountValue": 20,
  "validFrom": "2026-01-01",
  "validTo": "2026-01-31"
}
```

### Response

```
Offer created successfully
```

---

## 8.2 Get Offers

### Endpoint

```
GET /gyms/{gymId}/offers
```

---

## 8.3 Update Offer

### Endpoint

```
PUT /offers/{offerId}
```

---

## 8.4 Delete Offer

### Endpoint

```
DELETE /offers/{offerId}
```

---

# 9. Gym Analytics APIs

---

## 9.1 Get Gym Analytics

### Endpoint

```
GET /gyms/{gymId}/analytics
```

### Response

```json
{
  "profileViews": 1200,
  "trialBookings": 80,
  "trialConversions": 35,
  "revenueFromFitlocity": 75000
}
```

---

# 10. Gym Dashboard APIs

---

## 10.1 Get Dashboard Preferences

### Endpoint

```
GET /dashboard/preferences/{gymId}
```

---

## 10.2 Update Dashboard Preferences

### Endpoint

```
PUT /dashboard/preferences/{gymId}
```

### Request

```json
{
  "notificationEmail": true,
  "notificationWhatsapp": false,
  "alertOnTrialBooking": true
}
```

---

# 11. Gym Subscription APIs

---

## 11.1 Create Subscription

### Endpoint

```
POST /subscriptions
```

### Request

```json
{
  "gymId": "123",
  "planType": "PRO",
  "priceMonthly": 999
}
```

---

## 11.2 Get Subscription

### Endpoint

```
GET /subscriptions/{gymId}
```

---

## 11.3 Cancel Subscription

### Endpoint

```
DELETE /subscriptions/{subscriptionId}
```

---

# 12. Error Handling

| Code | Meaning               |
| ---- | --------------------- |
| 400  | Bad request           |
| 401  | Unauthorized          |
| 403  | Forbidden             |
| 404  | Resource not found    |
| 500  | Internal server error |

Example:

```json
{
  "success": false,
  "message": "Gym not found"
}
```

---

# 13. API Versioning

Example:

```
/api/v1/gyms
/api/v2/gyms
```

Ensures backward compatibility.

---

# 14. Rate Limiting

| Limit              | Example        |
| ------------------ | -------------- |
| User requests      | 100 per minute |
| Gym owner requests | 200 per minute |

---

# 15. Future API Extensions

| Feature           | API              |
| ----------------- | ---------------- |
| AI gym matching   | /recommendations |
| Trial booking     | /trial-bookings  |
| Trainer discovery | /trainers        |
| Fitness events    | /events          |
| Commerce products | /products        |

---

# 16. Summary

The GymService API architecture provides:

- Structured REST endpoints
- Secure authentication
- Scalable request handling
- Consistent response structure

These APIs enable Fitlocity to support gym discovery, equipment transparency, membership management, and analytics insights.
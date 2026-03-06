# 06-api-contracts.md

## Fitlocity Platform — API Contracts (GymService Module)

---

# 1. Overview

This document defines the **API contracts for the GymService module** of the Fitlocity platform.

The GymService APIs allow gym owners and platform users to interact with gym infrastructure data including:

- gym profiles
- gym equipment
- membership plans
- promotional offers
- gym analytics
- dashboard preferences
- SaaS subscriptions

API contracts specify:

- endpoint URLs
- request structure
- response format
- authentication requirements
- validation rules

---

# 2. Base API URL

### Production

```
https://api.fitlocity.com/gym-service/v1
```

### Development

```
http://localhost:8080/api/v1
```

Example full endpoint:

```
http://localhost:8080/api/v1/gyms
```

---

# 3. Authentication

Most APIs require **JWT authentication**.

### Header Format

```
Authorization: Bearer <jwt_token>
```

---

## Public APIs

| Endpoint                        | Description    |
|---------------------------------|---------------|
| GET /gyms                       | List gyms      |
| GET /gyms/{id}                  | View gym       |
| GET /gyms/{id}/equipment        | View equipment |
| GET /gyms/{id}/membership-tiers | View plans     |
| GET /gyms/{id}/offers           | View offers    |

---

## Protected APIs

| Endpoint                         | Description   |
|----------------------------------|--------------|
| POST /gyms                       | Create gym    |
| PUT /gyms/{id}                   | Update gym    |
| POST /gyms/{id}/equipment        | Add equipment |
| POST /gyms/{id}/membership-tiers | Create plan   |
| POST /gyms/{id}/offers           | Create offer  |

---

# 4. Standard API Response Format

All responses follow a consistent structure.

## Success Response

```json
{
  "success": true,
  "message": "Operation successful",
  "data": {}
}
```

## Error Response

```json
{
  "success": false,
  "message": "Resource not found",
  "errorCode": "RESOURCE_NOT_FOUND"
}
```

---

# 5. Gym APIs

---

## 5.1 Create Gym

### Endpoint

```
POST /gyms
```

### Authorization

Gym owner required.

### Request Body

```json
{
  "name": "Iron Strength Gym",
  "description": "Premium strength training gym",
  "address": "Sector 21, Chandigarh",
  "contactPhone": "9876543210",
  "contactEmail": "ironstrength@gmail.com",
  "establishedYear": 2018,
  "gymType": "Strength Training",
  "totalSqft": 3500,
  "floorsCount": 2,
  "amenities": ["Parking", "Shower", "Locker"]
}
```

### Response

```json
{
  "success": true,
  "message": "Gym created successfully",
  "data": {
    "id": "gym123",
    "name": "Iron Strength Gym"
  }
}
```

---

## 5.2 Get Gym Details

### Endpoint

```
GET /gyms/{gymId}
```

### Path Parameters

| Parameter | Description   |
|-----------|--------------|
| gymId     | Unique gym ID |

### Example

```
GET /gyms/gym123
```

### Response

```json
{
  "id": "gym123",
  "name": "Iron Strength Gym",
  "address": "Sector 21 Chandigarh",
  "rating": 4.5,
  "reviewCount": 120
}
```

---

## 5.3 Update Gym

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

```json
{
  "success": true,
  "message": "Gym updated successfully"
}
```

---

## 5.4 List Gyms

### Endpoint

```
GET /gyms
```

### Query Parameters

| Parameter | Description |
|-----------|------------|
| page      | Page number |
| size      | Page size   |
| city      | Filter city |

Example:

```
GET /gyms?page=0&size=10
```

### Response

```json
{
  "content": [
    {
      "id": "gym123",
      "name": "Iron Strength Gym",
      "rating": 4.6
    }
  ],
  "page": 0,
  "size": 10
}
```

---

# 6. Equipment APIs

---

## 6.1 Add Equipment

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
  "model": "XR-200",
  "quantity": 4,
  "condition": "Excellent"
}
```

### Response

```json
{
  "success": true,
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
  },
  {
    "equipmentName": "Leg Press",
    "brand": "Hammer Strength",
    "quantity": 2
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
  "success": true,
  "message": "Membership tier created"
}
```

---

## 7.2 List Membership Tiers

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

```json
{
  "success": true,
  "message": "Offer created successfully"
}
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

# 10. Dashboard Preference APIs

---

## 10.1 Get Preferences

```
GET /dashboard/preferences/{gymId}
```

---

## 10.2 Update Preferences

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

# 11. Subscription APIs

---

## 11.1 Create Subscription

```
POST /subscriptions
```

### Request

```json
{
  "gymId": "gym123",
  "planType": "PRO",
  "priceMonthly": 999
}
```

---

## 11.2 Get Subscription

```
GET /subscriptions/{gymId}
```

---

## 11.3 Cancel Subscription

```
DELETE /subscriptions/{subscriptionId}
```

---

# 12. HTTP Status Codes

| Code | Meaning                  |
|------|--------------------------|
| 200  | Success                  |
| 201  | Created                  |
| 400  | Bad Request              |
| 401  | Unauthorized             |
| 403  | Forbidden                |
| 404  | Resource Not Found       |
| 500  | Internal Server Error    |

---

# 13. Validation Rules

| Field        | Rule            |
|-------------|----------------|
| name         | Required        |
| contactEmail | Valid email     |
| priceMonthly | Positive value  |
| quantity     | Must be > 0     |

---

# 14. API Versioning

Versioning ensures backward compatibility.

Example:

```
/api/v1/gyms
/api/v2/gyms
```

---

# 15. Summary

The GymService API contracts define **standardized endpoints and request/response formats** for managing gym infrastructure within the Fitlocity platform.

These APIs enable:

- gym discovery
- equipment transparency
- membership management
- promotional offers
- analytics tracking
- SaaS subscription management

This contract ensures **consistent communication between frontend applications and backend services**.
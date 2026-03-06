# Fitlocity API Testing Guide

## Auth Service (Port 4001)

### 1. Request OTP
```
POST http://localhost:4001/auth/request-otp
Content-Type: application/json

{
  "phone": "9876543210",
  "role": "OWNER"
}
```
**Response:** `OTP sent`
**Note:** Check console for OTP (e.g., `OTP: 123456`)

---

### 2. Verify OTP
```
POST http://localhost:4001/auth/verify-otp
Content-Type: application/json

{
  "phone": "9876543210",
  "otp": "123456"
}
```
**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "uuid-string"
}
```
**Save the accessToken for GymService requests**

---

### 3. Refresh Token
```
POST http://localhost:4001/auth/refresh
Content-Type: application/json

{
  "refreshToken": "your-refresh-token-uuid"
}
```
**Response:**
```json
{
  "accessToken": "new-jwt-token"
}
```

---

## GymService (Port 8082)

### Public Endpoints (No Auth Required)

#### 1. Health Check
```
GET http://localhost:8082/api/test
```
**Response:**
```json
{
  "message": "Gym Service API working",
  "status": "OK",
  "timestamp": "2025-01-28T10:30:00",
  "service": "GymService"
}
```

---

#### 2. List All Gyms
```
GET http://localhost:8082/api/gyms?page=0&size=10&sortBy=createdAt
```
**Response:**
```json
{
  "content": [
    {
      "id": "uuid",
      "name": "Iron Gym",
      "slug": "iron-gym",
      "address": "Chandigarh",
      "gymType": "Strength",
      "isVerified": false,
      "rating": null
    }
  ],
  "page": 0,
  "size": 10
}
```

---

#### 3. Get Gym by ID
```
GET http://localhost:8082/api/gyms/{gymId}
```

---

#### 4. Get Gym Equipment
```
GET http://localhost:8082/api/gyms/{gymId}/equipment
```

---

#### 5. Get Membership Tiers
```
GET http://localhost:8082/api/gyms/{gymId}/membership-tiers
```

---

#### 6. Get Gym Offers
```
GET http://localhost:8082/api/gyms/{gymId}/offers
```

---

### Protected Endpoints (JWT Required)

**Add to all requests:**
```
Authorization: Bearer YOUR_ACCESS_TOKEN
```

#### 7. Create Gym
```
POST http://localhost:8082/api/gyms
Authorization: Bearer YOUR_ACCESS_TOKEN
Content-Type: application/json

{
  "name": "Iron Strength Gym",
  "slug": "iron-strength-gym",
  "description": "Premium strength training gym",
  "address": "Sector 21, Chandigarh",
  "gymType": "Strength Training",
  "contactPhone": "9876543210",
  "contactEmail": "gym@test.com"
}
```
**Response:**
```json
{
  "id": "uuid",
  "name": "Iron Strength Gym",
  "slug": "iron-strength-gym",
  "address": "Sector 21, Chandigarh",
  "gymType": "Strength Training",
  "isVerified": false,
  "rating": null
}
```

---

#### 8. Add Equipment
```
POST http://localhost:8082/api/gyms/{gymId}/equipment
Authorization: Bearer YOUR_ACCESS_TOKEN
Content-Type: application/json

{
  "category": "Strength",
  "equipmentName": "Bench Press",
  "brand": "Rogue",
  "model": "XR-200",
  "quantity": 5,
  "condition": "Excellent"
}
```
**Response:**
```json
{
  "id": "uuid",
  "category": "Strength",
  "equipmentName": "Bench Press",
  "brand": "Rogue",
  "quantity": 5,
  "condition": "Excellent"
}
```

---

#### 9. Create Membership Tier
```
POST http://localhost:8082/api/gyms/{gymId}/membership-tiers
Authorization: Bearer YOUR_ACCESS_TOKEN
Content-Type: application/json

{
  "name": "Premium Plan",
  "description": "12 month premium membership",
  "durationMonths": 12,
  "priceMonthly": 2000,
  "priceTotal": 24000,
  "joiningFee": 500
}
```
**Response:**
```json
{
  "id": "uuid",
  "name": "Premium Plan",
  "durationMonths": 12,
  "priceMonthly": 2000,
  "priceTotal": 24000,
  "joiningFee": 500
}
```

---

#### 10. Create Offer
```
POST http://localhost:8082/api/gyms/{gymId}/offers
Authorization: Bearer YOUR_ACCESS_TOKEN
Content-Type: application/json

{
  "title": "New Year Sale",
  "description": "20% discount on all memberships",
  "discountType": "PERCENTAGE",
  "discountValue": 20,
  "maxRedemptions": 100
}
```
**Response:**
```json
{
  "id": "uuid",
  "title": "New Year Sale",
  "description": "20% discount on all memberships",
  "discountType": "PERCENTAGE",
  "discountValue": 20,
  "maxRedemptions": 100
}
```

---

#### 11. Delete Equipment
```
DELETE http://localhost:8082/api/gyms/{gymId}/equipment/{equipmentId}
Authorization: Bearer YOUR_ACCESS_TOKEN
```

---

#### 12. Delete Membership Tier
```
DELETE http://localhost:8082/api/gyms/{gymId}/membership-tiers/{tierId}
Authorization: Bearer YOUR_ACCESS_TOKEN
```

---

#### 13. Delete Offer
```
DELETE http://localhost:8082/api/gyms/{gymId}/offers/{offerId}
Authorization: Bearer YOUR_ACCESS_TOKEN
```

---

## Complete Test Flow

### Step 1: Get JWT Token
1. Request OTP for phone `9876543210` with role `OWNER`
2. Check console for OTP
3. Verify OTP
4. Copy `accessToken`

### Step 2: Create Gym
Use the JWT token to create a gym (owner auto-created)

### Step 3: Add Resources
Use the `gymId` from Step 2 to:
- Add equipment
- Create membership tiers
- Create offers

### Step 4: View Public Data
Test public endpoints without JWT:
- List all gyms
- View gym details
- View equipment, memberships, offers

---

## Error Responses

### 401 Unauthorized
```json
{
  "timestamp": "2025-01-28T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required"
}
```

### 404 Not Found
```json
{
  "timestamp": "2025-01-28T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Gym not found"
}
```

### 400 Bad Request
```json
{
  "timestamp": "2025-01-28T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Slug already exists"
}
```

---

## Postman Collection Variables

Set these variables in Postman:
- `auth_base_url`: `http://localhost:4001`
- `gym_base_url`: `http://localhost:8082`
- `access_token`: (set after login)
- `gym_id`: (set after creating gym)

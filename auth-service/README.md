# Auth Service API Documentation

This service handles authentication via phone number and OTP.

## Base URL
`http://localhost:4001/auth`

---

## Endpoints

### 1. Request OTP
Sends an OTP to the specified phone number and registers the requested role.

- **URL:** `/request-otp`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "phone": "9876543210",
    "role": "customer"
  }
  ```
  *Note: Role can be `CUSTOMER`, `OWNER`, or `ADMIN`. It is case-insensitive in the request.*

- **Response (200 OK):**
  ```
  OTP sent
  ```

---

### 2. Verify OTP
Verifies the OTP sent to the phone number and returns authentication tokens.

- **URL:** `/verify-otp`
- **Method:** `POST`
- **Headers:**
  - `User-Agent`: (Optional) Device information
  - `X-Forwarded-For`: (Optional) IP address
- **Body:**
  ```json
  {
    "phone": "9876543210",
    "otp": "123456"
  }
  ```

- **Response (200 OK):**
  ```json
  {
    "accessToken": "ey...",
    "refreshToken": "uuid-..."
  }
  ```

---

### 3. Refresh Token
Generates a new access token using a valid refresh token.

- **URL:** `/refresh`
- **Method:** `POST`
- **Body:**
  ```json
  {
    "refreshToken": "your-refresh-token-uuid"
  }
  ```

- **Response (200 OK):**
  ```json
  {
    "accessToken": "new-ey..."
  }
  ```

---

## Testing with cURL

### Request OTP
```bash
curl -X POST http://localhost:4001/auth/request-otp \
     -H "Content-Type: application/json" \
     -d '{"phone": "1234567890", "role": "customer"}'
```

### Verify OTP
```bash
curl -X POST http://localhost:4001/auth/verify-otp \
     -H "Content-Type: application/json" \
     -d '{"phone": "1234567890", "otp": "YOUR_OTP_HERE"}'
```

---

## Data Models

### User Roles
- `CUSTOMER` (Default)
- `OWNER`
- `ADMIN`

### Database Persistence
- **OtpVerification**: Stores phone, hashed OTP, requested role, and expiry.
- **User**: Stores phone and role. If a new user signs up, the role from the OTP request is assigned. If an existing user requests a different role, it is updated upon verification.

# 08-scoring-engine-spec.md

## Fitlocity — AI Scoring & Recommendation Engine (GymService Focus)

---

# 1. Overview

The **Fitlocity Scoring Engine** is an AI-driven recommendation system designed to help users discover the most suitable gyms based on their preferences, location, and behavior.

The engine calculates a **gym compatibility score** for each gym and ranks gyms accordingly.

The scoring engine uses data from the **GymService module**, including:

- Gym attributes
- Equipment inventory
- Membership pricing
- Amenities
- Ratings
- Analytics data

The output is a **ranked list of recommended gyms**.

---

# 2. Goals of the Scoring Engine

| Goal                         | Description                             |
| ---------------------------- | --------------------------------------- |
| Personalized recommendations | Suggest gyms based on user preferences  |
| Smart ranking                | Rank gyms based on relevance            |
| Improve discovery            | Help users find the best gym faster     |
| Increase conversion          | Increase trial bookings and memberships |

---

# 3. Inputs to the Scoring Engine

The scoring engine uses multiple types of data.

## 3.1 User Inputs

| Input               | Description                               |
| ------------------- | ----------------------------------------- |
| Location            | User's city or coordinates                |
| Fitness goal        | Weight loss, muscle gain, general fitness |
| Budget              | Monthly membership budget                 |
| Preferred equipment | Machines or training equipment            |
| Amenities           | Sauna, parking, locker rooms              |
| Gym type            | CrossFit, strength training, yoga         |

### Example User Input

```json
{
  "location": "Chandigarh",
  "budget": 2000,
  "fitnessGoal": "Muscle Gain",
  "preferredEquipment": ["Squat Rack", "Bench Press"],
  "amenities": ["Parking", "Locker Room"]
}
```

---

# 4. Gym Data Used for Scoring

The scoring engine retrieves gym data from **GymService database tables**.

| Table                | Data Used             |
| -------------------- | --------------------- |
| gyms                 | Gym profile details   |
| gym_equipment        | Equipment inventory   |
| gym_membership_tiers | Pricing plans         |
| gym_offers           | Promotional discounts |
| gym_lead_analytics   | Popularity metrics    |

---

# 5. Scoring Factors

Each gym receives a score based on multiple factors.

| Factor                   | Weight |
| ------------------------ | ------ |
| Location proximity       | 25%    |
| Equipment match          | 20%    |
| Membership affordability | 15%    |
| Amenities match          | 15%    |
| Gym rating               | 10%    |
| Popularity analytics     | 10%    |
| Active offers            | 5%     |

**Total score = 100 points**

---

# 6. Scoring Formula

```text
GymScore =
(LocationScore * 0.25) +
(EquipmentScore * 0.20) +
(PriceScore * 0.15) +
(AmenitiesScore * 0.15) +
(RatingScore * 0.10) +
(PopularityScore * 0.10) +
(OfferScore * 0.05)
```

---

# 7. Location Scoring

Location score measures **distance between the user and the gym**.

| Distance | Score |
| -------- | ----- |
| < 1 km   | 100   |
| 1–3 km   | 80    |
| 3–5 km   | 60    |
| 5–10 km  | 40    |
| > 10 km  | 20    |

### Example

User location: Chandigarh Sector 22  
Gym location: Sector 21

Distance ≈ 0.8 km

**LocationScore = 100**

---

# 8. Equipment Matching Score

The system compares **user preferred equipment vs gym equipment inventory**.

### Formula

```text
EquipmentScore =
(Number of matching equipment / total requested equipment) * 100
```

### Example

User requested:

- Bench Press
- Squat Rack
- Deadlift Platform

Gym equipment:

- Bench Press
- Squat Rack

Match = 2 / 3

**EquipmentScore = 66**

---

# 9. Price Affordability Score

Price score evaluates whether the gym membership fits the user's budget.

| Monthly Price    | Score |
| ---------------- | ----- |
| Within budget    | 100   |
| 10% above budget | 80    |
| 20% above budget | 60    |
| 50% above budget | 30    |

### Example

User budget = ₹2000  
Gym price = ₹1900

**PriceScore = 100**

---

# 10. Amenities Score

Amenities are matched against user preferences.

### Formula

```text
AmenitiesScore =
(Matching amenities / requested amenities) * 100
```

### Example

User wants:

- Parking
- Locker Room
- Shower

Gym offers:

- Parking
- Shower

2 / 3 = 66

**AmenitiesScore = 66**

---

# 11. Gym Rating Score

Gym rating comes from the `gyms` table.

### Formula

```text
RatingScore = (GymRating / 5) * 100
```

### Example

Gym rating = 4.5

RatingScore = 90

---

# 12. Popularity Score

Popularity is calculated from **analytics metrics** in `gym_lead_analytics`.

### Metrics Used

| Metric            | Description            |
| ----------------- | ---------------------- |
| profile_views     | Gym profile views      |
| trial_bookings    | Trial requests         |
| trial_conversions | Membership conversions |

### Example Formula

```text
PopularityScore =
(profile_views_weight +
 trial_bookings_weight +
 trial_conversion_weight)
```

---

# 13. Offer Score

If the gym has active promotions, it receives a bonus score.

| Condition    | Score |
| ------------ | ----- |
| Active offer | 100   |
| No offer     | 50    |

---

# 14. Final Ranking Algorithm

After computing scores, gyms are sorted.

### Steps

1. Retrieve gyms from GymService
2. Calculate individual scores
3. Compute final score
4. Rank gyms by score
5. Return top gyms

### Example Ranking

| Rank | Gym                | Score |
| ---- | ------------------ | ----- |
| 1    | Iron Strength Gym  | 92    |
| 2    | PowerHouse Fitness | 88    |
| 3    | FitZone Gym        | 81    |

---

# 15. API Integration

### Endpoint

```
GET /recommendations/gyms
```

### Example Request

```
GET /recommendations/gyms?city=chandigarh&budget=2000
```

### Example Response

```json
{
  "recommendations": [
    {
      "gymId": "gym123",
      "name": "Iron Strength Gym",
      "score": 92
    },
    {
      "gymId": "gym456",
      "name": "PowerHouse Fitness",
      "score": 88
    }
  ]
}
```

---

# 16. Architecture of the Scoring Engine

```text
User Request
     │
     ▼
Recommendation API
     │
     ▼
Scoring Engine
     │
     ▼
GymService Database
     │
     ▼
Score Calculation
     │
     ▼
Ranked Gym List
```

---

# 17. Machine Learning Extension (Future)

Future versions may include machine learning models.

| Model                   | Use                                        |
| ----------------------- | ------------------------------------------ |
| Collaborative filtering | Recommend gyms based on user behavior      |
| Content-based filtering | Match gym attributes with user preferences |
| Deep learning           | Predict membership conversions             |

### Training Data

- User gym visits
- Trial bookings
- Membership purchases
- User ratings

---

# 18. Performance Optimization

| Technique          | Purpose                       |
| ------------------ | ----------------------------- |
| Caching            | Store recommendation results  |
| Precomputed scores | Reduce real-time calculations |
| Redis cache        | Fast lookup                   |

---

# 19. Monitoring Metrics

| Metric                    | Purpose                |
| ------------------------- | ---------------------- |
| Recommendation click rate | User engagement        |
| Trial booking conversion  | Recommendation success |
| API response time         | Performance monitoring |

---

# 20. Summary

The Fitlocity scoring engine provides a **smart recommendation system** that ranks gyms based on:

- Location proximity
- Equipment availability
- Membership pricing
- Amenities
- Gym ratings
- Popularity metrics

By integrating tightly with the **GymService module**, the engine improves gym discovery and user experience across the Fitlocity platform.
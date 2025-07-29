# ✅ Alpha-Pi MVP Backend Summary (Spring Boot 3.5.3 + MySQL)

## 🎯 Purpose

Alpha-Pi is a web platform that helps **small business owners** connect with **potential investors**.

Users can:
- Sign up as either a **Business Owner** or an **Investor**
- Fill out their respective profiles
- Investors can **save businesses** they’re interested in
- Business owners can **see who saved their business**
- Authentication is handled simply via `email + password` directly inside each profile (no separate `User` table)

---

## 🧱 Tech Stack

| Layer      | Tech                               |
|------------|------------------------------------|
| Backend    | Spring Boot 3.5.3                  |
| Database   | MySQL                              |
| ORM        | Spring Data JPA                    |
| Security   | Spring Security (BCrypt only)      |
| API Tool   | Postman (for manual testing)       |

---

## 👤 User Types

### 1. **Business Owner**
- Signs up with email + password
- Fills out their business profile
- Can **publish** the business when ready
- Can view a list of **interested investors** (those who saved their business)

### 2. **Investor**
- Signs up with email + password
- Fills out investor profile
- Can **browse all published businesses**
- Can **save/unsave** businesses
- Can view all businesses they’ve saved

---

## 📦 Entity Model (Core 3 Tables)

### 1. `BusinessProfile`
- Fields: `id`, `email`, `password`, `businessName`, `industry`, `description`, `logoUrl`, `contact info`, `isPublished`, `fundingGoal`, `currentRevenue`, `foundedDate`, `isRunning`
- One-to-many (inverse) with `InvestorSavedBusiness` as `interestedInvestors`

### 2. `InvestorProfile`
- Fields: `id`, `email`, `password`, `name`, `contact info`, `state`, `investmentRange`, `interests`
- One-to-many (inverse) with `InvestorSavedBusiness` as `savedBusinesses`

### 3. `InvestorSavedBusiness`
- Join table entity (not optional — fully modeled)
- Fields: `id`, `investor`, `business`, `savedAt`
- Many-to-One to both `InvestorProfile` and `BusinessProfile`

---

## 📤 DTOs Created

| DTO                     | Purpose                                      |
|-------------------------|----------------------------------------------|
| `BusinessProfileDTO`    | Business details + list of interested investors |
| `InvestorProfileDTO`    | Investor details + list of saved businesses  |
| `InvestorSavedBusinessDTO` | Saved business summary (businessId, name, industry, savedAt) |
| `InterestedInvestorDTO` | Shown to business owner as interested investor card |

---

## 🔐 Authentication Strategy

- No `User` table — each profile contains its own `email + password`
- Passwords hashed using `BCryptPasswordEncoder`
- Controllers for `/signup` and `/login` handle hashing + login checks manually
- No sessions, tokens, or roles (MVP simplicity)
- IDs are returned on successful login and tracked by frontend (e.g., localStorage)

---

## 🔐 Security Configuration

**SecurityConfig.java**:

- CSRF disabled
- `permitAll()` for:
  - `/api/businesses/signup`
  - `/api/businesses/login`
  - `/api/investors/signup`
  - `/api/investors/login`
  - `/api/businesses/**`
  - `/api/investors/**`
  - `/api/saved/**`
- All other routes require authentication (but this is relaxed for MVP)

---

## 📂 Controllers Built

### 1. `BusinessProfileController`

| Method | Endpoint                     | Description                             |
|--------|------------------------------|-----------------------------------------|
| POST   | `/api/businesses/signup`     | Sign up as business owner               |
| POST   | `/api/businesses/login`      | Log in as business owner                |
| PUT    | `/api/businesses/{id}`       | Update business profile                 |
| GET    | `/api/businesses/{id}`       | View full business profile (includes interested investors) |
| GET    | `/api/businesses`            | View all published businesses           |

---

### 2. `InvestorProfileController`

| Method | Endpoint                     | Description                             |
|--------|------------------------------|-----------------------------------------|
| POST   | `/api/investors/signup`      | Sign up as investor                     |
| POST   | `/api/investors/login`       | Log in as investor                      |
| PUT    | `/api/investors/{id}`        | Update investor profile                 |
| GET    | `/api/investors/{id}`        | View investor + saved businesses        |

---

### 3. `InvestorSavedBusinessController`

| Method | Endpoint                                       | Description                            |
|--------|------------------------------------------------|----------------------------------------|
| POST   | `/api/saved/{investorId}/{businessId}`         | Save a business (if not already saved) |
| DELETE | `/api/saved/{investorId}/{businessId}`         | Unsave a business                      |
| GET    | `/api/saved/business/{businessId}`             | Get list of investors who saved it     |

---

## 🧪 Postman Testing Results

✅ All endpoints tested:
- Sign up / Login ✅
- Profile update ✅
- Save / Unsave ✅
- View saved list ✅
- View interested investors ✅

---

## ✅ Current Status

All core backend functionality for the MVP is **implemented and fully tested**. It is ready to:

- Connect to a frontend (React)
- Support user signup/login
- Manage investor–business relationships via saves
- Display interested investors to business owners
- Display saved businesses to investors

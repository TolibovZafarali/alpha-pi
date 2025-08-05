# Alpha-Pi

Alpha-Pi is a full-stack platform that helps small business owners connect with investors in a more approachable and intelligent way. Business owners can build professional profiles with detailed business metrics, while investors can browse, filter, and save startups that match their interests and investment range. The platform includes smart filtering, investor-business messaging, and an AI mentor to guide business owners. Alpha-Pi is designed to make business funding more accessible, and to replace intimidating processes with a human-friendly and intuitive experience.

---

## Technologies Used

### Frontend
- React + Vite
- Custom styling with Flexbox
- EmailJS (for investor-to-business contact)
- React Router

### Backend
- Java + Spring Boot
- MySQL with Spring Data JPA (ORM)
- RESTful API design
- Java Validation (Hibernate Validator)

### Other
- Git & GitHub (version control)
- Postman (API testing)

---

## Installation Instructions

### 1. Clone the repository

```bash
git clone https://github.com/TolibovZafarali/alpha-pi.git
cd alpha-pi
```

### 2. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

### 3. Backend Setup

```bash
cd backend
./mvnw spring-boot:run
```

Make sure you have a local MySQL database running and update application.properties with your database credentials.

### 4. Environment Variables

Create a `.env` file in the `frontend/` directory and add:

```
VITE_GNEWS_API_KEY=your_api_key
```

#### Wireframe
Link: [Figma](https://www.figma.com/design/tCyj21yBK2Y2o2Gd2A2Uko/Wireframe-for-Alpha-Pi?node-id=54-136&t=aE5CSBCbd69RO32V-1)

### ER Diagram
Link: [DB Diagram](https://dbdiagram.io/d/Updated-Diagram-6887acd2cca18e685c19f8b2)

#### Unsolved Problems / Future Features
- In-app chat with message history and real-time updates (currently using EmailJS)
- AI mentor to assist business owners based on profile data
- Admin dashboard for platform moderation and analytics
- Location-based search and filtering (e.g. by state)
- Investor-side notifications for profile matches or contact attempts
# Alpha-Pi

Alpha-Pi is a full-stack platform that connects small business owners with potential investors in a simple and approachable way. Business owners can create professional profiles with real business metrics, while investors can browse, filter, and save startups that align with their industry interests and investment range.

---

✨ Live Demo: [thealphapi.com](https://www.thealphapi.com)

---

## Technologies Used

### Frontend
- React + Vite
- Custom styling with Flexbox
- EmailJS for investor-to-business contact
- React Router for navigation
- Axios for API communication

### Backend
- Java 21 + Spring Boot 3
- MySQL with Spring Data JPA
- JWT authentication (access & refresh tokens)
- Role-based security
- Validation with Hibernate Validator
- RESTful API design

### Infrastructure & Tools
- AWS Elastic Beanstalk + RDS (Aurora/MySQL)
- Dockerized backend for deployment
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

For the backend, set your database connection details in `backend/src/main/resources/application.properties` (or provide
them as environment variables) before running the service:

```
spring.datasource.url=jdbc:mysql://localhost:3306/alpha_pi
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password
```

---

### Deployment

- Backend: packaged with Docker and deployed to AWS Elastic Beanstalk
- Database: AWS RDS (Aurora MySQL)
- Frontend: deployed via AWS Amplify (connected to domain [thealphapi.com](https://www.thealphapi.com))

---

#### Wireframe
Link: [Figma](https://www.figma.com/design/tCyj21yBK2Y2o2Gd2A2Uko/Wireframe-for-Alpha-Pi?node-id=54-136&t=aE5CSBCbd69RO32V-1)

### ER Diagram
Link: [DB Diagram](https://dbdiagram.io/d/Updated-Diagram-6887acd2cca18e685c19f8b2)

#### Roadmap

- ✅ Business & Investor dashboards
- ✅ Role-aware JWT authentication
- ✅ Smart filtering system
- ✅ EmailJS integration
- ⏳ AI mentor for personalized startup guidance
- ⏳ Real-time chat between investors & business owners
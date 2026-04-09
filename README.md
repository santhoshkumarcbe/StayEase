# 🏨 StayEase Backend API

A scalable backend system for a hotel booking platform built using **Spring Boot**, featuring **JWT-based authentication**, **role-based authorization**, and deployed on Render with PostgreSQL.

---

## 🚀 Live Links

- 🔗 **Live API**: https://stayease-kw1s.onrender.com  
- 📘 **Swagger Docs**: https://stayease-kw1s.onrender.com/swagger-ui/index.html  
- 💻 **GitHub Repo**: https://github.com/santhoshkumarcbe/StayEase  

---

## 🧰 Tech Stack

- **Backend**: Spring Boot (Java 17)  
- **Security**: Spring Security + JWT Authentication  
- **Database**: PostgreSQL (Render Managed DB)  
- **ORM**: Spring Data JPA / Hibernate  
- **Build Tool**: Gradle  
- **Deployment**: Docker + Render  
- **API Docs**: Swagger (OpenAPI 3)  

---

## 🔐 Features

- User Registration & Login (JWT Authentication)  
- Role-Based Access Control:  
  - 👤 USER  
  - 🏨 HOTEL_MANAGER  
  - 🛠️ ADMIN  
- Hotel Management APIs  
- Booking Management APIs  
- Secure REST APIs with token validation  
- Global Exception Handling  
- Swagger UI for API testing  

---

## 📂 Project Structure

```
src/
 ├── controller/
 ├── service/
 ├── repository/
 ├── entity/
 ├── config/
 └── security/
```

---

## 🔑 Authentication Flow

1. User logs in via `/auth/login`  
2. Server returns JWT token  
3. Client sends token in header:

```
Authorization: Bearer <token>
```

4. JWT filter validates token for protected APIs  

---

## 📌 API Endpoints

### 🔓 Public APIs

| Method | Endpoint         | Description       |
|--------|----------------|------------------|
| POST   | `/auth/register` | Register user     |
| POST   | `/auth/login`    | Login & get token |
| GET    | `/api/hotels`    | View hotels       |

---

### 🔒 Protected APIs

| Method | Endpoint        | Role           |
|--------|---------------|----------------|
| GET    | `/api/users`    | USER           |
| POST   | `/api/bookings` | USER           |
| PUT    | `/api/hotels`   | HOTEL_MANAGER  |
| DELETE | `/api/hotels`   | ADMIN          |

---

## 🧪 Testing with Swagger

1. Open Swagger UI:  
https://stayease-kw1s.onrender.com/swagger-ui/index.html  

2. Click **Authorize** 🔒  
3. Enter token:  
```
Bearer <your_jwt_token>
```

4. Test secured APIs  

---

## ⚙️ Environment Variables

Set in Render:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<db>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
```

---

## 🐳 Docker Setup

### Build Image
```
docker build -t stayease-app .
```

### Run Container
```
docker run -p 8080:8080 stayease-app
```

---

## 📈 Key Highlights

- Implemented **JWT authentication and authorization**  
- Designed **RESTful APIs with layered architecture**  
- Integrated **Swagger for API documentation**  
- Deployed using **Docker on Render**  
- Connected to **managed PostgreSQL database**  

---

## 💡 Future Enhancements

- Payment Integration  
- Email Notifications  
- Caching (Redis)  
- Rate Limiting  
- CI/CD Pipeline  

---

## 👨‍💻 Author

SanthoshKumar K  
Software Engineer  

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!

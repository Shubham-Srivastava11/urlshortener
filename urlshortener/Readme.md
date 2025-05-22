# 🔗 URL Shortener Backend

This is the backend service for a URL shortener application built using **Spring Boot**, **PostgreSQL**, and **Docker**.

---

## 🧰 Tech Stack

- Java 17
- Spring Boot
- PostgreSQL
- Docker & Docker Compose
- Lombok

---

## 📦 Features

- Generate short URLs for long links
- Optional custom aliases
- Optional expiry dates
- Redirect from short URL to original URL
- URL storage in PostgreSQL
- Containerized with Docker

---

## 🚀 Getting Started

### 1. Clone the repository

```
git clone https://github.com/your-username/url-shortener-backend.git
cd url-shortener-backend
```

### 2. Configure Environment Variables

Update the database config in `application.properties` (or use environment variables):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/urlshortenerdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

### 3. Run with Docker Compose

Make sure Docker is installed and running.

```
docker-compose up --build
```

This will start both:

- Spring Boot app at `http://localhost:8080`
- PostgreSQL database

---

## 📫 API Endpoints

### ✅ POST `/shorten`

**Request Body** (JSON):

```json
{
  "longUrl": "https://example.com/some/long/url",
  "customAlias": "myalias",
  "expiryDate": "2025-12-31T23:59:59"
}
```

**Response**:

```json
{
  "shortUrl": "http://localhost:8080/myalias",
  "expiryDate": "2025-12-31T23:59:59"
}
```

---

### 🔁 GET `/{shortCode}`

Redirects to the original long URL.

Example:  
`http://localhost:8080/myalias` → `https://example.com/some/long/url`

---

## 🗃️ Database

PostgreSQL stores:

- `id`, `short_code`, `long_url`
- `created_at`, `expiry_date`

Use pgAdmin, DBeaver, or Docker CLI to inspect the DB.

---

## 🧪 Testing

You can test endpoints using:

- Postman
- cURL

Example cURL:

```
curl -X POST http://localhost:8080/shorten \
-H "Content-Type: application/json" \
-d '{
  "longUrl": "https://google.com",
  "customAlias": "googl",
  "expiryDate": "2025-12-31T23:59:59"
}'
```

---

## 🌐 Deployment Options

- ✅ Localhost
- ✅ Dockerized
- ✅ Can be deployed to:
    - Render
    - Railway
    - Fly.io
    - Any VPS or cloud platform

---

## 🧑‍💻 Author

Made with ❤️ by Shubham

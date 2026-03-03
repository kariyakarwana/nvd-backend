This looks like a solid foundation for your repository. I’ve polished the formatting, added a touch of professional flair, and organized the sections to make it highly readable for anyone landing on your GitHub page.

---

# NVD Backend – Spring Boot REST API

This is a **cloud-native backend service** designed for the NVD system. Built with Spring Boot and secured via JWT authentication, it is fully containerized and features an automated CI/CD pipeline for seamless deployment to AWS.

---

## 🚀 Tech Stack

| Category | Technology |
| --- | --- |
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.x |
| **Security** | Spring Security, JWT (JSON Web Tokens) |
| **Persistence** | JPA / Hibernate, MySQL |
| **DevOps** | Docker, GitHub Actions, AWS EC2 |

---

## 🏗 System Architecture

<img width="2050" height="348" alt="mermaid-diagram-2026-03-03-141350" src="https://github.com/user-attachments/assets/18de2682-44d0-460c-91c6-cddbef2f13e5" />

## 🔐 Security Design

* **Stateless Authentication:** No session data is stored on the server; all identity info is contained within the JWT.
* **Custom Security Filter:** Intercepts every request to validate the Bearer token in the header.
* **Access Control:** * `/api/auth/**`: Publicly accessible for login and registration.
* `/**`: All other endpoints are locked down by default.


* **CORS Management:** Pre-configured to allow requests only from the production frontend domain.

---

## 🗄 Database ER Diagram


<img width="854" height="710" alt="nvd-ER" src="https://github.com/user-attachments/assets/a97585ae-c5f7-493f-835e-cec9cdf53578" />

---


## ⚙️ Environment Variables

The application requires the following variables, which are injected during the Docker deployment process:

| Variable | Description |
| --- | --- |
| `DB_URL` | JDBC connection string for MySQL |
| `DB_USERNAME` | Database user |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for signing tokens |
| `SPRING_PROFILES_ACTIVE` | Set to `prod` or `dev` |

---

## 🐳 Docker Deployment

To build and run the service locally using Docker:

```bash
# Build the image
docker build -t username/nvd-back:1.0 .

# Run the container
docker run -d -p 8080:8080 \
  -e DB_URL=your_url \
  -e JWT_SECRET=your_secret \
  username/nvd-back:1.0

```

---

## 🔄 CI/CD Pipeline (GitHub Actions)

The pipeline automates the entire lifecycle upon a push to the `master` branch:

1. **Build:** Compiles the code and runs tests.
2. **Package:** Creates a production-ready JAR file.
3. **Containerize:** Builds a Docker image and pushes it to Docker Hub.
4. **Deploy:** SSH into **AWS EC2**, pulls the latest image, and restarts the container.

---

## 📂 Repository Structure

```text
src/main/java/com/nvd/
 ├── controller/  # REST Endpoints
 ├── service/     # Business Logic
 ├── repository/  # Database Queries (Data Access)
 ├── security/    # JWT & Spring Security Config
 └── model/       # Entities & DTOs

```

---

## 📌 Future Improvements

* [ ] **Refresh Tokens:** Implement a refresh token rotation for better UX/Security.
* [ ] **RBAC:** Add fine-grained Role-Based Access Control.
* [ ] **Monitoring:** Integrate Docker health checks and Spring Boot Actuator.
* [ ] **Edge Security:** Deploy behind an Nginx Reverse Proxy with SSL (HTTPS).

---

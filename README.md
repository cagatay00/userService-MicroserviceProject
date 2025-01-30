# userService-MicroserviceProject

# User Service API 🛠️

This is a **Spring Boot microservice** for **user registration, authentication, and profile management**. It supports **JWT-based authentication**, integrates with **PostgreSQL**, and uses **RabbitMQ** for logging.

## 🚀 Features

- 🔐 **User Registration & Authentication** (JWT)
- 🔄 **Sign-out with Token Blacklisting**
- 🔑 **Password Change Support**
- 📝 **User Profile Fetching**
- 🗄️ **Dockerized with PostgreSQL & RabbitMQ**

---

## 📂 Project Structure

```
/userservice
│── src/                       # Source code
│   ├── main/
│   │   ├── java/com/example/userservice/  # Java backend
│   │   ├── resources/         # Configuration files
│   ├── test/                  # Unit tests
│── target/                    # Compiled JAR files (after mvn package)
│── Dockerfile                 # Docker build instructions
│── docker-compose.yml         # Defines containers & dependencies
│── pom.xml                    # Maven dependencies
│── README.md                  # This documentation file
```

---

## 🛠️ **Setup Guide**

### 1️⃣ **Prerequisites**

Ensure you have:

- **Java 17** (or later)
- **Maven** installed (`mvn -version`)
- **Docker & Docker Compose** installed (`docker --version`)

### 2️⃣ **Build the Project**

Run:

```sh
mvn clean package
```

This generates `target/userservice-0.0.1-SNAPSHOT.jar`.

### 3️⃣ **Run the Service with Docker**

Start the application, database, and message queue:

```sh
docker-compose up --build
```

The service will be available at:

```
http://localhost:8081/api/users
```

### 4️⃣ **Stop Everything**

To stop the running containers:

```sh
docker-compose down
```

---

## 🔗 **API Endpoints**

### 🏷 **User Authentication**

| Method | Endpoint              | Description                |
| ------ | --------------------- | -------------------------- |
| `POST` | `/api/users/register` | Register a new user        |
| `POST` | `/api/users/login`    | Authenticate & get a token |
| `POST` | `/api/users/signout`  | Logout (blacklist token)   |

### 👤 **User Management**

| Method | Endpoint                     | Description                |
| ------ | ---------------------------- | -------------------------- |
| `GET`  | `/api/users/profile`         | Get logged-in user profile |
| `POST` | `/api/users/change-password` | Change user password       |

---

## 📦 **Environment Variables**

| Variable Name                   | Description            |
| ------------------------------- | ---------------------- |
| `SPRING_DATASOURCE_URL`         | PostgreSQL URL         |
| `SPRING_DATASOURCE_USERNAME`    | PostgreSQL username    |
| `SPRING_DATASOURCE_PASSWORD`    | PostgreSQL password    |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Schema mode (`update`) |
| `SPRING_RABBITMQ_HOST`          | RabbitMQ host          |
| `SPRING_RABBITMQ_PORT`          | RabbitMQ port          |

These values are set inside `docker-compose.yml`.

---

## 🛠 **Development & Debugging**

1. To run without Docker, change \`\` to:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/userservice
spring.datasource.username=admin
spring.datasource.password=admin
```

2. Then start manually:

```sh
mvn spring-boot:run
```

---

## 🛡️ **Security**

- **Passwords are hashed** using BCrypt.
- **JWT Tokens** are used for authentication.
- **Token blacklist** prevents reuse of logged-out tokens.

---

## 📌 **Future Improvements**

- ✅ Add **email verification**
- ✅ Implement **refresh token mechanism**
- ✅ API Documentation with **Swagger/OpenAPI**

---

## 📝 **Contributing**

1. Fork the repo
2. Create a feature branch (`git checkout -b new-feature`)
3. Commit changes (`git commit -m "Add feature"`)
4. Push to branch (`git push origin new-feature`)
5. Open a Pull Request

---

## 📜 **License**

This project is licensed under the **MIT License**.

---

### **Made with ❤️ by Çağatay**


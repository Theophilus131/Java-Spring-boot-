# Flyway MySQL Migration - Spring Boot

## 📌 Project Overview

This project demonstrates **database migration using Flyway** in a Spring Boot application with MySQL.

The goal of this implementation is to show how database schema changes can be version-controlled, automated, and safely managed across environments.

---

## 🎯 Objective

To implement database migration using Flyway and demonstrate:

- Versioned SQL migration scripts
- Automatic execution of migrations on application startup
- Schema change tracking using `flyway_schema_history`
- Schema evolution using incremental versions

---

## 🛠 Tech Stack

- Java
- Spring Boot
- MySQL
- Flyway
- Maven

---

## ⚙️ Configuration

Flyway is configured in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/internship_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=none
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

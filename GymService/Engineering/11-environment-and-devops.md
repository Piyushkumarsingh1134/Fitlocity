# 11-environment-and-devops.md

# Fitlocity Platform — Environment Setup & DevOps (GymService Focus)

---

# 1. Overview

This document defines the environment setup, DevOps infrastructure, CI/CD pipeline, and deployment architecture used for the Fitlocity platform, with a primary focus on the GymService microservice.

GymService manages:

- Gym profiles
- Equipment inventory
- Membership tiers
- Promotional offers
- Analytics
- SaaS subscriptions

The DevOps architecture ensures the system is:

| Goal        | Description                     |
|------------|---------------------------------|
| Reliable    | Services remain available       |
| Scalable    | Handle increasing traffic       |
| Automated   | CI/CD automates deployments     |
| Secure      | Sensitive data is protected     |
| Observable  | Logs and metrics are available  |

---

# 2. Environment Architecture

The platform operates across multiple environments to support safe development and deployment.

## Environments

| Environment        | Purpose                  |
|-------------------|--------------------------|
| Local Development | Developer testing        |
| Staging           | Pre-production validation|
| Production        | Live system              |

Each environment uses separate:

- Configuration
- Database
- Infrastructure resources

---

# 3. Local Development Environment

Developers run GymService locally for development and testing.

## Development Stack

| Tool         | Purpose              |
|-------------|----------------------|
| Java 17      | Application runtime  |
| Spring Boot  | Backend framework    |
| PostgreSQL   | Database             |
| Flyway       | DB migrations        |
| Maven        | Build tool           |
| Docker       | Container runtime    |
| Postman      | API testing          |

## Setup Steps

### 1. Clone Repository

```bash
git clone https://github.com/fitlocity/gymservice.git
cd gymservice
```

### 2. Build Project

```bash
mvn clean install
```

### 3. Run Application

```bash
mvn spring-boot:run
```

### 4. Access API

```
http://localhost:8080/api/v1
```

---

# 4. Environment Configuration

Spring Boot manages environment configuration via profiles.

## Configuration Files

| File                    | Purpose                |
|-------------------------|------------------------|
| application.yml         | Default config         |
| application-dev.yml     | Development settings   |
| application-stage.yml   | Staging settings       |
| application-prod.yml    | Production settings    |

## Example Configuration

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fitlocity
    username: postgres
    password: password

server:
  port: 8080
```

Sensitive values must be overridden using environment variables.

---

# 5. Docker Containerization

GymService is packaged as a Docker container to ensure portability and consistency.

## Benefits

| Benefit      | Description                       |
|-------------|-----------------------------------|
| Portability  | Runs anywhere Docker is supported |
| Isolation    | Independent runtime environment   |
| Consistency  | Same setup across all environments|

## Example Dockerfile

```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gymservice.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
```

## Build Docker Image

```bash
docker build -t fitlocity/gymservice:1.0 .
```

---

# 6. Container Registry

Docker images are pushed to a container registry.

## Supported Registries

| Registry                  | Description               |
|---------------------------|---------------------------|
| Docker Hub                | Public registry           |
| AWS ECR                   | AWS managed registry      |
| Google Artifact Registry  | GCP managed registry      |

Example image tag:

```
fitlocity/gymservice:1.0
```

---

# 7. Deployment Architecture

## High-Level Flow

```
Client Applications
        │
Load Balancer
        │
API Gateway
        │
GymService Containers
        │
PostgreSQL Database
```

---

# 8. Kubernetes Deployment

GymService runs on Kubernetes for orchestration and scaling.

## Core Components

| Component  | Purpose               |
|-----------|-----------------------|
| Pod        | Running container     |
| Deployment | Manage replicas       |
| Service    | Internal networking   |
| Ingress    | External routing      |

## Example Deployment YAML

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: gymservice
spec:
  replicas: 3
  selector:
    matchLabels:
      app: gymservice
  template:
    metadata:
      labels:
        app: gymservice
    spec:
      containers:
      - name: gymservice
        image: fitlocity/gymservice:1.0
        ports:
        - containerPort: 8080
```

---

# 9. CI/CD Pipeline Overview

CI/CD automates build, test, and deployment.

## CI/CD Flow

```
Developer pushes code
        │
CI pipeline triggered
        │
Build application
        │
Run tests
        │
Build Docker image
        │
Push image to registry
        │
Deploy to Kubernetes
```

---

# 10. Continuous Integration (CI)

Ensures code quality and stability.

## CI Tasks

| Task               | Description              |
|-------------------|--------------------------|
| Compilation        | Build project            |
| Unit Tests         | Validate functionality   |
| Static Analysis    | Detect code issues       |
| Security Scanning  | Detect vulnerabilities   |

## CI Tools

| Tool            | Purpose         |
|-----------------|----------------|
| GitHub Actions  | Automation      |
| Jenkins         | Pipeline engine |
| GitLab CI       | Integrated CI/CD|

---

# 11. Continuous Deployment (CD)

Automates delivery to staging and production.

## CD Tasks

| Task                | Description               |
|--------------------|---------------------------|
| Build Docker image | Package application       |
| Push image         | Upload to registry        |
| Deploy containers  | Update Kubernetes pods    |
| Health checks      | Verify successful rollout |

---

# 12. Example GitHub Actions Pipeline

```yaml
name: GymService CI/CD

on:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3

      - name: Setup Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'

      - name: Build project
        run: mvn clean package

      - name: Build Docker image
        run: docker build -t fitlocity/gymservice .

      - name: Push image
        run: docker push fitlocity/gymservice
```

---

# 13. Database Migration

Database schema changes are managed using Flyway.

## Migration Directory

```
src/main/resources/db/migration
```

Example:

```
V1__create_gym_tables.sql
```

Migrations are automatically executed on service startup.

---

# 14. Monitoring & Observability

Ensures visibility into system health.

## Monitoring Tools

| Tool        | Purpose            |
|------------|--------------------|
| Prometheus  | Metrics collection |
| Grafana     | Metrics dashboards |
| ELK Stack   | Log aggregation    |

## Key Metrics

| Metric              | Description           |
|--------------------|-----------------------|
| API response time   | Performance           |
| Error rate          | Reliability           |
| CPU usage           | Resource utilization  |
| Throughput          | Traffic volume        |

---

# 15. Logging Architecture

## Logging Flow

```
Application Logs
        │
Log Collector
        │
Centralized Log System
        │
Visualization Dashboard
```

## Logging Tools

| Tool           | Purpose             |
|---------------|---------------------|
| Logstash       | Log processing      |
| Elasticsearch  | Log storage         |
| Kibana         | Log visualization   |

---

# 16. Security in DevOps

## Security Practices

| Practice            | Description              |
|--------------------|--------------------------|
| Secrets Management  | Secure credentials       |
| HTTPS Encryption    | Secure communication     |
| RBAC                | Role-based access        |
| Image Scanning      | Secure container images  |

Secrets may be stored in:

- Kubernetes Secrets
- AWS Secrets Manager
- HashiCorp Vault

---

# 17. Backup & Disaster Recovery

## Backup Strategy

| Backup Type         | Frequency |
|--------------------|----------|
| Full backup         | Daily    |
| Incremental backup  | Hourly   |

## Recovery Scenarios

| Scenario          | Solution                  |
|------------------|---------------------------|
| Database failure  | Restore from backup       |
| Container crash   | Restart pod               |
| Node failure      | Kubernetes rescheduling   |

---

# 18. Scaling Strategy

## Horizontal Scaling

```
1 pod → 3 pods → 10 pods
```

## Scaling Triggers

| Metric             | Action              |
|-------------------|--------------------|
| CPU > 70%          | Increase replicas   |
| High traffic       | Add additional pods |

---

# 19. Deployment Workflow Example

```
Developer commits code
        │
Repository
        │
CI triggered
        │
Docker image built
        │
Image pushed to registry
        │
Kubernetes deployment updated
        │
New pods running
```

---

# 20. Summary

The Fitlocity DevOps architecture provides a reliable, scalable, and automated infrastructure for the GymService module.

It includes:

- Environment separation
- Containerized deployment
- CI/CD automation
- Monitoring and centralized logging
- Security best practices
- Backup and disaster recovery
- Horizontal scaling support

This ensures the GymService backend remains production-ready and capable of supporting large-scale platform growth.
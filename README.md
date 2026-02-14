This is a [Spring Boot](https://spring.io/projects/spring-boot) project.

## Implemented Functionalities

- **Containerization & Deployment**:
    - Docker support via `docker-compose.yaml`, `docker-compose.demo.yaml` and `Dockerfile`
    - Automated validation for Pull Request using GitHub Actions (`pre-merge.yml`)

- **CI/CD Flow**:
    - Open a pull request to trigger the CI/CD pipeline for validation
    - Merge to `develop` branch to deploy to development environment

## Getting Started

### Prerequisites

Make sure you have the following installed:

- [Java 25](https://adoptium.net/pt-BR/temurin/releases?version=25&mode=filter&os=any&arch=any)
- [Docker](https://www.docker.com/get-started)

1. Clone the repository:

   ```bash
   git clone https://github.com/jilds/adventure-book.git
   ```
2. Navigate to the project directory:

   ```bash
   cd adventure-book
   ```

### Build the Application

To run locally Maven, ensure you have `Java`  installed and follow these steps:

1. Run the maven build:

    ```bash
    .\mvnw clean install
    ```

   To skip unit test run:

   ```bash
   .\mvnw clean install -DskipTests
   ```

### Run using docker compose:

To run locally using Docker, ensure you have [Docker](https://www.docker.com/get-started) installed and follow these
steps:

1. Build the Docker image and start it:

   ```bash
   docker compose -f docker-compose.demo.yaml up -d
   ```

2. To open the Open API Doc Page please, click on the link bellow:

   [adventure-book](http://localhost:8080/swagger-ui/index.html)

### Run using Maven Spring Boot

1. Setup data base:

    ```bash
   docker compose up -d
   ```

2. Run the application:
   ```bash
   .\mvnw spring-boot:run
   ```

### Environment Variables

To run on cloud using Docker, ensure you have setup the following environment variables:

1. To set the system environment, use one of the following values:
   ```bash
   env SYSTEM_ENV            = dev, test, prod
   ```
   only test env is supported for now.

2. To connect to a database, set the following environment variables:

   ```bash
   env DB_URL                = url to connect to the database
   env DB_USERNAME           = username to connect to the database
   env DB_PASSWORD           = password to connect to the database
   ```
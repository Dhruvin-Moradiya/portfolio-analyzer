# Portfolio Analyzer

A Java Spring Boot application for analyzing GitHub portfolios.

## Prerequisites
- Java 17 or higher (Java 21 recommended)
- Maven (or use the provided Maven Wrapper: `./mvnw`)

## Setup

### 1. Clone the repository
```sh
git clone https://github.com/Dhruvin-Moradiya/portfolio-analyzer.git
cd portfolio-analyzer
```

### 2. Install dependencies
```sh
./mvnw clean install
```

### 3. Build the application
```sh
./mvnw clean package
```

### 4. Run the application
```sh
./mvnw spring-boot:run
```
Or run the generated jar:
```sh
java -jar target/portfolio-analyzer-0.0.1-SNAPSHOT.jar
```

### 5. Run tests
```sh
./mvnw test
```

### 6. Check code coverage (JaCoCo)
- Ensure JaCoCo is configured in `pom.xml` (see below)
- Run:
```sh
./mvnw clean test
```
- Open the report:
```
target/site/jacoco/index.html
```
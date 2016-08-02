# aries
Much like my beater car from the 80's, Aries is a beater Dropwizard application which can be used to simulate healthcheck failures, slow response times, and more for testing purposes

## Build & Run

1. Download and install [maven](https://maven.apache.org/download.cgi)
2. mvn clean install
3. docker-compose build
4. docker-compose up

## API

### Application - Port 8080

#### GET /status?minResponseDelayMillis=x&maxResponseDelayMillis=y

Request status with immediate response
```bash
curl -v "localhost:8080/status"
```

```json
{"healthy":true,"callCount":1,"responseDelayMillis":0}
```

Request status with a delay between 2 and 5 seconds

```bash
curl "localhost:8080/status?minResponseDelayMillis=2000&maxResponseDelayMillis=5000"
```

```json
{"healthy":true,"callCount":1,"responseDelayMillis":3862}
```

#### POST /status

Simulate healthcheck failure
```bash
curl -X POST -d '{"healthy":false}' -H "Content-Type: application/json" localhost:8080/status;
```

```json
{"healthy":false,"callCount":0,"responseDelayMillis":0}
```
Resume healthcheck success
```bash
curl -X POST -d '{"healthy":true}' -H "Content-Type: application/json" localhost:8080/status;
```

```json
{"healthy":true,"callCount":0,"responseDelayMillis":0}
```

### Admin - Port 8081

#### GET /healthcheck - Healthcheck endpoint. Returns 200 on success, 500 on failure
```bash 
curl "localhost:8081/healthcheck"
```

```json
{"countingHealthcheck":{"healthy":true,"message":"Healthy. Count: 1"},"deadlocks":{"healthy":true}}
```

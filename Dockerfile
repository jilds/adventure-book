# Stage 1: Build the application
FROM eclipse-temurin:25-jre-alpine AS builder
USER root
WORKDIR /builder
COPY target/*.jar application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher --destination extracted

# Stage 2: Run the application
FROM eclipse-temurin:25-jre-alpine
LABEL com.jilds.interview.adventurebook.app.name="Adventure Book"
WORKDIR /usr/local/app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser


COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher", "--spring.profiles.active=${SYSTEM_ENV}"]
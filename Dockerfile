FROM maven:3.8.5-openjdk-17 AS builder
RUN mkdir -p /app/src/main/java
COPY pom.xml /app/
COPY .mvn /app/.mvn
COPY src /app/src
WORKDIR /app/
RUN mvn clean package -DskipTests

FROM --platform=linux/amd64 openjdk:17-jdk-slim
ENV ARCHIVE_FILE_NAME="ing-brokage-api-1.0.0.jar"
RUN mkdir /app/
WORKDIR /app/
RUN echo "#!/bin/sh\njava -jar ${ARCHIVE_FILE_NAME}" > entrypoint.sh
RUN chmod +x entrypoint.sh
COPY --from=builder /app/target/ing-brokage-api-1.0.0.jar /app/$ARCHIVE_FILE_NAME
EXPOSE 8080
ENTRYPOINT ./entrypoint.sh
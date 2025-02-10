FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN apk add --no-cache bash coreutils
RUN chmod +x ./mvnw
RUN export $(grep -v '^#' .env | xargs) && ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/a.jar"]

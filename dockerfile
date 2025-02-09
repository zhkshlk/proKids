FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN chmod +x ./mvnw
RUN ./mvnw clean package
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

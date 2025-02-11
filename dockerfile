FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN apk add --no-cache bash coreutils
RUN apk add --no-cache dos2unix
RUN dos2unix ./wait-for-it.sh
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/a.jar"]

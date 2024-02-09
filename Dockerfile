FROM openjdk:17-slim as build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN ./gradlew build -x test

FROM openjdk:17-slim

EXPOSE 8080

COPY --from=build /app/build/libs/media-api-0.0.1-SNAPSHOT.jar /app/media-api-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/media-api-0.0.1-SNAPSHOT.jar"]
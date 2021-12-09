FROM openjdk:16-alpine3.13 AS MAVEN_BUILD

WORKDIR /build

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src/ src
RUN ./mvnw clean package

FROM openjdk:16-alpine3.13

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/poke-challenge-*.jar /app/poke-challenge.jar

ENTRYPOINT ["java", "-jar", "/app/poke-challenge.jar"]
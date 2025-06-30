FROM openjdk:21-jdk

LABEL authors="suxita"
WORKDIR /app
COPY target/boredreader-0.0.1-SNAPSHOT.jar app.jar
ENV ANTHROPIC_API_KEY=""
CMD ["java","-jar", "/app/app.jar"]
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY /target/TestForJavaCode-spring-boot.jar service.jar
ENTRYPOINT ["java", "-jar", "service.jar"]
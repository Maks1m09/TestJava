FROM openjdk:11-jdk-slim-bullseye
WORKDIR /app
COPY /out/artifacts/Test_jar/Test.jar /app/testTask.jar
ENTRYPOINT ["java", "-jar", "testTask.jar"]
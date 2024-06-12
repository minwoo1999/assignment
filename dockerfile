FROM openjdk:17
ENV TZ=Asia/Seoul
ARG JAR_FILE=build/libs/flow-global-assignment-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "app.jar"]
EXPOSE 8080
# 1. 빌드 스테이지
FROM amazoncorretto:17 AS build
COPY . /home/app
WORKDIR /home/app
# gradlew에 실행 권한 부여 및 빌드
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# 2. 실행 스테이지
FROM amazoncorretto:17-alpine
EXPOSE 8080
COPY --from=build /home/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
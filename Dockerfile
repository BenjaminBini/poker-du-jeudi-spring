FROM maven:3.6.3-jdk-14 as build

RUN mkdir -p /data/poker-du-jeudi
WORKDIR /data/poker-du-jeudi
COPY pom.xml .
RUN mvn dependency:resolve
COPY src src
RUN mvn install -Dmaven.test.skip=true

FROM adoptopenjdk:14-jdk-hotspot

COPY --from=build /data/poker-du-jeudi/target/*.jar /data/poker-du-jeudi/app.jar

ENTRYPOINT ["java","-jar","/data/poker-du-jeudi/app.jar"]
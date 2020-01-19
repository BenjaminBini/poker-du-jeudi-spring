FROM adoptopenjdk:13-jdk-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV LANG fr_FR.UTF-8
ENV LANGUAGE fr_FR:en
ENV LC_ALL fr_FR.UTF-8
RUN sed -i -e 's/# fr_FR.UTF-8 UTF-8/fr_FR.UTF-8 UTF-8/' /etc/locale.gen && \
    locale-gen
ENTRYPOINT ["java","-jar","/app.jar"]
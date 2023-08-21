FROM gradle:8.1.1-jdk17 AS build
LABEL mantainer="devops_ampli"
WORKDIR /code
COPY . /code
RUN gradle buildDependents
RUN gradle build
RUN ls build/libs/

FROM openjdk:21-jdk-slim

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.12.0/opentelemetry-javaagent.jar /
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.12.0/jmx_prometheus_javaagent-0.12.0.jar /

COPY ["prometheus-jmx-config.yaml", "/prometheus-jmx-config.yaml"]
COPY --from=build ["/code/build/libs/*.jar", "/app.jar"]

CMD ["/app.jar"]
EXPOSE 8080 9090

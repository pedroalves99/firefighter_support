FROM openjdk:11-jre-slim as builder
WORKDIR application
COPY target/rest-service-0.0.1-SNAPSHOT.jar /usr/app/rest-service.jar

RUN java -Djarmode=layertools -jar /usr/app/rest-service.jar extract

FROM openjdk:8-jre-slim
WORKDIR application	
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
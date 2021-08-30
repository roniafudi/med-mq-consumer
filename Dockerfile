FROM amazoncorretto:8
WORKDIR /opt/consumer
COPY ./consumer/build/libs/Consumer*.jar consumer.jar
ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "consumer.jar"]
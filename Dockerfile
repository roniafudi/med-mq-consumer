FROM amazoncorretto:8
WORKDIR /opt/med-mq-consumer
COPY ./build/libs/med-mq-consumer-0.0.1-SNAPSHOT.jar med-mq-consumer.jar
ENTRYPOINT ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "med-mq-consumer.jar"]
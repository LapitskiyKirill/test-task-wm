FROM openjdk:11
COPY target/challenge-2.4.4.jar consumer
ENTRYPOINT ["java","-jar","consumer"]
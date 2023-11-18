FROM openjdk:latest
VOLUME ["/tmp"]
EXPOSE 8080
ADD ./target/backenduni.jar backenduni-aws.jar
ENTRYPOINT ["java","-jar","backenduni-aws.jar"]
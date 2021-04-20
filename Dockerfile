FROM openjdk:11.0-jdk
COPY ./target/proposta.jar /www/java/
WORKDIR /www/java/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "proposta.jar"]
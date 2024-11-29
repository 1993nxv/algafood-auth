FROM openjdk:24-ea-12-jdk-slim

WORKDIR /oauth

COPY target/*.jar /oauth/algafood-oauth.jar

EXPOSE 8181

CMD ["java", "-jar", "algafood-oauth.jar"]
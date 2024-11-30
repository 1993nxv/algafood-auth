FROM openjdk:24-ea-12-jdk-slim

WORKDIR /oauth

COPY target/*.jar /oauth/algafood-oauth.jar
COPY wait-for-it.sh  /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8181

CMD ["java", "-jar", "algafood-oauth.jar"]
FROM maven:3.9.9-amazoncorretto-23-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:23
WORKDIR /app

COPY --from=build ./build/target/*.jar ./app.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_PASSWORD='admin'
ENV DATASOURCE_URL='jdbc:mysql://localhost:3306/tododb'
ENV DATASOURCE_USERNAME='admin'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar app.jar
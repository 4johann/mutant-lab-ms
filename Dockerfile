FROM openjdk:8-jre-alpine

ENV TIMEZONE=America/Bogota
ENV APP_LOCALE=es
ENV REDIS_PORT=6379
ENV REDIS_HOST=192.168.2.3
ENV DB_HOST=192.168.2.3
ENV DB_PORT=5432
ENV DB_NAME=test
ENV DB_USER=tester
ENV DB_PASSWORD=test123
ENV MAX_POOL_SIZE=5
ENV SWAGGER_HOST=localhost
EXPOSE 8080
RUN mkdir /app
COPY app/build/libs /app

ENTRYPOINT [ "sh", "-c", "java -jar -Dspring.profiles.active=prod -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=10 /app/app-boot.jar" ]
FROM openjdk:8-jre-alpine

RUN mkdir /code
COPY app/build/libs /code

ENTRYPOINT [ "sh", "-c", "java -jar -Dspring.profiles.active=prod -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=10 /code/app-boot.jar" ]
FROM gradle:7.4.2-jdk17

WORKDIR /app

COPY /app .

RUN gradle installDist

USER root
RUN chmod 755 ./build/libs/app-1.0-SNAPSHOT.jar

CMD ./build/install/app/bin/app

FROM gradle:7.4.2-jdk17

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD sudo ./build/libs/app-1.0-SNAPSHOT.jar

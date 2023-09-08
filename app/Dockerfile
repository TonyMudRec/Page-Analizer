FROM gradle:7.4.2-jdk17

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD ./build/install/app/lib/app-1.0-SNAPSHOT.jar

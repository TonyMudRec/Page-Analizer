FROM gradle:7.4.2-jdk17

WORKDIR /app

COPY /app .

RUN ./gradlew --no-daemon dependencies

COPY src src
COPY config config

RUN ./gradlew --no-daemon build

RUN gradle installDist

CMD ./build/libs/app-1.0-SNAPSHOT.jar

FROM gradle:7.4.2-jdk17

WORKDIR /app

COPY . .

RUN ./gradlew dependencies

RUN ./gradlew build

RUN gradle installDist

CMD ./build/libs/app-1.0-SNAPSHOT.jar

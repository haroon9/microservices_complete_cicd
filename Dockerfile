FROM gradle:5.4.1-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src/
WORKDIR /home/gradle/src/
RUN gradle clean --no-daemon
RUN gradle build --no-daemon 
EXPOSE 2222
CMD gradle run
FROM alpine:latest

# add gradle package, also installs openjdk et al
RUN apk add gradle 

WORKDIR /app/charts

COPY . .

RUN gradle build

# TODO: check if this is the correct way
CMD gradle run


# FROM gradle:jdk8 as builder
# # RUN mkdir /home/app
# # COPY . /home/app/
# WORKDIR /app/charts
# COPY . .
# RUN gradle build

# FROM openjdk:8-jre-slim
# RUN mkdir /home/app
# WORKDIR /home/app
# COPY --from=builder /app/charts .
# CMD gradle run
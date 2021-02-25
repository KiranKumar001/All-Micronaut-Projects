FROM ghcr.io/graalvm/graalvm-ce:java11-21.0.0.2 as graalvm
RUN gu install native-image

COPY . /home/app/micronaut-kafka
WORKDIR /home/app/micronaut-kafka

RUN native-image --no-server -cp build/libs/micronaut-kafka-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
# No rest api, so no port to expose
# EXPOSE 8080
COPY --from=graalvm /home/app/micronaut-kafka/micronaut-kafka /app/micronaut-kafka

#RUN ["chmod", "+x", "/app/micronaut-kafka"]
ENTRYPOINT ["/app/micronaut-kafka"]

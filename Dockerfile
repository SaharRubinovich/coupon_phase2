FROM openjdk:16-alpine
COPY ./target/coupon2-0.0.1-SNAPSHOT.jar /usr/app
WORKDIR /usr/app
RUN sh -c 'touch coupon2-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","coupon2-0.0.1-SNAPSHOT.jar"]
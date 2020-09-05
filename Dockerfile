FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
MAINTAINER  Maxim Morev <maxmorev@gmail.com>
RUN mkdir /opt/micro \
RUN mkdir -p /opt/micro/src/main \
&& chown -R 1001 /opt/micro \
&& chmod u=rwx,g=rx /opt/micro \
&& chown -R 1001:root /opt/micro
# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
WORKDIR /opt/micro
COPY src/main/webapp /opt/micro/src/main/webapp
COPY build/libs/eshop-web-v2-*.jar /opt/micro/app.jar
RUN chown -R 1001:root /opt/micro
EXPOSE 8080
USER 1001
CMD ["java", "-jar", "app.jar"]

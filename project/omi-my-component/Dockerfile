FROM openjdk:11.0.3-jre-slim-stretch
ENV JAVA_OPTS -Xmx256m -Xms256m -XX:MaxMetaspaceSize=200m
COPY target/*.jar app.jar
EXPOSE 80
CMD java ${JAVA_OPTS} -Dserver.port=80 -Dspring.config.additional-location=file:/codenow/config/ -Dlogging.config=classpath:logback-codenow.xml -jar app.jar

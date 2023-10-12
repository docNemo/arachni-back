FROM openjdk:20-jdk-oraclelinux7
COPY arachni-back-app/target/arachni-back.jar arachni-back.jar

ENV JAVA_OPTS = ""

EXPOSE 8080
CMD ["sh", "-c", "java -jar arachni-back.jar"]
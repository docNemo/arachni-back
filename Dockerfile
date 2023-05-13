FROM openjdk:20-jdk-oraclelinux7
COPY target/article-store.jar article-store.jar

ENV JAVA_OPTS = ""

EXPOSE 8080
CMD ["sh", "-c", "java -jar article-store.jar"]
FROM openjdk:8-jdk
EXPOSE 8080
RUN export LC_ALL=en_US.UTF-8
RUN export LANG=en_US.UTF-8
RUN export LC_TIME=th_TH.UTF-8
RUN apt-get clean && apt-get -y update && apt-get install -y locales && locale-gen en_US.UTF-8 && locale-gen th_TH.UTF-8
RUN locale-gen en_US.UTF-8
ENV TZ=Asia/Bangkok
ARG JAR_FILE=target/APIBackendTestApplication.war
COPY ${JAR_FILE} APIBackendTestApplication.war
ENTRYPOINT ["java","-jar","-Dspring.datasource.url=jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=master;user=sa;‌​password=P@ssw0rd","-Dserver.port=8080","-Dspring.jpa.show-sql=false","-Dspring.jpa.hibernate.ddl-auto=update","-Dspring.boot.admin.client.url=http://localhost:8008","/APIBackendTestApplication.war"]

FROM openjdk:8-jdk
EXPOSE 8080
RUN export LC_ALL=en_US.UTF-8
RUN export LANG=en_US.UTF-8
RUN export LC_TIME=th_TH.UTF-8
RUN apt-get clean && apt-get -y update && apt-get install -y locales && locale-gen en_US.UTF-8 && locale-gen th_TH.UTF-8
RUN locale-gen en_US.UTF-8
ENV TZ=Asia/Bangkok
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ARG JAR_FILE=build/APIBackendTestApplication-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} APIBackendTestApplication.jar
ENTRYPOINT ["java","-jar","-Dserver.port=8080","/APIBackendTestApplication.jar"]

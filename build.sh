#mvn clean compile package -Dmaven.test.skip=true
#mv target/engine-0.0.1-SNAPSHOT.jar build/
docker rm -f app db
docker build -t api_backend .
docker-compose up
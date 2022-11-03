# webCalculator
This is an implementation of a calculator web application created to run on Docker.

To build the app:
./mvnw clean install spring-boot:run

Use the following command to set up Docker containers:

docker network create our-network
docker run --name=mongo-container --rm -d --network=our-network mongo
docker build -t inotes .
docker run --name=inotes-container --rm -d -p 8080:8080 --network=our-network inotes

# webCalculator
This is an implementation of a calculator web application created to run on Docker.

To run the release from Docker hub:
Use this command to pull the image to your docker
> docker pull groomycat/interactive_calculator:latest

Create network 'our-network' 
> docker network create our-network

Create mongo DB container:
> docker run --name=mongo-container --rm -d --network=our-network mongo

Creating the container 'calculator' from our 'groomycat/interactive_calculator' image 
> docker run --name=calculator  -d -p 8080:8080 --network=our-network groomycat/interactive_calculator




To build the app from scratch:
./mvnw clean install spring-boot:run

Use the following command to set up Docker containers:
> docker network create our-network
> docker run --name=mongo-container --rm -d --network=our-network mongo
> docker build -t inotes .
> docker run --name=inotes-container --rm -d -p 8080:8080 --network=our-network inotes

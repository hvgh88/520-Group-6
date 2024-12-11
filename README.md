# UMass Hangout

## Introduction
Students often struggle to make meaningful connections and find peers with shared interests, leading to feelings of isolation. The UMass Hangout application addresses this by enabling students to create or join interest-based groups, facilitating flexible social interactions and real- time communication for organizing activities. The objective of the UMass Hangout web application is to create a
platform that facilitates student interactions, encourages group activities, and enhances the overall campus experience. By connecting students with shared interests and availability, UMass Hangout aims to  foster a sense of community and belonging among UMass students.

## Configuration Information
### Prerequisites:
1. Java openjdk version 1.8
2. Docker
3. Maven
4. Node/NPM
5. MySQL

### Steps to run chat-app backend service:
1. MySQL - Once MySQL is up and running. Create a database named `chat-app-db`
2. Run an ElasticSearch instance on docker in the following manner:
a. `docker pull docker.elastic.co/elasticsearch/elasticsearch:7.17.9`
b. `docker run -p 9200:9200 -e "discovery.type=single-node" \
   -e "http.cors.enabled=true" \
   -e "http.cors.allow-origin=*" \
   -e "http.cors.allow-headers=X-Requested-With,X-Auth-Token,Content-Type,Content-Length,Authorization" \
   -e "http.cors.allow-credentials=true" \
   docker.elastic.co/elasticsearch/elasticsearch:7.17.9`

3. Make sure you are able to access http://localhost:9200 successfully
4. Navigate to chat-app/src/main/resources/application.properties and set the `spring.datasource.username` to your personal MySQL username and `spring.datasource.password` to your MySQL password for the user specified.
5. Run the application in the directory chat app by using the command `mvn spring-boot:run`. You could alternatively run using a compatible IDE by running the `Main.java` file of the project

### Steps to run user-registration backend service
1. Verify that MySQL is set up, Create a database named `umass_hangout` 
2. Create a user/password as `umasstudent/umasstudent` in the db or navigate to user-registration/src/main/resources/application.properties and set the `spring.datasource.username` to your personal MySQL username and `spring.datasource.password` to your MySQL password for the user specified.
3. Run the application in the directory chat app by using the command `mvn spring-boot:run`. You could alternatively run using a compatible IDE by running the `Main.java` file of the project. 
4. The service is set to run on port 8080, modify in application.properties if needed to run on different port.

### Steps to run front end application
1. Install the dependencies in the frontend service in the folder frontend-handout by running the command `npm i`
2. Verify that the backend service urls are properly configured in config.js and the backend services are up and running.
7. Run the frontend service by running the command `npm start`

### Verifying that everything runs correctly
1. Should be able to access the application at http://localhost:3000/login
2. Should be able to view tables created as defined in the project on MySQL (Use commands - `SHOW TABLES;`)
3. Verify the logs of chat-app and ElasticSearch image to ensure there are no errors

### Video Demo Link
[Link](https://drive.google.com/file/d/1P4NJJcGvSgjgb7j4wXQkQFdlVKuBQBua/view?usp=sharing)






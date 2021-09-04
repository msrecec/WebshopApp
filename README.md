# Webshop assignment

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/project-material/assignment_1.png)

# Models
![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/project-material/assignment_2.png)
# Prerequisites
### Mandatory:
Postgres  12+
Java 16
Docker
Maven 3.8.2
### Optional:
pgAdmin 4+



# Postgres

DDL files for the postgres database are located in <b>/database-files/ddl.txt</b>
<b>To run the project you must create database with the name "webshop" and create tables via the contents of ddl.txt file </b>

## Running Postgres with Docker

To run postgres with docker run: <code> docker pull postgres</code> in command prompt (if you are on windows) or terminal (if you are on linux/mac)

To start the docker container run: <code>docker run -d -p 3000:5432 --rm --name postgres -e POSTGRES_PASSWORD=admin postgres
</code>

### Optional: Connect with pgAdmin
For easier use connect to postgres container via pgAdmin with <b>username: postgres and password: admin</b>
and copy paste contents of ddl.txt file into pgAdmin and run the query.

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_1.png)


![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_2.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_3.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_4.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_5.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_6.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_7.png)

![enter image description here](https://raw.githubusercontent.com/msrecec/WebshopApp/master/database-files/pg_8.png)

# Wiremock

To run wiremock go to /wiremock subfolder and run:
<code>java -jar wiremock-jre8-standalone-2.30.1.jar  --port 8081 --verbose</code>

# Build the app

in root folder of project run <code>mvn package</code> to build the project


# Running the app

To run the app go to root folder of this project and run:

<code>./mvnw spring-boot:run</code>

Or build the app and go to /target folder and run:

<code> java -jar webshop-0.0.1-SNAPSHOT.jar</code>

# Postman

If you are using Postman to test the API you can import the collection from /postman folder with api calls

# Swagger

To use Swagger-UI run the project and go to: <b> http://localhost:8080/swagger-ui </b>
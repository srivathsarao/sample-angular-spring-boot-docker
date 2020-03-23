# Running the Application

Docker needs to be installed in the machine.

"docker-compose up" should start up the angular and Spring boot applications.

And opening the url [http://localhost](http://localhost) (using docker-desktop) or 192.168. 99.100 (using docker-toolbox).

If any issue arises due to port. Changing the port in docker-compose.yml (line 6) should be sufficient.

# Components

![alt text](https://raw.githubusercontent.com/srivathsarao/sample-angular-spring-boot-docker/master/architecture.png)


**transaction-ui** Contains the Angular 8 code. Bootstrap, NGRX, RxJs, Ag-grid.

Ag-grid is used to both generate the table in the UI and to export the contents as csv.

Docker image runs with nginx server and statically hosted files of angular from dist folder of ng build output.

Nginx does the reverse proxy of the services using nginx.conf

Karma and Jasmine for unit testing.

**transaction-service**

Built using spring boot. **Spring data jpa** (to fetch the database values using Example.of).

It connects to embedded h2 database. H2 console is also enabled.

At the startup, the **Spring batch** reads the data line by line and inserts in the database.

The reason for using spring batch is because, it helps in loading large dataset and it can be integrated with quarts or any other frameworks to run at certain time.

**Lombok** is used to make the model classes and logging simplified.

# Future improvements

**Include cucumber** to do integration tests with urls.

Add **JenkinsFile** and test in Jenkins.

Needs to do **server side generation of CSV** files for high datasets.

Feed the data using **Kafka** to spring boot and integrate it with **Spring cloud streams**.

If more services are added, API gatway like zuul is required and code can be movied to **Kubernetes**.

**Authentication and authorization can be added using OAUTH2** **static scanning and dynamic scanning** can be added for application security and make it follow **OWASP Standards**.
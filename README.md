# Mini-Loan Application Service and Client App

## Task Description

The task is two-fold:
1. Implement a [Spring Boot](https://spring.io/projects/spring-boot)-based backend web service to handle and store loan applications for would-be borrowers. 
2. Implement a minimal [Angular](https://angular.io/) app which allows for the creation of a new loan application together with the ability to verify the presence of potentially already-submitted loan applications.

### Part 1: RESTful Service &mdash; Requirements
The service must make available, at least, the following two endpoints:
1. A ```POST``` endpoint to process a loan application request capable of handling a JSON payload (provided below: Loan Application Request JSON Payload) containing the required information pertaining to the loan itself together with information in relation to the borrowers. A loan application can include one or more borrowers. The service must store the submitted loan applications for, at least, as long as the service is running. Finally, the endpoint should return a response that includes the identifier of the newly created loan application.
2. A ```GET``` endpoint that confirms either the presence or absence of a loan application by means of the provided identifier.

In total, the service includes six endpoints (including the two required endpoints mentioned above):
- ```GET /api/v1/loans/{id}``` (required)
- ```PUT /api/v1/loans/{id}```
- ```DELETE /api/v1/loans/{id}```
- ```GET /api/v1/loans```
- ```POST /api/v1/loans``` (required)
- ```GET /api/v1/loans/{id}/borrowers```

#### Loan Application Request JSON Payload

```json
{
   "lanetakere":[
      {
         "fnr":"01056000069",
         "navn":"Kari Nordmann"
      },
      {
         "fnr":"01056000301",
         "navn":"Ola Nordmann"
      }
   ],
   "lanebelop":2450000,
   "behov":"Vi skal låne penger til........",
   "lopetid":300,
   "avdragsfriPeriode":12,
   "type":"annuitet"
}
```
#### Building and Running the Service (with Linux)
Execute the following command in the Spring Boot service's top-level directory (that is, _service_):

```./mvnw package``` 

Run the following command after successfully executing the above command:

```java -jar target/loan-0.0.1-SNAPSHOT.jar```

#### OpenAPI Documentation
Swagger-based OpenAPI documentation is available for the Spring Boot service, here: ```http://localhost:8080/swagger-ui.html```

#### Logging
A loan application request is logged to the standard output (console) by the service.

#### Additional Dependencies
In addition to the required dependencies for the actual Spring Boot framework, the following dependencies (Maven coordinates) have been included:
- ```org.springframework.boot:spring-boot-starter-hateoas```
- ```org.springdoc:springdoc-openapi-ui:1.5.10```
- ```com.h2database:h2```
- ```org.jetbrains:annotations:22.0.0```

The ```spring-boot-starter-hateoas``` dependency was included to help with creating hypermedia-driven representations that follow the [HATEOAS](https://restcookbook.com/Basics/hateoas/) principle.

The ```springdoc-openapi-ui``` dependency was added to automatically generate OpenAPI-based API documentation which can easily be visualized and interacted with by means of the [Swagger UI](https://swagger.io/tools/swagger-ui/).

The ```h2``` dependency &mdash;an [in-memory database](https://www.h2database.com/html/main.html)&mdash; was added to allow for the persistence of the loan applications while the service is running. 

Finally, the ```annotations``` dependency has been included so that the integrated development environment (that is, [IntelliJ IDEA](https://www.jetbrains.com/idea/)) will generate appropriate warnings if *null* checks are missing.

### Part 2: Angular Client App &mdash; Requirements
Pending.

## Miscellaneous Comments and Considerations
- **Transaction management**: The aim of lazy loading is to save resources by not loading related objects into memory when the main object is loaded. Instead, the initialization of lazy entities is postponed until the moment they're needed. When retrieving lazily-loaded data, there are two steps in the process. First, there's populating the main object, and second, retrieving the data within its proxies. Loading data always requires an open *session* in [Hibernate](https://hibernate.org/). The problem arises when the second step happens after the transaction has closed, which leads to a ```LazyInitializationException```.
    - Lazy loading with automatic transactions with the ```spring.jpa.properties.hibernate.enable_lazy_load_no_trans``` property. Turning this on means that **each fetch of a lazy entity will open a temporary session and run inside a separate transaction** probably resulting in the [N + 1 issue](https://vladmihalcea.com/n-plus-1-query-problem/).
    - Lazy loading with a surrounding transaction 
- The application's service is both implemented with Java 16 and targets Java 16, as well. If this was an actual production 
service, it would have made more sense to use a [Java LTS release](https://www.oracle.com/java/technologies/java-se-support-roadmap.html) instead &mdash; 
currently either Java 11 or, the very recently released, Java 17.
- In relation to [GDPR](https://gdpr-info.eu/) considerations, personal data of the borrowers is not logged when receiving a loan application request.
- For testing purposes, upon startup, the provided loan application is added to the service's repository.
- The H2 database also provides the ability to access a database using a browser. If you started the server on the same computer as the browser, open the URL ```http://localhost:8080/h2``` and provide the driver class of the database, the JDBC URL and user credentials to log in.

## Relevant Resources
- [What is HATEOAS?](https://dzone.com/articles/rest-api-what-is-hateoas)
- [HAL - Hypertext Application Language](https://stateless.group/hal_specification.html)
- [Best Practices in API Design](https://swagger.io/resources/articles/best-practices-in-api-design/)

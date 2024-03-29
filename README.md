# Mini-Loan Application Service and Client App

## Task Description

The task is two-fold:
1. Implement a [Spring Boot](https://spring.io/projects/spring-boot)-based backend web service to handle and store loan applications for would-be borrowers. 
2. Implement a minimal [Angular](https://angular.io/) app which allows for the creation of new loan applications together with the ability to check the status of already-submitted loan applications.

> Side note: For the sake of consistency, English is being used throughout the project (including documentation, source code and JSON keys).

### Part 1: RESTful Service &mdash; Requirements
The service must make available, at least, the following two endpoints:
1. A `POST` endpoint to process a loan application request capable of handling a JSON payload (provided below: [Loan Application Request JSON Payload](#loan-application-request-json-payload)) containing the required information pertaining to the loan itself together with information in relation to the borrowers. A loan application can include one or more borrowers. The service must store the submitted loan applications for, at least, as long as the service is running. Finally, the endpoint should return a response that includes the identifier of the newly created loan application.
2. A `GET` endpoint that retrieves a loan application including its status (that is, `Pending`, `Denied` or `Approved`).

In total, the service includes six endpoints (including the two required endpoints mentioned above):
- `GET /api/v1/loans/{id}` (**required**): Retrieves a specific loan application by identifier including its status. If a loan application with the specified identifier does not exist, a 404 (Not Found) HTTP status code is returned.
- `PUT /api/v1/loans/{id}`: Updates a specific loan application by passing in a loan application (JSON) payload and the accompanying identifier.
- `DELETE /api/v1/loans/{id}`: Deletes a specific loan application by identifier. If a loan application with the specified identifier does not exist, a 404 (Not Found) HTTP status code is returned.
- `GET /api/v1/loans`: Retrieves all of the submitted loan applications.
- `POST /api/v1/loans` (**required**): Creates a new loan application by passing in a loan application (JSON) payload.
- `GET /api/v1/loans/{id}/borrowers`: Retrieves the borrowers for a specific loan application by identifier. If a loan application with the specified identifier does not exist, a 404 (Not Found) HTTP status code is returned.

#### Loan Application Request JSON Payload

```json
{
   "borrowers":[
      {
         "socialSecurityNumber":"01056000069",
         "name":"Kari Nordmann"
      },
      {
         "socialSecurityNumber":"01056000301",
         "name":"Ola Nordmann"
      }
   ],
   "amount":2450000,
   "motivation":"Vi skal låne penger til........",
   "duration":300,
   "deductionFreePeriod":12,
   "type":"annuity"
}
```

The above-mentioned endpoints can be interacted with on the (Linux) command-line.

Execute the following command to retrieve all of the submitted loan applications (optionally followed by piping to the `json_pp` command):

```bash
curl -v localhost:8080/api/v1/loans
```

Execute the following command to retrieve a specific loan application:

```bash
curl -v localhost:8080/api/v1/loans/1
```

Execute the following command to create a new loan application

```bash
curl -v -X POST localhost:8080/api/v1/loans
         -H 'Content-type:application/json'
         -d '{"amount": "3250000", "motivation": "Vi ønsker å kjøpe drømmehuset vårt.", "duration": "240", "deductionFreePeriod": "12", "type": "annuity", "borrowers": [{"name": "Cecilie Johansen", "socialSecurityNumber": "01056000307"}, {"name": "Tommy Johansen", "socialSecurityNumber": "01056000311"}]}'
```

Execute the following command to retrieve the borrowers for a specific loan application:

```bash
curl -v localhost:8080/api/v1/loans/1/borrowers
```

#### Building and Running the Service (with Linux)
Execute the following command in the Spring Boot service's top-level directory (that is, `service`):

```bash
./mvnw package
``` 

Run the following command after successfully executing the above command:

```bash
java -jar target/loan-0.0.1-SNAPSHOT.jar
```

#### OpenAPI Documentation
Swagger-based OpenAPI documentation is available for the Spring Boot service, here: `http://localhost:8080/swagger-ui.html`.

#### Logging
A loan application request is logged to the standard output (console) by the service.

#### The Service Layer and Business Logic
In a layered architecture, the [service layer](https://martinfowler.com/eaaCatalog/serviceLayer.html) is responsible for representing concepts of the business, information about the business situation, and business rules. State that reflects the business situation is controlled and used here, even though the technical details of storing it are delegated to the infrastructure. 

In the context of the loan application service, the service layer is where the submitted loan applications can be validated, verified and further processed &mdash;in conjunction with other services&mdash; until completion.

#### Additional Dependencies
In addition to the required dependencies for the actual Spring Boot framework, the following dependencies (Maven coordinates) have been included:
- `org.springframework.boot:spring-boot-starter-hateoas`
- `org.springdoc:springdoc-openapi-ui:1.5.10`
- `com.h2database:h2`
- `org.jetbrains:annotations:22.0.0`

The `spring-boot-starter-hateoas` dependency was included to help with creating hypermedia-driven representations that follow the [HATEOAS](https://restcookbook.com/Basics/hateoas/) principle.

The `springdoc-openapi-ui` dependency was added to automatically generate OpenAPI-based API documentation which can easily be visualized and interacted with by means of the [Swagger UI](https://swagger.io/tools/swagger-ui/).

The `h2` dependency &mdash;an [in-memory database](https://www.h2database.com/html/main.html)&mdash; was added to allow for the persistence of the loan applications while the service is running. 

Finally, the `annotations` dependency has been included so that the integrated development environment (that is, [IntelliJ IDEA](https://www.jetbrains.com/idea/)) will generate appropriate warnings if *null* checks are missing.

### Part 2: Angular Client App &mdash; Requirements
The Angular app includes functionality to submit new loan applications together with the ability to check the status &mdash;that is, `Pending`, `Denied` or `Approved`&mdash; of already submitted loan applications. Screen shots of the app are available in the repository's `images` directory.

What's more, the app must be startable with `npm start` (which will start the Angular Live Development Server). The app can be accessed, here: `http://localhost:4200/home`.

#### Additional Dependencies
In addition to the required Angular framework dependencies, the following dependency has been included: [Bootstrap widgets](https://ng-bootstrap.github.io/#/home).

## Miscellaneous Comments and Considerations
- **Transaction management**: The aim of lazy loading is to save resources by not loading related objects into memory when the main object is loaded. Instead, the initialization of lazy entities is postponed until the moment they're needed. When retrieving lazily-loaded data, there are two steps in the process. First, there's populating the main object, and second, retrieving the data within its proxies. Loading data always requires an open *session* in [Hibernate](https://hibernate.org/). The problem arises when the second step happens after the transaction has closed, which leads to a `LazyInitializationException`.
    - Lazy loading with automatic transactions with the `spring.jpa.properties.hibernate.enable_lazy_load_no_trans` property: Turning this on means that **each fetch of a lazy entity will open a temporary session and run inside a separate transaction** probably resulting in the [N + 1 issue](https://vladmihalcea.com/n-plus-1-query-problem/).
    - Lazy loading with a surrounding transaction: In this case, this is the approach that has been taken. 
- The application's service is both implemented with Java 16 and targets Java 16, as well. If this was an actual production 
service, it would make more sense to use a [Java LTS release](https://www.oracle.com/java/technologies/java-se-support-roadmap.html) instead &mdash; 
currently either Java 11 or, the very recently released, Java 17.
- In relation to [GDPR](https://gdpr-info.eu/) considerations, personal data of the borrowers is not logged when receiving a loan application request. An alternative approach to removing the personal data would be to anonymize it.
- For testing purposes, upon startup, an example loan application is added to the service's repository.
- The H2 database also provides the ability to access the database using a browser. If you started the server on the same computer as the browser, open the URL `http://localhost:8080/h2` and provide the driver class of the database, the JDBC URL and user credentials to log in.
- At this point, the project does not include any unit tests.
- At this point, for the sake of expediency, the overriden `equals`, `hashCode` and `toString` methods in the model classes (that is, the `Loan` and `Borrower` classes) are inadequately implemented. 

## Relevant Resources
- [What is HATEOAS?](https://dzone.com/articles/rest-api-what-is-hateoas)
- [HAL - Hypertext Application Language](https://stateless.group/hal_specification.html)
- [Best Practices in API Design](https://swagger.io/resources/articles/best-practices-in-api-design/)
